import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;

public class ConnectionPool implements Pool {
    private static final int POOL_SIZE = 10;
    private static String URL;
    private static String USERNAME;
    private static String PASSWORD;
    private static Queue<Connection> freeConnections;//TODO переделать
    private static ConnectionPool instance;
    private static Semaphore semaphore;
    private static ThreadLocal<Connection> threadLocal;

    private ConnectionPool() {
        try {
            create();
        } catch (SQLException | ClassNotFoundException throwables) {
            System.out.println("ConnectionPoolException - " + throwables);
        }
        threadLocal = ThreadLocal.withInitial(ConnectionPool::getConnectionToThread);
    }

    private static void getProperty() {
        try (InputStream input = ConnectionPool.class.getClassLoader().getResourceAsStream("database.properties")) {
            Properties prop = new Properties();
            if (input == null) {
                throw new Exception("File database.properties not found");
            }
            prop.load(input);
            URL = prop.getProperty("url");
            USERNAME = prop.getProperty("username");
            PASSWORD = prop.getProperty("password");
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ConnectionPool getInstance() {
        if (instance == null) {
            instance = new ConnectionPool();
        }
        return instance;
    }

    private void create() throws SQLException, ClassNotFoundException {
        getProperty();
        freeConnections = new ConcurrentLinkedQueue<>();
        semaphore = new Semaphore(POOL_SIZE);
        for (int i = 0; i < POOL_SIZE; i++) {
            freeConnections.add(createConnection());
        }
    }

    private static Connection getConnectionToThread() {
        Connection connection = null;
        try {
            semaphore.acquire();
            connection = freeConnections.poll();
            if (connection != null) {
                if (!connection.isValid(0)) {
                    connection.close();
                    connection = createConnection();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void returnConnection() {
        Connection connection = threadLocal.get();
        threadLocal.remove();
        try {
            if (!connection.isValid(0)) {
                connection.close();
                freeConnections.add(createConnection());
            } else {
                freeConnections.add(connection);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        semaphore.release();
    }

    public Connection getConnection() {
        Connection connection = threadLocal.get();
        try {
            if (!connection.isValid(0)) {
                connection.close();
                threadLocal.remove();
                freeConnections.add(createConnection());
                return threadLocal.get();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    private static Connection createConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        connection.setAutoCommit(false);
        return connection;
    }

    public void commit() {
        for (Connection conn : freeConnections) {
            try {
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        Connection connection = threadLocal.get();
        try {
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void rollback() {
        Connection connection = threadLocal.get();
        try {
            connection.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
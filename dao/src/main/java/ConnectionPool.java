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
    private Semaphore semaphore;

    private ConnectionPool() {
        try {
            create();
        } catch (SQLException | ClassNotFoundException throwables) {
            System.out.println("ConnectionPoolException - " + throwables);
        }
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
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
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

    public void returnConnection(Connection connection) {
        try {
            boolean isValid = connection.isValid(0);
            if (!isValid) {
                connection.close();
            }
            freeConnections.add(isValid ? connection : createConnection());
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("returnConnection Exception - " + e);
        }
        semaphore.release();
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            semaphore.acquire();
            connection = freeConnections.poll();
            boolean isValid = false;
            if (connection != null) {
                isValid = connection.isValid(0);
                if (!isValid) {
                    connection.close();
                }
            }
            return isValid ? connection : createConnection();
        } catch (InterruptedException | SQLException | ClassNotFoundException e) {
            System.out.println("returnConnection Exception - " + e);
        }
        return connection;
    }

    private Connection createConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        connection.setAutoCommit(false);
        return connection;
    }

}
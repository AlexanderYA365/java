import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.LinkedBlockingQueue;

public class ConnectionPool {
    private static final int POOL_SIZE = 10;
    private static final int MAX_TIMEOUT = 10;
    private static String URL;
    private static String USERNAME;
    private static String PASSWORD;
    private static LinkedBlockingQueue<Connection> pool;
    private static ConnectionPool instance;

    private ConnectionPool() {
        try {
            create();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static void getProperty() {
        try (InputStream input = AccountDAO.class.getClassLoader().getResourceAsStream("database.properties")) {
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
            synchronized (ConnectionPool.class) {
                instance = new ConnectionPool();
            }
        }
        return instance;
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = pool.take();
            if (!connection.isValid(MAX_TIMEOUT)) {
                connection.close();
                connection = createConnection();
            }
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void create() throws InterruptedException, SQLException {
        getProperty();
        for (int i = 0; i < POOL_SIZE; i++) {
            Connection connection = createConnection();
            pool.put(connection);
        }
    }

    private Connection createConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        connection.setAutoCommit(false);
        return connection;
    }

}
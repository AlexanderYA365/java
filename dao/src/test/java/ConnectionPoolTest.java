import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ConnectionPoolTest {

    @Test
    void getInstance(){
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try {
            connectionPool.create();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Connection connection = connectionPool.getConnection();
        Connection[] connections = new Connection[11];
        for (int i = 0; i < 11; i++){
            connections[i] = connectionPool.getConnection();
            System.out.println("connection #" + i);
        }
    }

}
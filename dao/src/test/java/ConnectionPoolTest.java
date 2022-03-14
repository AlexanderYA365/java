import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

class ConnectionPoolTest {

    @Test
    void getInstance() {
        Pool pool = ConnectionPool.getInstance();

    }

}
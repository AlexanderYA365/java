import com.getjavajob.training.yakovleva.dao.ConnectionPool;
import com.getjavajob.training.yakovleva.dao.Pool;
import org.junit.jupiter.api.Test;

class ConnectionPoolTest {

    @Test
    void getInstance() {
        Pool pool = ConnectionPool.getInstance();

    }

}
import java.sql.Connection;

public interface Pool {

    Connection getConnection();

    void returnConnection(Connection connection);

}

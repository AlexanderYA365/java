import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class JNDIPool implements Pool {
    private DataSource source;
    private static final JNDIPool jndiPool = new JNDIPool();

    public static JNDIPool  getInstance(){
        return jndiPool;
    }

    private JNDIPool() {
        try{
            Context initContext = new InitialContext();
            Context contextEnv = (Context) initContext.lookup("java:comp/env");
            this.source = (DataSource) contextEnv.lookup("jdbc/socnetwork");
        }catch (Exception e){
            System.out.println("JNDIPool Exception - " + e);
        }
    }

    @Override
    public Connection getConnection() {
        try {
            return this.source.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void returnConnection() {
    }

    @Override
    public void rollback() {
    }

    @Override
    public void commit() {
    }

}
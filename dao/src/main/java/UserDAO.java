import java.sql.Connection;
import java.sql.Statement;

public class UserDAO {

    public void create(Connection connection, User user){
        try {
            Statement statement = connection.createStatement();
            String text = "INSERT INTO user(username, password) " +
                    "VALUES (" + "'" + user.getUserName() + "\',\'"+ user.getPassword() + "\');";
            //System.out.println(text);
            int rows = statement.executeUpdate(text);
            connection.commit();
            System.out.println("Added "+ rows + " rows");
        } catch(Exception ex){
            System.out.println("Connection failed...");
            System.out.println(ex);
        }
    }

}

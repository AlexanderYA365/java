import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class GroupDAO {
    private String url;
    private String username;
    private String password;
    private Connection connection;

    public GroupDAO(){
        try (InputStream input = AccountDAO.class.getClassLoader().getResourceAsStream("database.properties")) {
            Properties prop = new Properties();
            if (input == null) {
                throw new Exception("Sorry, unable to find database.properties");
            }
            prop.load(input);
            url = prop.getProperty("url");
            username = prop.getProperty("username");
            password = prop.getProperty("password");
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
            connection.setAutoCommit(false);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public boolean createGroup(Group group) throws Exception {
        if(group.getGroupName().equals("")){
            throw new Exception("Group must have name");
        } else {
            try {
                Statement statement = connection.createStatement();
                String text = "INSERT INTO socialnetwork.group(groupname, logo, idAdministrator, account) " +
                        "VALUES (" + "'" + group.getGroupName() + "\',\'"+ group.getLogo() +
                        "'," + group.getIdAdministrator() + ","+ group.getIdAccount()  + ");";
                int rows = statement.executeUpdate(text);
                System.out.printf("Added %d rows", rows);
            } catch(Exception ex){
                System.out.println("Connection failed...");
                System.out.println(ex);
            } finally {
                try {
                    connection.commit();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return true;
        }
    }

    public Group readGroup(int id){
        Group group = new Group();
        try {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM socialnetwork.group WHERE idgroup = ?");
            query.setInt(1, id);
            ResultSet resultSet = query.executeQuery();
            while(resultSet.next()){
                group.setIdGroup(resultSet.getInt(1));
                group.setGroupName(resultSet.getString(2));
                group.setLogo(resultSet.getString(3));
                group.setIdAdministrator(resultSet.getInt(4));
                group.setIdAccount(resultSet.getInt(5));
            }
        }catch(Exception ex){
            System.out.println("Connection failed...");
            System.out.println(ex);
        }
        return group;
    }

    public List<Group> readGroups(){
        List<Group> groupList = new ArrayList<Group>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM socialnetwork.group");
            while(resultSet.next()){
                Group group = new Group();
                group.setIdGroup(resultSet.getInt(1));
                group.setGroupName(resultSet.getString(2));
                group.setLogo(resultSet.getString(3));
                group.setIdAdministrator(resultSet.getInt(4));
                group.setIdAccount(resultSet.getInt(5));
                groupList.add(group);
            }
        }catch(Exception ex){
            System.out.println("Connection failed...");
            System.out.println(ex);
        }
        return groupList;
    }

    public boolean updateGroup(Group group){
        try {
            Statement statement = connection.createStatement();
            String sql = "UPDATE socialnetwork.group" +
                    " SET groupName = " + "'" + group.getGroupName()  + "'," +
                    " logo = " + "'" + group.getLogo()  + "'," +
                    " idAdministrator = " + group.getIdAdministrator()  + "," +
                    " Account = " + group.getIdAccount()  +
                    " WHERE idgroup = " + group.getIdGroup() +";";
            int rows = statement.executeUpdate(sql);
            System.out.println("Updated rows = " + rows);
        }catch(Exception ex){
            System.out.println("Connection failed...");
            System.out.println(ex);
        } finally {
            try {
                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public boolean deleteGroup(Group group) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM socialnetwork.group WHERE idgroup = " + group.getIdGroup());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

}

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class GroupDao {
    private String url;
    private String username;
    private String password;
    private Connection connection;

    public GroupDao() {
        try (InputStream input = AccountDao.class.getClassLoader().getResourceAsStream("database.properties")) {
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
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
            connection.setAutoCommit(false);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public boolean create(Group group) throws Exception {
        if (group.getGroupName().equals("")) {
            throw new Exception("Group must have name");
        } else {
            try {
                Statement statement = connection.createStatement();
                String text = "INSERT INTO heroku_dc02d468f96562c.`group`(groupname, logo, idAdministrator, account) " +
                        "VALUES (" + "'" + group.getGroupName() + "\',\'" + group.getLogo() +
                        "'," + group.getIdAdministrator() + "," + group.getIdAccount() + ");";
                int rows = statement.executeUpdate(text);
                System.out.printf("Added %d rows", rows);
            } catch (Exception ex) {
                System.out.println("Connection failed...createGroup");
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

    public Group read(int id) {
        Group group = new Group();
        try {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM heroku_dc02d468f96562c.`group` WHERE idgroup = ?");
            query.setInt(1, id);
            ResultSet resultSet = query.executeQuery();
            while (resultSet.next()) {
                group.setIdGroup(resultSet.getInt(1));
                group.setGroupName(resultSet.getString(2));
                group.setLogo(resultSet.getString(3));
                group.setIdAdministrator(resultSet.getInt(4));
                group.setIdAccount(resultSet.getInt(5));
            }
        } catch (Exception ex) {
            System.out.println("Connection failed...readGroup");
            System.out.println(ex);
        }
        return group;
    }

    public List<Group> readGroups() {
        List<Group> groupList = new ArrayList<Group>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM heroku_dc02d468f96562c.`group`");
            while (resultSet.next()) {
                Group group = new Group();
                group.setIdGroup(resultSet.getInt(1));
                group.setGroupName(resultSet.getString(2));
                group.setLogo(resultSet.getString(3));
                group.setIdAdministrator(resultSet.getInt(4));
                group.setIdAccount(resultSet.getInt(5));
                groupList.add(group);
            }
        } catch (Exception ex) {
            System.out.println("Connection failed...readGroups");
            System.out.println(ex);
        }
        return groupList;
    }

    public List<Group> readGroupsAccount(int id) {
        List<Group> groupList = new ArrayList<Group>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM heroku_dc02d468f96562c.`group` WHERE Account = " + id);
            while (resultSet.next()) {
                Group group = new Group();
                group.setIdGroup(resultSet.getInt(1));
                group.setGroupName(resultSet.getString(2));
                group.setLogo(resultSet.getString(3));
                group.setIdAdministrator(resultSet.getInt(4));
                group.setIdAccount(resultSet.getInt(5));
                groupList.add(group);
            }
        } catch (Exception ex) {
            System.out.println("Connection failed...readGroups");
            System.out.println(ex);
        }
        return groupList;
    }

    public boolean update(Group group) {
        try {
            Statement statement = connection.createStatement();
            String sql = "UPDATE heroku_dc02d468f96562c.`group`" +
                    " SET groupName = " + "'" + group.getGroupName() + "'," +
                    " logo = " + "'" + group.getLogo() + "'," +
                    " idAdministrator = " + group.getIdAdministrator() + "," +
                    " Account = " + group.getIdAccount() +
                    " WHERE idgroup = " + group.getIdGroup() + ";";
            int rows = statement.executeUpdate(sql);
            System.out.println("Updated rows = " + rows);
        } catch (Exception ex) {
            System.out.println("Connection failed...updateGroup");
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

    public boolean delete(Group group) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM heroku_dc02d468f96562c.`group` WHERE idgroup = " + group.getIdGroup());
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

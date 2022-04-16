import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GroupDao {
    private final Pool connectionPool;
    private static GroupDao groupDao;

    private GroupDao() {
        connectionPool = ConnectionPool.getInstance();
    }

    public static GroupDao getInstance(){
        if (groupDao == null) {
            groupDao = new GroupDao();
        }
        return groupDao;
    }

    public boolean create(Group group) {
        String sql = "INSERT INTO heroku_dc02d468f96562c.`group`(groupname, logo, idAdministrator, account)" +
                " VALUES ('?', '?', ?, ?);";
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement query = connection.prepareStatement(sql)) {
            query.setString(1, group.getGroupName());
            query.setString(2, group.getLogo());
            query.setInt(3, group.getIdAdministrator());
            query.setInt(4, group.getIdAccount());
            int rows = query.executeUpdate();
            System.out.println("Added " + rows + " rows");
        } catch (Exception ex) {
            System.out.println("Connection failed...createFriend");
            System.out.println(ex);
        }
        return true;
    }

    public Group read(int id) {
        Group group = new Group();
        try {
            Connection connection = connectionPool.getConnection();
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

    public List<Group> read(String groupName) {
        System.out.println("Group read(String groupName)");
        List<Group> groupList = new ArrayList<Group>();
        try {
            String sql = "SELECT * FROM heroku_dc02d468f96562c.`group` WHERE groupname = ?";
            System.out.println(sql);
            Connection connection = connectionPool.getConnection();
            PreparedStatement query = connection.prepareStatement(sql);
            query.setString(1, groupName);
            ResultSet resultSet = query.executeQuery();
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
            System.out.println("Connection failed...read");
            System.out.println(ex);
        }
        return groupList;
    }

    public List<Group> readGroups() {
        List<Group> groupList = new ArrayList<Group>();
        try {
            Connection connection = connectionPool.getConnection();
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
        List<Group> groupList = new ArrayList<>();
        try {
            Connection connection = connectionPool.getConnection();
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
            Connection connection = connectionPool.getConnection();
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
        }
        return true;
    }

    public boolean delete(Group group) {
        try {
            Connection connection = connectionPool.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM heroku_dc02d468f96562c.`group` WHERE idgroup = " + group.getIdGroup());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    public boolean insertAccount(Group group, int idAccount) {
        System.out.println("insertAccount, group - " + group + " ,idAccount - " + idAccount);
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement query = connection.prepareStatement(
                "INSERT INTO heroku_dc02d468f96562c.`group`(groupname, logo, idAdministrator, account) VALUES (?, ?, ?, ?)")) {
            query.setString(1, group.getGroupName());
            query.setString(2, group.getLogo());
            query.setInt(3, group.getIdAdministrator());
            query.setInt(4, idAccount);
            int rows = query.executeUpdate();
            System.out.println("Added " + rows + " rows");
        } catch (Exception ex) {
            System.out.println("Connection failed...insertAccount");
            System.out.println(ex);
        }
        return true;
    }

}

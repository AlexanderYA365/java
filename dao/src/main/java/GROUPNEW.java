import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GROUPNEW {
    private Connection connection;
    private final Pool connectionPool;

    public GROUPNEW(Pool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public boolean create(Group group) throws Exception {
        String sql = "INSERT INTO heroku_dc02d468f96562c.`group`(groupname, logo, idAdministrator, account, dateCreateGroup)" +
                " VALUES ('?', '?', ?, ?, NOW());";
        try (PreparedStatement query = connection.prepareStatement(sql)) {
            query.setString(1, group.getGroupName());
            query.setString(2, group.getLogo());
            query.setInt(3, group.getIdAdministrator());
            query.setInt(4, group.getIdAccount());
            query.setDate(5, (Date) group.getDateCreateGroup());
            int rows = query.executeUpdate();
            System.out.println("Added " + rows + " rows");
        } catch (Exception ex) {
            System.out.println("Connection failed...createFriend");
            System.out.println(ex);
        } finally {
            try {
                connection.commit();
                connectionPool.returnConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public Group read(int id) {
        Group group = new Group();
        try {
            connection = connectionPool.getConnection();
            PreparedStatement query = connection.prepareStatement("SELECT * FROM heroku_dc02d468f96562c.`group` WHERE idgroup = ?");
            query.setInt(1, id);
            ResultSet resultSet = query.executeQuery();
            while (resultSet.next()) {
                group.setIdGroup(resultSet.getInt(1));
                group.setGroupName(resultSet.getString(2));
                group.setLogo(resultSet.getString(3));
                group.setIdAdministrator(resultSet.getInt(4));
                group.setIdAccount(resultSet.getInt(5));
                group.setDateCreateGroup(resultSet.getDate(6));
            }
        } catch (Exception ex) {
            System.out.println("Connection failed...readGroup");
            System.out.println(ex);
        }
        connectionPool.returnConnection(connection);
        return group;
    }

    public List<Group> read(String groupName) {
        System.out.println("Group read(String groupName)");
        List<Group> groupList = new ArrayList<Group>();
        try {
            String sql = "SELECT * FROM heroku_dc02d468f96562c.`group` WHERE groupname = ?";
            System.out.println(sql);
            connection = connectionPool.getConnection();
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
                group.setDateCreateGroup(resultSet.getDate(6));
                groupList.add(group);
            }
        } catch (Exception ex) {
            System.out.println("Connection failed...read");
            System.out.println(ex);
        }
        connectionPool.returnConnection(connection);
        return groupList;
    }

    public List<Group> readGroups() {
        List<Group> groupList = new ArrayList<Group>();
        try {
            connection = connectionPool.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM heroku_dc02d468f96562c.`group`");
            while (resultSet.next()) {
                Group group = new Group();
                group.setIdGroup(resultSet.getInt(1));
                group.setGroupName(resultSet.getString(2));
                group.setLogo(resultSet.getString(3));
                group.setIdAdministrator(resultSet.getInt(4));
                group.setIdAccount(resultSet.getInt(5));
                group.setDateCreateGroup(resultSet.getDate(6));
                groupList.add(group);
            }
        } catch (Exception ex) {
            System.out.println("Connection failed...readGroups");
            System.out.println(ex);
        }
        connectionPool.returnConnection(connection);
        return groupList;
    }

    public List<Group> readGroupsAccount(int id) {
        List<Group> groupList = new ArrayList<Group>();
        try {
            connection = connectionPool.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM heroku_dc02d468f96562c.`group` WHERE Account = " + id);
            while (resultSet.next()) {
                Group group = new Group();
                group.setIdGroup(resultSet.getInt(1));
                group.setGroupName(resultSet.getString(2));
                group.setLogo(resultSet.getString(3));
                group.setIdAdministrator(resultSet.getInt(4));
                group.setIdAccount(resultSet.getInt(5));
                group.setDateCreateGroup(resultSet.getDate(6));
                groupList.add(group);
            }
        } catch (Exception ex) {
            System.out.println("Connection failed...readGroups");
            System.out.println(ex);
        }
        connectionPool.returnConnection(connection);
        return groupList;
    }

    public boolean update(Group group) {
        try {
            connection = connectionPool.getConnection();
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
                connectionPool.returnConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public boolean delete(Group group) {
        try {
            connection = connectionPool.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM heroku_dc02d468f96562c.`group` WHERE idgroup = " + group.getIdGroup());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                connection.commit();
                connectionPool.returnConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public boolean insertAccount(Group group, int idAccount) {
        System.out.println("insertAccount, group - " + group + " ,idAccount - " + idAccount);
        connection = connectionPool.getConnection();
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
        } finally {
            try {
                connection.commit();
                connectionPool.returnConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

}

import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class GroupDao {
    private final JNDIPool connectionPool;

    public GroupDao() {
        connectionPool = JNDIPool.getInstance();
    }

    public boolean create(Group group) {
        String sql = "INSERT INTO heroku_dc02d468f96562c.`group`(groupname, logo, idAdministrator, account)" +
                " VALUES ('?', '?', ?, ?);";
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement query = connection.prepareStatement(sql)) {
            query.setString(1, group.getGroupName());
            query.setString(2, group.getLogo());
            query.setInt(3, group.getIdAdministrator());
            query.setInt(4, group.getIdAccount());
            int rows = query.executeUpdate();
            System.out.println("Added " + rows + " rows");
        } catch (Exception ex) {
            System.out.println("create Exception - " + ex);
        }
        return true;
    }

    public Group read(int id) {
        Group group = new Group();
        String sql = "SELECT * FROM heroku_dc02d468f96562c.`group` WHERE idgroup = ?";
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement query = connection.prepareStatement(sql)) {
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
            System.out.println("read(int id) Exception - " + ex);
        }
        return group;
    }

    public List<Group> read(String groupName) {
        System.out.println("Group read(String groupName)");
        List<Group> groupList = new ArrayList<>();
        String sql = "SELECT * FROM heroku_dc02d468f96562c.`group` WHERE groupname = ?";
        System.out.println(sql);
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement query = connection.prepareStatement(sql)) {
            query.setString(1, groupName);
            ResultSet resultSet = query.executeQuery();
            fillGroup(groupList, resultSet);
        } catch (Exception ex) {
            System.out.println("read Exception - " + ex);
        }
        return groupList;
    }

    public List<Group> readGroups() {
        List<Group> groupList = new ArrayList<>();
        String sql = "SELECT * FROM heroku_dc02d468f96562c.`group`";
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement query = connection.prepareStatement(sql)) {
            ResultSet resultSet = query.executeQuery(sql);
            fillGroup(groupList, resultSet);
        } catch (Exception ex) {
            System.out.println("readGroups Exception - " + ex);
        }
        return groupList;
    }

    public List<Group> readGroupsAccount(int id) {
        List<Group> groupList = new ArrayList<>();
        String sql = "SELECT * FROM heroku_dc02d468f96562c.`group` WHERE Account = " + id;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement query = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = query.executeQuery(sql)) {
                fillGroup(groupList, resultSet);
            }
        } catch (Exception ex) {
            System.out.println("readGroupsAccount Exception - " + ex);
        }
        return groupList;
    }

    public boolean update(Group group) {
        String sql = "UPDATE heroku_dc02d468f96562c.`group`" +
                " SET groupName = " + "'" + group.getGroupName() + "'," +
                " logo = " + "'" + group.getLogo() + "'," +
                " idAdministrator = " + group.getIdAdministrator() + "," +
                " Account = " + group.getIdAccount() +
                " WHERE idgroup = " + group.getIdGroup() + ";";
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement query = connection.prepareStatement(sql)) {
            int rows = query.executeUpdate(sql);
            System.out.println("Updated rows = " + rows);
        } catch (Exception ex) {
            System.out.println("update Exception - " + ex);
        }
        return true;
    }

    public boolean delete(Group group) {
        String sql = "DELETE FROM heroku_dc02d468f96562c.`group` WHERE idgroup = " + group.getIdGroup();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement query = connection.prepareStatement(sql)) {
            int rows = query.executeUpdate(sql);
            System.out.println("delete rows = " + rows);
        } catch (SQLException ex) {
            System.out.println("delete Exception - " + ex);
        }
        return true;
    }

    public boolean insertAccount(Group group, int idAccount) {
        System.out.println("insertAccount, group - " + group + " ,idAccount - " + idAccount);
        String sql = "INSERT INTO heroku_dc02d468f96562c.`group`(groupname, logo, idAdministrator, account) VALUES (?, ?, ?, ?)";
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement query = connection.prepareStatement(sql)) {
            query.setString(1, group.getGroupName());
            query.setString(2, group.getLogo());
            query.setInt(3, group.getIdAdministrator());
            query.setInt(4, idAccount);
            int rows = query.executeUpdate();
            System.out.println("Added " + rows + " rows");
        } catch (Exception ex) {
            System.out.println("insertAccount Exception - " + ex);
        }
        return true;
    }

    private void fillGroup(List<Group> groupList, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            Group group = new Group();
            group.setIdGroup(resultSet.getInt(1));
            group.setGroupName(resultSet.getString(2));
            group.setLogo(resultSet.getString(3));
            group.setIdAdministrator(resultSet.getInt(4));
            group.setIdAccount(resultSet.getInt(5));
            groupList.add(group);
        }
    }

}
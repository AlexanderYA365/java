import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FriendDao {
    private final Pool connectionPool;
    private static FriendDao friendDao;

    private FriendDao() {
        connectionPool = ConnectionPool.getInstance();
    }

    public static FriendDao getInstance(){
        if (friendDao == null) {
            friendDao = new FriendDao();
        }
        return friendDao;
    }

    public boolean create(Friend friend) {
        System.out.println("FriendDao.create - start");
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement query = connection.prepareStatement(
                "INSERT INTO friends(idAccount, idFriendsAccount) VALUES (?, ?)")) {
            query.setInt(1, friend.getIdAccount());
            query.setInt(2, friend.getIdFriendsAccount());
            int rows = query.executeUpdate();
            System.out.println("Added " + rows + " rows");
        } catch (Exception ex) {
            System.out.println("Connection failed...createFriend");
            System.out.println(ex);
        }
        return true;
    }

    public List<Friend> readFriends(int id) {
        List<Friend> friendList = new ArrayList<>();
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement query = connection.prepareStatement("SELECT * FROM friends WHERE idAccount = ?")) {
            query.setInt(1, id);
            ResultSet resultSet = query.executeQuery();
            while (resultSet.next()) {
                Friend friend = new Friend();
                friend.setId(resultSet.getInt(1));
                friend.setIdAccount(resultSet.getInt(2));
                friend.setIdFriendsAccount(resultSet.getInt(3));
                friendList.add(friend);
            }
        } catch (Exception ex) {
            System.out.println("Connection failed...readFriends");
            System.out.println(ex);
        }
        return friendList;
    }

    public Friend read(int id) {
        Friend friend = new Friend();
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement query = connection.prepareStatement("SELECT * FROM friends WHERE idfriends = ?")) {
            query.setInt(1, id);
            ResultSet resultSet = query.executeQuery();
            while (resultSet.next()) {
                friend.setId(resultSet.getInt(1));
                friend.setIdAccount(resultSet.getInt(2));
                friend.setIdFriendsAccount(resultSet.getInt(3));
            }
        } catch (Exception ex) {
            System.out.println("Connection failed...readFriend");
            System.out.println(ex);
        }
        return friend;
    }

    public List<Friend> readAllFriends() {
        List<Friend> friendList = new ArrayList<>();
        Connection connection = connectionPool.getConnection();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM friends");
            while (resultSet.next()) {
                Friend friend = new Friend();
                friend.setIdAccount(resultSet.getInt(1));
                friend.setIdFriendsAccount(resultSet.getInt(2));
                friendList.add(friend);
            }
        } catch (Exception ex) {
            System.out.println("Connection failed...readAllFriends");
            System.out.println(ex);
        }
        return friendList;
    }

    public boolean update(Friend friend) {
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement query = connection.prepareStatement(
                "UPDATE friends SET idAccount = ?, idFriendsAccount = ? WHERE idFriends = ?")) {
            query.setInt(1, friend.getIdAccount());
            query.setInt(2, friend.getIdFriendsAccount());
            query.setInt(3, friend.getId());
            int rows = query.executeUpdate();
            System.out.println("Updated rows = " + rows);
        } catch (Exception ex) {
            System.out.println("Connection failed...updateFriend");
            System.out.println(ex);
        }
        return true;
    }

    public boolean deleteFriendIdAccountIdFriendAccount(int idAccount, int idFriend) {
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement query = connection.prepareStatement("DELETE FROM friends WHERE idAccount = ? AND idFriendsAccount = ? ")) {
            query.setInt(1, idAccount);
            query.setInt(2, idFriend);
            int rows = query.executeUpdate();
            System.out.println("Delete rows = " + rows);
        } catch (SQLException throwables) {
            System.out.println("Connection failed...deleteFriendIdAccountIdFriendAccount");

            throwables.printStackTrace();
        }
        return true;
    }

    public boolean delete(Friend friend) {
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement query = connection.prepareStatement("DELETE FROM friends WHERE idFriends = ?")) {
            query.setInt(1, friend.getId());
            int rows = query.executeUpdate();
            System.out.println("Delete rows = " + rows);
        } catch (SQLException throwables) {
            System.out.println("Connection failed...deleteFriend");

            throwables.printStackTrace();
        }
        return true;
    }

    public List<Friend> readFriendsName(int id) {
        System.out.println("readFriendsName - start");
        List<Friend> friends = new ArrayList<>();
        String sql = "SELECT a.username, a.name,  " +
                "f.idAccount, f.idFriendsAccount, f.idFriends FROM friends f JOIN account a " +
                "ON f.idFriendsAccount = a.idAccount WHERE f.idAccount = ?";
        System.out.println(sql);
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Friend friend = new Friend();
                friend.setUsername(resultSet.getString(1));
                friend.setName(resultSet.getString(2));
                friend.setIdAccount(resultSet.getInt(3));
                friend.setIdFriendsAccount(resultSet.getInt(4));
                friend.setId(resultSet.getInt(5));
                friends.add(friend);
            }
        } catch (Exception ex) {
            System.out.println("Connection failed...readFriendsName");
            System.out.println(ex);
        }
        System.out.println("readWallMassageUserId - finish");
        return friends;
    }

}
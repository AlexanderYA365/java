import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FriendDao {
    private final JNDIPool connectionPool;

    public FriendDao() {
        connectionPool = JNDIPool.getInstance();
    }

    public boolean create(Friend friend) {
        System.out.println("FriendDao.create - start");
        String sql = "INSERT INTO friends(idAccount, idFriendsAccount) VALUES (?, ?)";
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement query = connection.prepareStatement(sql)) {
            query.setInt(1, friend.getIdAccount());
            query.setInt(2, friend.getIdFriendsAccount());
            int rows = query.executeUpdate();
            System.out.println("Added " + rows + " rows");
        } catch (Exception ex) {
            System.out.println("create Exception - " + ex);
        }
        return true;
    }

    public List<Friend> readFriends(int id) {
        List<Friend> friendList = new ArrayList<>();
        String sql = "SELECT * FROM friends WHERE idAccount = ?";
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement query = connection.prepareStatement(sql)) {
            query.setInt(1, id);
            try (ResultSet resultSet = query.executeQuery()) {
                while (resultSet.next()) {
                    Friend friend = new Friend();
                    friend.setId(resultSet.getInt(1));
                    friend.setIdAccount(resultSet.getInt(2));
                    friend.setIdFriendsAccount(resultSet.getInt(3));
                    friendList.add(friend);
                }
            }
        } catch (Exception ex) {
            System.out.println("readFriends Exception - " + ex);
        }
        return friendList;
    }

    public Friend read(int id) {
        Friend friend = new Friend();
        String sql = "SELECT * FROM friends WHERE idfriends = ?";
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement query = connection.prepareStatement(sql)) {
            query.setInt(1, id);
            try (ResultSet resultSet = query.executeQuery()) {
                while (resultSet.next()) {
                    friend.setId(resultSet.getInt(1));
                    friend.setIdAccount(resultSet.getInt(2));
                    friend.setIdFriendsAccount(resultSet.getInt(3));
                }
            }
        } catch (Exception ex) {
            System.out.println("read Exception - " + ex);
        }
        return friend;
    }

    public List<Friend> readAllFriends() {
        List<Friend> friendList = new ArrayList<>();
        String sql = "SELECT * FROM friends";
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement query = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = query.executeQuery(sql)) {
                while (resultSet.next()) {
                    Friend friend = new Friend();
                    friend.setIdAccount(resultSet.getInt(1));
                    friend.setIdFriendsAccount(resultSet.getInt(2));
                    friendList.add(friend);
                }
            }
        } catch (Exception ex) {
            System.out.println("readAllFriends Exception - " + ex);
        }
        return friendList;
    }

    public void update(Friend friend) {
        String sql = "UPDATE friends SET idAccount = ?, idFriendsAccount = ? WHERE idFriends = ?";
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement query = connection.prepareStatement(sql)) {
            query.setInt(1, friend.getIdAccount());
            query.setInt(2, friend.getIdFriendsAccount());
            query.setInt(3, friend.getId());
            int rows = query.executeUpdate();
            System.out.println("Updated rows = " + rows);
        } catch (Exception ex) {
            System.out.println("update Exception - " + ex);
        }
    }

    public void deleteFriendIdAccountIdFriendAccount(int idAccount, int idFriend) {
        String sql = "DELETE FROM friends WHERE idAccount = ? AND idFriendsAccount = ? ";
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement query = connection.prepareStatement(sql)) {
            query.setInt(1, idAccount);
            query.setInt(2, idFriend);
            int rows = query.executeUpdate();
            System.out.println("Delete rows = " + rows);
        } catch (SQLException ex) {
            System.out.println("deleteFriendIdAccountIdFriendAccount Exception - " + ex);
        }
    }

    public void delete(Friend friend) {
        String sql = "DELETE FROM friends WHERE idFriends = ?";
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement query = connection.prepareStatement(sql)) {
            query.setInt(1, friend.getId());
            int rows = query.executeUpdate();
            System.out.println("Delete rows = " + rows);
        } catch (SQLException ex) {
            System.out.println("delete Exception - " + ex);
        }
    }

    public List<Friend> readFriendsName(int id) {
        System.out.println("readFriendsName - start");
        List<Friend> friends = new ArrayList<>();
        String sql = "SELECT a.username, a.name,  " +
                "f.idAccount, f.idFriendsAccount, f.idFriends FROM friends f JOIN account a " +
                "ON f.idFriendsAccount = a.idAccount WHERE f.idAccount = ?";
        System.out.println(sql);
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement query = connection.prepareStatement(sql)) {
            query.setInt(1, id);
            try (ResultSet resultSet = query.executeQuery()) {
                while (resultSet.next()) {
                    Friend friend = new Friend();
                    friend.setUsername(resultSet.getString(1));
                    friend.setName(resultSet.getString(2));
                    friend.setIdAccount(resultSet.getInt(3));
                    friend.setIdFriendsAccount(resultSet.getInt(4));
                    friend.setId(resultSet.getInt(5));
                    friends.add(friend);
                }
            }
        } catch (Exception ex) {
            System.out.println("readFriendsName Exception - " + ex);
        }
        return friends;
    }

}
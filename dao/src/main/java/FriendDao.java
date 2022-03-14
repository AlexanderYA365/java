import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class FriendDao {
    private Connection connection;
    private Pool connectionPool;

    public FriendDao() {
        connectionPool = ConnectionPool.getInstance();
    }

    public boolean create(Friend friend) {
        System.out.println("FriendDao.create - start");
        connection = connectionPool.getConnection();
        try (PreparedStatement query = connection.prepareStatement(
                "INSERT INTO friends(idAccount, idFriendsAccount) VALUES (?, ?)")) {
            query.setInt(1, friend.getIdAccount());
            query.setInt(2, friend.getIdFriendsAccount());
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

    public List<Friend> readFriends(int id) {
        List<Friend> friendList = new ArrayList<>();
        connection = connectionPool.getConnection();
        try (PreparedStatement query = connection.prepareStatement("SELECT * FROM friends WHERE idAccount = ?")) {
            query.setInt(1, id);
            ResultSet resultSet = query.executeQuery();
            while (resultSet.next()) {
                Friend friend = new Friend();
                friend.setIdFriends(resultSet.getInt(1));
                friend.setIdAccount(resultSet.getInt(2));
                friend.setIdFriendsAccount(resultSet.getInt(3));
                friendList.add(friend);
            }
        } catch (Exception ex) {
            System.out.println("Connection failed...readFriends");
            System.out.println(ex);
        }
        connectionPool.returnConnection(connection);
        return friendList;
    }

    public Friend read(int id) {
        Friend friend = new Friend();
        connection = connectionPool.getConnection();
        try (PreparedStatement query = connection.prepareStatement("SELECT * FROM friends WHERE idfriends = ?")) {
            query.setInt(1, id);
            ResultSet resultSet = query.executeQuery();
            while (resultSet.next()) {
                friend.setIdFriends(resultSet.getInt(1));
                friend.setIdAccount(resultSet.getInt(2));
                friend.setIdFriendsAccount(resultSet.getInt(3));
            }
        } catch (Exception ex) {
            System.out.println("Connection failed...readFriend");
            System.out.println(ex);
        }
        connectionPool.returnConnection(connection);
        return friend;
    }

    public List<Friend> readAllFriends() {
        List<Friend> friendList = new ArrayList<>();
        connection = connectionPool.getConnection();
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
        connectionPool.returnConnection(connection);
        return friendList;
    }

    public boolean update(Friend friend) {
        connection = connectionPool.getConnection();
        try (PreparedStatement query = connection.prepareStatement(
                "UPDATE friends SET idAccount = ?, idFriendsAccount = ? WHERE idFriends = ?")) {
            query.setInt(1, friend.getIdAccount());
            query.setInt(2, friend.getIdFriendsAccount());
            query.setInt(3, friend.getIdFriends());
            int rows = query.executeUpdate();
            System.out.println("Updated rows = " + rows);
        } catch (Exception ex) {
            System.out.println("Connection failed...updateFriend");
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

    public boolean deleteFriendIdAccountIdFriendAccount(int idAccount, int idFriend) {
        connection = connectionPool.getConnection();
        try (PreparedStatement query = connection.prepareStatement("DELETE FROM friends WHERE idAccount = ?, idFriendsAccount = ? ")) {
            query.setInt(1, idAccount);
            query.setInt(2, idFriend);
            int rows = query.executeUpdate();
            System.out.println("Delete rows = " + rows);
        } catch (SQLException throwables) {
            System.out.println("Connection failed...deleteFriendIdAccountIdFriendAccount");

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

    public boolean delete(Friend friend) {
        connection = connectionPool.getConnection();
        try (PreparedStatement query = connection.prepareStatement("DELETE FROM friends WHERE idFriends = ?")) {
            query.setInt(1, friend.getIdFriends());
            int rows = query.executeUpdate();
            System.out.println("Delete rows = " + rows);
        } catch (SQLException throwables) {
            System.out.println("Connection failed...deleteFriend");

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

    public List<Friend> readFriendsName(int id) {
        System.out.println("readFriendsName - start");
        List<Friend> friends = new ArrayList<>();
        String sql = "SELECT username, name FROM friends JOIN account " +
                "ON friends.idFriendsAccount = account.idAccount WHERE friends.idAccount = ? AND friends.idFriendsAccount <> ?";
        System.out.println(sql);
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Friend friend = new Friend();
                friend.setUsername(resultSet.getString(1));
                friend.setName(resultSet.getString(2));
                friends.add(friend);
            }
        } catch (Exception ex) {
            System.out.println("Connection failed...readWallMassageUserId");
            System.out.println(ex);
        }
        System.out.println("readWallMassageUserId - finish");
        connectionPool.returnConnection(connection);
        return friends;
    }

}
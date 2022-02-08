import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class FriendDao {
    private String url;
    private String username;
    private String password;
    private Connection connection;

    public FriendDao() {
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

    public boolean create(Friend friend) {
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
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public List<Friend> readFriends(int id) {
        List<Friend> friendList = new ArrayList<>();

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
        return friendList;
    }

    public Friend read(int id) {
        Friend friend = new Friend();
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
        return friend;
    }

    public List<Friend> readAllFriends() {
        List<Friend> friendList = new ArrayList<>();
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
        try (PreparedStatement query = connection.prepareStatement(
                "UPDATE friends SET idAccount = ?, idFriendsAccount = ? WHERE idFriends = ?")) {
            query.setInt(1, friend.getIdAccount());
            query.setInt(2, friend.getIdFriendsAccount());
            query.setInt(2, friend.getIdFriends());
            int rows = query.executeUpdate();
            System.out.println("Updated rows = " + rows);
        } catch (Exception ex) {
            System.out.println("Connection failed...updateFriend");
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

    public boolean deleteFriendIdAccountIdFriendAccount(int idAccount, int idFriend) {
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
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public boolean delete(Friend friend) {
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
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

}
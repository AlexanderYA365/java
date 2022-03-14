import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class WallMassageDao {
    private Connection connection;
    private final Pool connectionPool;

    WallMassageDao() {
        connectionPool = ConnectionPool.getInstance();
    }

    public boolean create(WallMassage wallMassage) {
        if (wallMassage.getMassage() == null) {
            return false;
        }
        String sql = "INSERT INTO wallmassage(idSender, idReceiving, massage, picture, publicationDate, edited) " +
                "VALUES (?,?,?,?, NOW(), ?);";
        System.out.println(sql);
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {//TODO
            preparedStatement.setInt(1, wallMassage.getIdSender());
            preparedStatement.setInt(2, wallMassage.getIdReceiving());
            preparedStatement.setString(3, wallMassage.getMassage());
            preparedStatement.setString(4, wallMassage.getPicture());
            preparedStatement.setBoolean(5, wallMassage.isEdited());
            System.out.println(preparedStatement);
            int rows = preparedStatement.executeUpdate();
            System.out.println("Added " + rows + " rows");
        } catch (Exception ex) {
            System.out.println("Connection failed... createAccount");
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

    public List<WallMassage> readWallMassageUserId(int id) {
        System.out.println("readWallMassageUserId - start");
        List<WallMassage> massageList = new ArrayList<WallMassage>();
        String sql = "SELECT * FROM wallmassage WHERE idReceiving = ?";
        System.out.println(sql);
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                WallMassage massage = new WallMassage();
                massage.setIdSender(resultSet.getInt(2));
                massage.setIdReceiving(resultSet.getInt(3));
                massage.setMassage(resultSet.getString(4));
                massage.setPicture(resultSet.getString(5));
                massage.setPublicationDate(resultSet.getDate(6));
                //massage.setEdited(resultSet.getBoolean(6));
                massageList.add(massage);
            }
        } catch (Exception ex) {
            System.out.println("Connection failed...readWallMassageUserId");
            System.out.println(ex);
        }
        System.out.println("readWallMassageUserId - finish");
        connectionPool.returnConnection(connection);
        return massageList;
    }

    public List<WallMassage> readWallMassageUserIdNameSender(int id) {
        System.out.println("readWallMassageUserIdNameSender - start");
        List<WallMassage> massageList = new ArrayList<WallMassage>();
        String sql = "SELECT username, massage, picture, publicationDate FROM account JOIN wallmassage " +
                "ON idAccount = idSender WHERE idReceiving = ?";
        System.out.println(sql);
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                WallMassage massage = new WallMassage();
                massage.setUsernameSender(resultSet.getString(1));
                massage.setMassage(resultSet.getString(2));
                massage.setPicture(resultSet.getString(3));
                massage.setPublicationDate(resultSet.getDate(4));
                massageList.add(massage);
            }
        } catch (Exception ex) {
            System.out.println("Connection failed...readWallMassageUserId");
            System.out.println(ex);
        }
        System.out.println("readWallMassageUserId - finish");
        connectionPool.returnConnection(connection);
        return massageList;
    }

}

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class WallMassageDao {
    private String url;
    private String username;
    private String password;
    private Connection connection;

    WallMassageDao(){
        System.out.println("WallMassageDao - start");
        try (InputStream input = AccountDao.class.getClassLoader().getResourceAsStream("database.properties")) {
            Properties prop = new Properties();
            if (input == null) {
                throw new Exception("Sorry, unable to find database.properties");
            }
            System.out.println("try read properties ");
            prop.load(input);
            url = prop.getProperty("url");
            username = prop.getProperty("username");
            password = prop.getProperty("password");
            System.out.println("url = " + url);
            System.out.println("username = " + username);
            System.out.println("password = " + password);

        } catch (IOException ex) {
            System.out.println(ex);
            ex.printStackTrace();
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);//TODO connection pool
            connection.setAutoCommit(false);
        } catch (SQLException | ClassNotFoundException throwables) {
            System.out.println("Connection failed... WallMassageDao");
            throwables.printStackTrace();
        }
        System.out.println("WallMassageDao - finish");

    }

    public boolean create(WallMassage wallMassage){
        if (wallMassage.getMassage() == null) {
            return false;
        }
        String sql = "INSERT INTO wallmassage(idSender, idReceiving, massage, picture, publicationDate, edited) " +
                "VALUES (?,?,?,?, NOW(), ?);";
        System.out.println(sql);
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){//TODO
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
        }finally {
            try {
                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public List<WallMassage> readWallMassageUserId(int id) {
        System.out.println("readWallMassageUserId - start");
        List<WallMassage>  massageList = new ArrayList<WallMassage>();
        String sql = "SELECT * FROM wallmassage WHERE idReceiving = ?";
        System.out.println(sql);
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                WallMassage massage = new WallMassage();
                massage.setIdSender(resultSet.getInt(2));
                massage.setIdReceiving(resultSet.getInt(3));
                massage.setMassage(resultSet.getString(4));
                massage.setPicture(resultSet.getString(5));
                //massage.setPublicationDate(resultSet.getDate(5));
                //massage.setEdited(resultSet.getBoolean(6));
                massageList.add(massage);
                }
        } catch (Exception ex) {
            System.out.println("Connection failed...readWallMassageUserId");
            System.out.println(ex);
        }
        System.out.println("readWallMassageUserId - finish");
        return massageList;
    }
}

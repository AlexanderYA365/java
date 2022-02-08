import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class MassageDao {
    private String url;
    private String username;
    private String password;
    private Connection connection;

    MassageDao() {
        System.out.println("MassageDao - start");
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
            System.out.println("Connection failed... MassageDao");
            throwables.printStackTrace();
        }
        System.out.println("MassageDao - finish");

    }

    public boolean create(Massage massage) {
        if (massage.getMassage() == null) {
            return false;
        }
        String sql = "INSERT INTO massage(idSender, idReceiving, massage, picture, publicationDate, edited) " +
                "VALUES (?,?,?,?, NOW(), ?);";
        System.out.println(sql);
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, massage.getIdSender());
            preparedStatement.setInt(2, massage.getIdReceiving());
            preparedStatement.setString(3, massage.getMassage());
            preparedStatement.setString(4, massage.getPicture());
            preparedStatement.setBoolean(5, massage.isEdited());
            System.out.println(preparedStatement);
            int rows = preparedStatement.executeUpdate();
            System.out.println("Added " + rows + " rows");
        } catch (Exception ex) {
            System.out.println("Connection failed... MassageDao");
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

    public List<Massage> readMassageUserId(int id) {
        System.out.println("readMassageDaoId - start");
        List<Massage> massageList = new ArrayList<Massage>();
        String sql = "SELECT * FROM massage WHERE idReceiving = ?";
        System.out.println(sql);
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Massage massage = new Massage();
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
        return massageList;
    }

}

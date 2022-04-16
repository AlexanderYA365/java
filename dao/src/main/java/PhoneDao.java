import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PhoneDao {

    private Pool connectionPool;
    private static PhoneDao phoneDAO;

    private PhoneDao() {
        connectionPool = ConnectionPool.getInstance();
    }

    public static PhoneDao getInstance() {
        if (phoneDAO == null) {
            phoneDAO = new PhoneDao();
        }
        return phoneDAO;
    }

    public boolean create(Phone phone) {
        String sql = "INSERT INTO phone(id, phonenumber, phonetype) " +
                "VALUES (?,?,?);";
        System.out.println(sql);
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, phone.getIdAccount());
            preparedStatement.setString(2, phone.getPhoneNumber());
            preparedStatement.setInt(3, phone.getPhoneType());
            System.out.println(preparedStatement);
            int rows = preparedStatement.executeUpdate();
            System.out.println("Added " + rows + " rows");
            connection.commit();
        } catch (Exception ex) {
            System.out.println("Connection failed... MassageDao");
            System.out.println(ex);
        }
        return true;
    }

    public List<Phone> read(int id) {
        List<Phone> phones = new ArrayList<>();
        String sql = "SELECT * FROM phone WHERE idaccount = ?;";
        System.out.println(sql);
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Phone phone = new Phone();
                phone.setIdAccount(resultSet.getInt(1));
                phone.setPhoneNumber(resultSet.getString(2));
                phone.setPhoneType(resultSet.getInt(3));
                phones.add(phone);
            }
        } catch (Exception ex) {
            System.out.println("Connection failed... PhoneDao.read");
            System.out.println(ex);
        }
        return phones;
    }

    public boolean update(Phone phone) {
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement query = connection.prepareStatement(
                "UPDATE phone SET phonenumber = ?, phonetype = ? WHERE IdAccount = ?")) {
            query.setString(1, phone.getPhoneNumber());
            query.setString(2, phone.getPhoneNumber());
            query.setInt(3, phone.getIdAccount());
            int rows = query.executeUpdate();
            System.out.println("Updated rows = " + rows);
        } catch (Exception ex) {
            System.out.println("Connection failed...updateFriend");
            System.out.println(ex);
        }
        return true;
    }

    public boolean delete(Phone phone) {
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement query = connection.prepareStatement("DELETE FROM phone WHERE IdAccount = ? AND phonenumber = ?")) {
            query.setInt(1, phone.getIdAccount());
            query.setString(2, phone.getPhoneNumber());
            int rows = query.executeUpdate();
            System.out.println("Delete rows = " + rows);
        } catch (SQLException throwables) {
            System.out.println("Connection failed...deleteFriend");

            throwables.printStackTrace();
        }
        return true;
    }

}

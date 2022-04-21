import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PhoneDao {
    private final JNDIPool connectionPool;

    public PhoneDao() {
        connectionPool = JNDIPool.getInstance();
    }

    public boolean create(Phone phone) {
        String sql = "INSERT INTO phone(id, phonenumber, phonetype) " +
                "VALUES (?,?,?);";
        System.out.println(sql);
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, phone.getIdAccount());
            preparedStatement.setString(2, phone.getPhoneNumber());
            preparedStatement.setInt(3, phone.getPhoneType());
            int rows = preparedStatement.executeUpdate();
            System.out.println("Added " + rows + " rows");
            } catch (Exception ex) {
                System.out.println("create Exception - " + ex);
            }
        } catch (Exception ex) {
            System.out.println("create Exception - " + ex);
        }
        return true;
    }

    public List<Phone> read(int id) {
        List<Phone> phones = new ArrayList<>();
        String sql = "SELECT * FROM phone WHERE idaccount = ?;";
        System.out.println(sql);
        try (Connection connection = connectionPool.getConnection()) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Phone phone = new Phone();
                    phone.setIdAccount(resultSet.getInt(1));
                    phone.setPhoneNumber(resultSet.getString(2));
                    phone.setPhoneType(resultSet.getInt(3));
                    phones.add(phone);
                }
            }
        } catch (Exception ex) {
            System.out.println("read Exception - " + ex);
        }
        } catch (Exception ex) {
            System.out.println("read Exception - " + ex);
        }
        return phones;
    }

    public boolean update(Phone phone) {
        String sql ="UPDATE phone SET phonenumber = ?, phonetype = ? WHERE IdAccount = ?";
        try (Connection connection = connectionPool.getConnection()) {
        try (PreparedStatement query = connection.prepareStatement(sql)) {
            query.setString(1, phone.getPhoneNumber());
            query.setString(2, phone.getPhoneNumber());
            query.setInt(3, phone.getIdAccount());
            int rows = query.executeUpdate();
            System.out.println("Updated rows = " + rows);
        } catch (Exception ex) {
            System.out.println("update Exception - " + ex);
        }        } catch (Exception ex) {
            System.out.println("update Exception - " + ex);
        }
        return true;
    }

    public boolean delete(Phone phone) {
        String sql = "DELETE FROM phone WHERE IdAccount = ? AND phonenumber = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement query = connection.prepareStatement(sql)) {
            query.setInt(1, phone.getIdAccount());
            query.setString(2, phone.getPhoneNumber());
            int rows = query.executeUpdate();
            System.out.println("Delete rows = " + rows);
            } catch (SQLException ex) {
                System.out.println("delete Exception - " + ex);
            }
        } catch (SQLException ex) {
            System.out.println("delete Exception - " + ex);
        }
        return true;
    }

}
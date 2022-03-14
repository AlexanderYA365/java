import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MassageDao {
    private Connection connection;
    private Pool connectionPool;

    MassageDao() {
        connectionPool = ConnectionPool.getInstance();
    }

    public boolean create(Massage massage) {
        if (massage.getMassage() == null) {
            return false;
        }//TODO
        String sql = "INSERT INTO massage(idSender, idReceiving, massage, picture, publicationDate, edited) " +
                "VALUES (?,?,?,?, NOW(), ?);";
        System.out.println(sql);
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, massage.getIdSender());
            preparedStatement.setInt(2, massage.getIdReceiving());
            preparedStatement.setString(3, massage.getMassage());
            preparedStatement.setString(4, massage.getPicture());
            preparedStatement.setBoolean(5, massage.isEdited());
            System.out.println(preparedStatement);
            int rows = preparedStatement.executeUpdate();
            System.out.println("Added " + rows + " rows");
            connection.commit();
        } catch (Exception ex) {
            System.out.println("Connection failed... MassageDao");
            System.out.println(ex);
        }
        connectionPool.returnConnection(connection);
        return true;
    }

    public List<Massage> readMassageUserId(int id) {
        System.out.println("readMassageDaoId - start");
        List<Massage> massageList = new ArrayList<Massage>();
        String sql = "SELECT * FROM massage WHERE idReceiving = ?";
        System.out.println(sql);
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Massage massage = new Massage();
                massage.setId(resultSet.getInt(1));
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
        connectionPool.returnConnection(connection);
        System.out.println("readWallMassageUserId - finish");
        return massageList;
    }

    public List<Massage> readMassageUserIdNameSender(int id) {
        System.out.println("readMassageUserIdNameSender - start");
        List<Massage> massageList = new ArrayList<Massage>();
        String sql = "SELECT id, idSender, idReceiving, username, massage, picture, publicationDate FROM account JOIN massage " +
                "ON idAccount = idSender WHERE idReceiving = ?";
        System.out.println(sql);
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Massage massage = new Massage();
                massage.setId(resultSet.getInt(1));
                massage.setIdSender(resultSet.getInt(2));
                massage.setIdReceiving(resultSet.getInt(3));
                massage.setUsernameSender(resultSet.getString(4));
                massage.setMassage(resultSet.getString(5));
                massage.setPicture(resultSet.getString(6));
                massage.setPublicationDate(resultSet.getDate(7));
                massageList.add(massage);
            }
        } catch (Exception ex) {
            System.out.println("Connection failed...readWallMassageUserId");
            System.out.println(ex);
        }
        connectionPool.returnConnection(connection);
        System.out.println("readWallMassageUserId - finish");
        return massageList;
    }

    public List<Massage> readsMassageAccounts(int idSender, int idReceiving){
        System.out.println("readsMassageAccounts - start");
        List<Massage> massageList = new ArrayList<Massage>();
        String sql = "SELECT id, idSender, idReceiving, a.name, b.name, massage, picture, publicationDate, edited FROM massage " +
                        "INNER JOIN account a ON idReceiving = a.idAccount " +
                        "INNER JOIN account b ON idSender = b.idAccount " +
                        "WHERE (idSender = ? AND idReceiving = ?) OR (idSender = ? AND idReceiving = ?)";
        System.out.println(sql);
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, idSender);
            preparedStatement.setInt(2, idReceiving);
            preparedStatement.setInt(3, idReceiving);
            preparedStatement.setInt(4, idSender);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Massage massage = new Massage();
                massage.setId(resultSet.getInt(1));
                massage.setIdSender(resultSet.getInt(2));
                massage.setIdReceiving(resultSet.getInt(3));
                massage.setUsernameReceiving(resultSet.getString(4));
                massage.setUsernameSender(resultSet.getString(5));
                massage.setMassage(resultSet.getString(6));
                massage.setPicture(resultSet.getString(7));
                massage.setPublicationDate(resultSet.getDate(8));
                //massage.setEdited(resultSet.getBoolean(6));
                massageList.add(massage);
            }
        } catch (Exception ex) {
            System.out.println("Connection failed...readsMassageAccounts");
            System.out.println(ex);
        }
        connectionPool.returnConnection(connection);
        System.out.println("readsMassageAccounts - finish");
        return massageList;
    }

}

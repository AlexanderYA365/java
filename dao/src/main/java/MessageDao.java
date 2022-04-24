import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MessageDao {
    private final JNDIPool connectionPool;

    public MessageDao() {
        connectionPool = JNDIPool.getInstance();
    }

    public boolean create(Message message) {
        String sql = "INSERT INTO massage(idSender, idReceiving, massage, picture, publicationDate, edited, messageType) " +
                "VALUES (?,?,?,?, NOW(), ?, ?);";
        System.out.println(sql);
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, message.getIdSender());
                preparedStatement.setInt(2, message.getIdReceiving());
                preparedStatement.setString(3, message.getMessage());
                preparedStatement.setString(4, message.getPicture());
                preparedStatement.setBoolean(5, message.isEdited());
                preparedStatement.setInt(6, message.getMessageType());
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

    public List<Message> readMessageUserId(int id) {
        System.out.println("readMessageDaoId - start");
        List<Message> massageList = new ArrayList<>();
        String sql = "SELECT * FROM massage WHERE idReceiving = ?";
        System.out.println(sql);
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, id);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Message message = new Message();
                        message.setId(resultSet.getInt(1));
                        message.setIdSender(resultSet.getInt(2));
                        message.setIdReceiving(resultSet.getInt(3));
                        message.setMessage(resultSet.getString(4));
                        message.setPicture(resultSet.getString(5));
                        message.setPublicationDate(resultSet.getDate(6));
                        message.setEdited(resultSet.getBoolean(7));
                        message.setMessageType(resultSet.getInt(8));
                        massageList.add(message);
                    }
                }
            } catch (Exception ex) {
                System.out.println("readMessageUserId Exception - " + ex);
            }
        } catch (Exception ex) {
            System.out.println("readMessageUserId Exception - " + ex);
        }
        return massageList;
    }

    public List<Message> readMessageUserIdNameSender(int id) {
        System.out.println("readMessageUserIdNameSender - start");
        List<Message> messageList = new ArrayList<>();
        String sql = "SELECT id, idSender, idReceiving, name, massage, picture, publicationDate FROM account JOIN massage " +
                "ON idAccount = idSender WHERE idReceiving = ? AND messageType = 1";
        System.out.println(sql);
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, id);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Message message = new Message();
                        message.setId(resultSet.getInt(1));
                        message.setIdSender(resultSet.getInt(2));
                        message.setIdReceiving(resultSet.getInt(3));
                        message.setUsernameSender(resultSet.getString(4));
                        message.setMessage(resultSet.getString(5));
                        message.setPicture(resultSet.getString(6));
                        message.setPublicationDate(resultSet.getDate(7));
                        messageList.add(message);
                    }
                }
            } catch (Exception ex) {
                System.out.println("readMessageUserIdNameSender Exception - " + ex);
            }
        } catch (Exception ex) {
            System.out.println("readMessageUserIdNameSender Exception - " + ex);
        }
        return messageList;
    }

    public List<Message> readsMessageAccounts(int idSender, int idReceiving) {
        System.out.println("readsMessageAccounts - start");
        List<Message> massageList = new ArrayList<>();
        String sql = "SELECT id, idSender, idReceiving, a.name, b.name, massage, picture, publicationDate, edited, messageType FROM massage " +
                "INNER JOIN account a ON idReceiving = a.idAccount " +
                "INNER JOIN account b ON idSender = b.idAccount " +
                "WHERE (idSender = ? AND idReceiving = ?) OR (idSender = ? AND idReceiving = ?) AND messageType = 1";
        System.out.println(sql);
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, idSender);
                preparedStatement.setInt(2, idReceiving);
                preparedStatement.setInt(3, idReceiving);
                preparedStatement.setInt(4, idSender);
                fillMessage(massageList, preparedStatement);
            } catch (Exception ex) {
                System.out.println("readsMessageAccounts Exception - " + ex);
            }
        } catch (Exception ex) {
            System.out.println("readsMessageAccounts Exception - " + ex);
        }
        return massageList;
    }

    public List<Message> readWallMassage(int id) {
        System.out.println("readWallMassage - start");
        List<Message> massageList = new ArrayList<>();
        String sql = "SELECT id, idSender, idReceiving, a.name, b.name, massage, picture, publicationDate, edited, messageType FROM massage " +
                "INNER JOIN account a ON idReceiving = a.idAccount " +
                "INNER JOIN account b ON idSender = b.idAccount " +
                "WHERE idReceiving = ? AND messageType = 0";
        System.out.println(sql);
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, id);
                fillMessage(massageList, preparedStatement);
            } catch (Exception ex) {
                System.out.println("readsMessageAccounts Exception - " + ex);
            }
        } catch (Exception ex) {
            System.out.println("readsMessageAccounts Exception - " + ex);
        }
        return massageList;
    }

    private void fillMessage(List<Message> massageList, PreparedStatement preparedStatement) {
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Message message = new Message();
                message.setId(resultSet.getInt(1));
                message.setIdSender(resultSet.getInt(2));
                message.setIdReceiving(resultSet.getInt(3));
                message.setUsernameReceiving(resultSet.getString(4));
                message.setUsernameSender(resultSet.getString(5));
                message.setMessage(resultSet.getString(6));
                message.setPicture(resultSet.getString(7));
                message.setPublicationDate(resultSet.getDate(8));
                message.setEdited(resultSet.getBoolean(9));
                message.setMessageType(resultSet.getInt(10));
                massageList.add(message);
            }
        } catch (SQLException ex) {
            System.out.println("fillMessage exception - " + ex);
        }
    }
}
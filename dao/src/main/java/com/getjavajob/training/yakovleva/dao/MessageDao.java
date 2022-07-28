package com.getjavajob.training.yakovleva.dao;

import com.getjavajob.training.yakovleva.common.Message;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class MessageDao {
    private final JdbcTemplate jdbcTemplate;

    public MessageDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean create(Message message) {
        String sql = "INSERT INTO message(sender_id, receiver_id, message, picture, publication_date," +
                " edited, message_type) " +
                "VALUES (?,?,?,?, NOW(), ?, ?);";
        System.out.println(sql);
        int result = jdbcTemplate.update(sql, message.getSenderId(), message.getReceiverId(),
                message.getMessage(), message.getPicture(),
                message.isEdited(), message.getMessageType());
        return result > 0;
    }

    public List<Message> getMessageUserId(int id) {
        System.out.println("readMessageDaoId - start");
        String sql = "SELECT * FROM message WHERE receiver_id = ?";
        System.out.println(sql);
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (resultSet, i) -> fillMessages(resultSet));
    }

    public List<Message> getMessageUserIdNameSender(int receiverId) {
        System.out.println("readMessageUserIdNameSender");
        String sql = "SELECT id, sender_id, receiver_id, name, message, picture, publication_date, edited, message_type FROM account JOIN message " +
                "ON account_id = sender_id WHERE receiver_id =? OR sender_id =? AND message_type = 1;";
        System.out.println(sql);
        return jdbcTemplate.queryForObject(sql, new Object[]{receiverId, receiverId}, (resultSet, i) -> fillMessagesAccount(resultSet));
    }

    public List<Message> getUniqueMessagesForUser(int receiverId) {
        System.out.println("getUniqueMessagesForUser");
        System.out.println("receiverId - " + receiverId);
        String sql = "SELECT id, sender_id, receiver_id, name, message, picture, publication_date, edited, message_type FROM account JOIN message " +
                "ON account_id = sender_id WHERE sender_id = ? OR receiver_id = ? AND message_type = 1 GROUP BY sender_id;";
        System.out.println(sql);
        List<Message> messages = new ArrayList<>();
        try {
            messages = jdbcTemplate.queryForObject(sql, new Object[]{receiverId, receiverId},
                    (resultSet, i) -> fillMessagesAccount(resultSet));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        System.out.println("mes - " + messages);
        return messages;
    }

    public List<Message> getMessageAccounts(int senderId, int receiverId) {
        System.out.println("getMessageAccounts");
        String sql = "SELECT id, sender_id, receiver_id, a.name, b.name, message, picture, publication_date, " +
                "edited, message_type FROM message " +
                "INNER JOIN account a ON receiver_id = a.account_id " +
                "INNER JOIN account b ON sender_id = b.account_id " +
                "WHERE (sender_id = ? AND receiver_id = ?) OR (sender_id = ? AND receiver_id = ?) AND message_type = 1";
        System.out.println(sql);
        List<Message> messages = new ArrayList<>();
        try {
            messages = jdbcTemplate.queryForObject(sql, new Object[]{senderId, receiverId, receiverId, senderId},
                    (resultSet, i) -> fillMessages(resultSet));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return messages;
    }

    public List<Message> getWallMessage(int receivingId) {
        System.out.println("readWallMessage - start");
        String sql = "SELECT id, sender_id, receiver_id, a.name, b.name, message, picture, publication_date, edited, " +
                "message_type FROM message " +
                "INNER JOIN account a ON receiver_id = a.account_id " +
                "INNER JOIN account b ON sender_id = b.account_id " +
                "WHERE receiver_id = ? AND message_type = 0";
        System.out.println(sql);
        List<Message> messages = getMessages(receivingId, sql);
        return messages;
    }

//    private List<Message> getUniqueMessages(int receivingId, String sql) {
//        System.out.println("getUniqueMessages - start");
//        List<Message> messages = new ArrayList<>();
//        try {
//            messages = jdbcTemplate.queryForObject(sql,
//                    new Object[]{receivingId}, (resultSet, i) -> fillMessages(resultSet));
//            System.out.println("UniqueMessages - " + messages);
//        } catch (Exception ex) {
//            System.out.println(ex);
//        }
//        return getMessages(messages);
//    }

    private List<Message> getMessages(List<Message> messages) {
        if (messages.size() == 0) {
            Message message = new Message();
            message.setReceiverId(0);
            message.setId(0);
            message.setSenderId(0);
            message.setMessage("0");
            message.setPicture("0");
            message.setPublicationDate(new Date());
            message.setUsernameSender("0");
            message.setEdited(false);
            messages.add(message);
        }
        return messages;
    }

    private List<Message> getMessages(int receivingId, String sql) {
        System.out.println("getMessages - start");
        List<Message> messages = new ArrayList<>();
        try {
            messages = jdbcTemplate.queryForObject(sql,
                    new Object[]{receivingId}, (resultSet, i) -> fillMessages(resultSet));
            System.out.println("getMessages - " + messages);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return getMessages(messages);
    }

    public boolean delete(int id) {
        System.out.println("messageDao, delete(id) - " + id);
        String sql = "DELETE FROM message WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    private List<Message> fillMessagesAccount(ResultSet resultSet) throws SQLException {
        List<Message> messages = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Message message = new Message();
                message.setId(resultSet.getInt(1));
                message.setSenderId(resultSet.getInt(2));
                message.setReceiverId(resultSet.getInt(3));
                message.setUsernameSender(resultSet.getString(4));
                message.setMessage(resultSet.getString(5));
                message.setPicture(resultSet.getString(6));
                message.setPublicationDate(resultSet.getDate(7));
                message.setEdited(resultSet.getBoolean(8));
                message.setMessageType(resultSet.getInt(9));
                messages.add(message);
            }
        } catch (Exception ex) {
            System.out.println("ex - " + ex);
        }
        return messages;
    }

    private List<Message> fillMessages(ResultSet resultSet) throws SQLException {
        List<Message> messages = new ArrayList<>();
        while (resultSet.next()) {
            Message message = new Message();
            message.setId(resultSet.getInt(1));
            message.setSenderId(resultSet.getInt(2));
            message.setReceiverId(resultSet.getInt(3));
            message.setUsernameReceiving(resultSet.getString(4));
            message.setUsernameSender(resultSet.getString(5));
            message.setMessage(resultSet.getString(6));
            message.setPicture(resultSet.getString(7));
            message.setPublicationDate(resultSet.getDate(8));
            message.setEdited(resultSet.getBoolean(9));
            message.setMessageType(resultSet.getInt(10));
            messages.add(message);
        }
        return messages;
    }

}
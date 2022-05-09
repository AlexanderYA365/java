package com.getjavajob.training.yakovleva.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MessageDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MessageDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public boolean create(Message message) {
        String sql = "INSERT INTO massage(idSender, idReceiving, massage, picture, publicationDate," +
                " edited, messageType) " +
                "VALUES (?,?,?,?, NOW(), ?, ?);";
        System.out.println(sql);
        jdbcTemplate.update(sql, message.getIdSender(), message.getIdReceiving(),
                message.getMessage(), message.getPicture(),
                message.getPublicationDate(), message.isEdited(), message.getMessageType());
        return true;
    }

    public List<Message> readMessageUserId(int id) {
        System.out.println("readMessageDaoId - start");
        String sql = "SELECT * FROM massage WHERE idReceiving = ?";
        System.out.println(sql);
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (resultSet, i) -> fillMessages(resultSet));
    }

    public List<Message> readMessageUserIdNameSender(int idReceiving) {
        System.out.println("readMessageUserIdNameSender - start");
        String sql = "SELECT id, idSender, idReceiving, name, massage, picture, publicationDate FROM account JOIN massage " +
                "ON idAccount = idSender WHERE idReceiving = ? AND messageType = 1;";
        System.out.println(sql);
        return jdbcTemplate.queryForObject(sql, new Object[]{idReceiving}, (resultSet, i) -> fillMessagesAccount(resultSet));
    }

    public List<Message> readUniqueMessagesForUser(int idReceiving) {
        System.out.println("readMessage - start");
        String sql = "SELECT id, idSender, idReceiving, name, massage, picture, publicationDate FROM account JOIN massage " +
                "ON idAccount = idSender WHERE idReceiving = ? AND messageType = 1 GROUP BY idSender;";
        System.out.println(sql);
        return jdbcTemplate.queryForObject(sql, new Object[]{idReceiving}, (resultSet, i) -> fillMessagesAccount(resultSet));
    }

    public List<Message> readsMessageAccounts(int idSender, int idReceiving) {
        System.out.println("readsMessageAccounts - start");
        String sql = "SELECT id, idSender, idReceiving, a.name, b.name, massage, picture, publicationDate, " +
                "edited, messageType FROM massage " +
                "INNER JOIN account a ON idReceiving = a.idAccount " +
                "INNER JOIN account b ON idSender = b.idAccount " +
                "WHERE (idSender = ? AND idReceiving = ?) OR (idSender = ? AND idReceiving = ?) AND messageType = 1";
        System.out.println(sql);
        return jdbcTemplate.queryForObject(sql, new Object[]{idSender, idReceiving, idReceiving, idSender},
                (resultSet, i) -> fillMessages(resultSet));
    }

    public List<Message> readWallMassage(int idReceiving) {
        System.out.println("readWallMassage - start");
        String sql = "SELECT id, idSender, idReceiving, a.name, b.name, massage, picture, publicationDate, edited, " +
                "messageType FROM massage " +
                "INNER JOIN account a ON idReceiving = a.idAccount " +
                "INNER JOIN account b ON idSender = b.idAccount " +
                "WHERE idReceiving = ? AND messageType = 0";
        System.out.println(sql);
        return jdbcTemplate.queryForObject(sql, new Object[]{idReceiving}, (resultSet, i) -> fillMessages(resultSet));
    }

    private List<Message> fillMessagesAccount(ResultSet resultSet) throws SQLException {
        List<Message> messages = new ArrayList<>();
        while (resultSet.next()) {
            Message message = new Message();
            message.setId(resultSet.getInt(1));
            message.setIdSender(resultSet.getInt(2));
            message.setIdReceiving(resultSet.getInt(3));
            message.setUsernameSender(resultSet.getString(4));
            message.setMessage(resultSet.getString(5));
            message.setPicture(resultSet.getString(6));
            message.setPublicationDate(resultSet.getDate(7));
            messages.add(message);
        }
        return messages;
    }

    private List<Message> fillMessages(ResultSet resultSet) throws SQLException {
        List<Message> messages = new ArrayList<>();
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
            messages.add(message);
        }
        return messages;
    }

}
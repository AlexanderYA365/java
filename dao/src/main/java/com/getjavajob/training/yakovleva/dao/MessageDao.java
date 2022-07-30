package com.getjavajob.training.yakovleva.dao;

import com.getjavajob.training.yakovleva.common.Account;
import com.getjavajob.training.yakovleva.common.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class MessageDao {
    private SessionFactory sessionFactory;
    private static final Logger logger = LogManager.getLogger();

    public MessageDao(SessionFactory sessionFactory) {
        logger.info("MessageDao(sessionFactory)");
        this.sessionFactory = sessionFactory;
    }

    public boolean create(Message message) {
        logger.info("MessageDao.create(message)");
        logger.debug("MessageDao.create(message = {})", message);
        return sessionFactory.getCurrentSession().save(message) != null;
    }

    public List<Message> getMessageUserId(int id) {
        logger.info("MessageDao.getMessageUserId(id)");
        logger.debug("MessageDao.getMessageUserId(id = {})", id);
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Message> criteriaQuery = criteriaBuilder.createQuery(Message.class);
        Root<Message> from = criteriaQuery.from(Message.class);
        CriteriaQuery<Message> selectMessage = criteriaQuery.select(from).where(
                criteriaBuilder.equal(from.get("receiverId"), id));
        return session.createQuery(selectMessage).getResultList();
    }

    public List<Message> getMessageUserIdNameSender(int receiverId) {
        logger.info("MessageDao.getMessageUserIdNameSender(receiverId)");
        logger.debug("MessageDao.getMessageUserIdNameSender(receiverId = {})", receiverId);
        System.out.println("readMessageUserIdNameSender");

        String sql = "SELECT id, sender_id, receiver_id, name, message, picture, publication_date, edited," +
                " message_type FROM account JOIN message " +
                "ON account_id = sender_id WHERE receiver_id =? OR sender_id =? AND message_type = 1;";
        System.out.println(sql);
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Account> query = criteriaBuilder.createQuery(Account.class);
        Root<Account> root = query.from(Account.class);
        Join<Object, Object> messages = root.join("message");
        query.where(criteriaBuilder.or(criteriaBuilder.equal(messages.get("friendId"), root.get("id")),
                criteriaBuilder.equal(messages.get("accountId"), accountId)));
        return session.createQuery(query).getResultList();
//
//        Session session = sessionFactory.getCurrentSession();
//        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
//        CriteriaQuery<Message> criteriaQuery = criteriaBuilder.createQuery(Message.class);
//        Root<Message> from = criteriaQuery.from(Message.class);
//        ListJoin<Message,Account> join = from.join(Account.class);
//
//
//
//        return jdbcTemplate.queryForObject(sql, new Object[]{receiverId, receiverId}, (resultSet, i)
//                -> fillMessagesAccount(resultSet));
        return new ArrayList<>();
    }

    public List<Message> getUniqueMessagesForUser(int receiverId) {
        logger.info("MessageDao.getUniqueMessagesForUser(receiverId)");
        logger.debug("MessageDao.getUniqueMessagesForUser(receiverId = {})", receiverId);
        System.out.println("getUniqueMessagesForUser");
        System.out.println("receiverId - " + receiverId);
        String sql = "SELECT id, sender_id, receiver_id, name, message, picture, publication_date, edited, message_type" +
                " FROM account JOIN message " +
                "ON account_id = sender_id WHERE sender_id = ? OR receiver_id = ? AND message_type = 1 GROUP BY sender_id;";
        System.out.println(sql);
//        List<Message> messages = new ArrayList<>();
//        try {
//            messages = jdbcTemplate.queryForObject(sql, new Object[]{receiverId, receiverId},
//                    (resultSet, i) -> fillMessagesAccount(resultSet));
//        } catch (Exception ex) {
//            System.out.println(ex);
//        }
//        System.out.println("mes - " + messages);
//        return messages;
        return new ArrayList<>();
    }

    public List<Message> getMessageAccounts(int senderId, int receiverId) {
        logger.info("MessageDao.getMessageAccounts(senderId, receiverId)");
        logger.debug("MessageDao.getMessageAccounts(senderId = {}, receiverId = {})", senderId, receiverId);
        System.out.println("getMessageAccounts");
        String sql = "SELECT id, sender_id, receiver_id, a.name, b.name, message, picture, publication_date, " +
                "edited, message_type FROM message " +
                "INNER JOIN account a ON receiver_id = a.account_id " +
                "INNER JOIN account b ON sender_id = b.account_id " +
                "WHERE (sender_id = ? AND receiver_id = ?) OR (sender_id = ? AND receiver_id = ?) AND message_type = 1";
        System.out.println(sql);
//        List<Message> messages = new ArrayList<>();
//        try {
//            messages = jdbcTemplate.queryForObject(sql, new Object[]{senderId, receiverId, receiverId, senderId},
//                    (resultSet, i) -> fillMessages(resultSet));
//        } catch (Exception ex) {
//            System.out.println(ex);
//        }
//        return messages;
        return new ArrayList<>();
    }

    public List<Message> getWallMessage(int receiverId) {
        logger.info("MessageDao.getWallMessage(receiverId)");
        logger.debug("MessageDao.getWallMessage(receiverId = {})", receiverId);
        System.out.println("readWallMessage - start");
        String sql = "SELECT id, sender_id, receiver_id, a.name, b.name, message, picture, publication_date, edited, " +
                "message_type FROM message " +
                "INNER JOIN account a ON receiver_id = a.account_id " +
                "INNER JOIN account b ON sender_id = b.account_id " +
                "WHERE receiver_id = ? AND message_type = 0";
        System.out.println(sql);
//        List<Message> messages = getMessages(receivingId, sql);
//        return messages;
        return new ArrayList<>();
    }

    public boolean delete(int id) {
        logger.info("MessageDao.delete(receiverId)");
        logger.debug("MessageDao.delete(id = {})", id);
        Session session = sessionFactory.getCurrentSession();
        Message deleteMessage = session.find(Message.class, id);
        session.delete(deleteMessage);
        return true;
    }

}
package com.getjavajob.training.yakovleva.dao;

import com.getjavajob.training.yakovleva.common.Account;
import com.getjavajob.training.yakovleva.common.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.List;

@Repository
@Transactional
public class MessageDao {
    private static final Logger logger = LogManager.getLogger();
    private SessionFactory sessionFactory;

    public MessageDao(SessionFactory sessionFactory) {
        logger.info("MessageDao(sessionFactory)");
        this.sessionFactory = sessionFactory;
    }

    public boolean create(Message message) {
        logger.info("MessageDao.create(message)");
        logger.info("MessageDao.create(message = {})", message);
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
        String sql = "SELECT id, sender_id, receiver_id, name, message, picture, publication_date, edited," +
                " message_type FROM account JOIN message " +
                "ON account_id = sender_id WHERE receiver_id =? OR sender_id =? AND message_type = 1;";
        System.out.println(sql);
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Account> query = criteriaBuilder.createQuery(Account.class);
        Root<Account> fromAccount = query.from(Account.class);
        Join<Object, Object> messages = fromAccount.join("message");
        Predicate receiverIdOrSenderId = criteriaBuilder.or(
                criteriaBuilder.equal(messages.get("receiverId"), receiverId),
                criteriaBuilder.equal(messages.get("senderId"), receiverId));
        Predicate andMessageType = criteriaBuilder.and(criteriaBuilder.equal(messages.get("messageType"), 1));
        query.where(criteriaBuilder.and(andMessageType, receiverIdOrSenderId));
        return session.createQuery(query).getSingleResult().getMessage();
    }

    public List<Message> getUniqueMessagesForUser(int receiverId) {
        logger.info("MessageDao.getUniqueMessagesForUser(receiverId)");
        logger.info("MessageDao.getUniqueMessagesForUser(receiverId = {})", receiverId);
        String sql = "SELECT id, sender_id, receiver_id, name, message, picture, publication_date, edited, message_type" +
                " FROM account JOIN message " +
                "ON account_id = sender_id WHERE sender_id = ? OR receiver_id = ? AND message_type = 1 GROUP BY sender_id;";
        System.out.println(sql);
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Account> query = criteriaBuilder.createQuery(Account.class);
        Root<Account> fromAccount = query.from(Account.class);
        Join<Account, Message> messages = fromAccount.join("message", JoinType.INNER);
        Predicate receiverIdOrSenderId = criteriaBuilder.or(
                criteriaBuilder.equal(messages.get("receiverId"), receiverId),
                criteriaBuilder.equal(messages.get("senderId"), receiverId));
        Predicate andMessageType = criteriaBuilder.and(
                criteriaBuilder.equal(messages.get("messageType"), 1),
                receiverIdOrSenderId);
        query.where(andMessageType).groupBy(messages.get("senderId"));
        return session.createQuery(query).getResultList().get(0).getMessage();
    }

    public List<Message> getMessageAccounts(int senderId, int receiverId) {
        logger.info("MessageDao.getMessageAccounts(senderId, receiverId)");
        logger.info("MessageDao.getMessageAccounts(senderId = {}, receiverId = {})", senderId, receiverId);
        String sql = "SELECT id, sender_id, receiver_id, a.name, b.name, message, picture, publication_date, " +
                "edited, message_type FROM message " +
                "INNER JOIN account a ON receiver_id = a.account_id " +
                "INNER JOIN account b ON sender_id = b.account_id " +
                "WHERE (sender_id = ? AND receiver_id = ?) OR (sender_id = ? AND receiver_id = ?) AND message_type = 1";
        System.out.println(sql);
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Message> query = criteriaBuilder.createQuery(Message.class);
        Root<Message> root = query.from(Message.class);
        Join<Account, Message> accounts = root.join("account", JoinType.INNER);
        Join<Account, Message> accounts1 = root.join("account", JoinType.INNER);
        Predicate and1 = criteriaBuilder.and(
                criteriaBuilder.equal(root.get("senderId"), senderId),
                criteriaBuilder.equal(root.get("receiverId"), receiverId));
        Predicate and2 = criteriaBuilder.and(
                criteriaBuilder.equal(root.get("senderId"), receiverId),
                criteriaBuilder.equal(root.get("receiverId"), senderId));
        Predicate or = criteriaBuilder.or(and1, and2);
        Predicate all = criteriaBuilder.and(criteriaBuilder.equal(root.get("messageType"), 1),
                or);
        query.where(all);
        return session.createQuery(query).getResultList();
    }

    public List<Message> getWallMessage(int receiverId) {
        logger.info("MessageDao.getWallMessage(receiverId)");
        logger.debug("MessageDao.getWallMessage(receiverId = {})", receiverId);
        String sql = "SELECT id, sender_id, receiver_id, a.name, b.name, message, picture, publication_date, edited, " +
                "message_type FROM message " +
                "INNER JOIN account a ON receiver_id = a.account_id " +
                "INNER JOIN account b ON sender_id = b.account_id " +
                "WHERE receiver_id = ? AND message_type = 0";
        System.out.println(sql);
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Message> query = criteriaBuilder.createQuery(Message.class);
        Root<Message> root = query.from(Message.class);
        Join<Account, Message> accounts = root.join("account", JoinType.INNER);
        Join<Account, Message> accounts1 = root.join("account", JoinType.INNER);
        Predicate and = criteriaBuilder.and(
                criteriaBuilder.equal(root.get("receiverId"), receiverId),
                criteriaBuilder.equal(root.get("messageType"), 0));
        query.where(and);
        return session.createQuery(query).getResultList();
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
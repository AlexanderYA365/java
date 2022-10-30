package com.getjavajob.training.yakovleva.dao;

import com.getjavajob.training.yakovleva.common.Account;
import com.getjavajob.training.yakovleva.common.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.*;
import java.util.List;

@Repository
@Transactional
public class MessageDao {
    private static final Logger logger = LogManager.getLogger();
    private EntityManagerFactory entityManagerFactory;

    public MessageDao(EntityManagerFactory entityManagerFactory) {
        logger.info("MessageDao(sessionFactory)");
        this.entityManagerFactory = entityManagerFactory;
    }

    public boolean create(Message message) {
        logger.info("MessageDao.create(message)");
        logger.info("MessageDao.create(message = {})", message);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            entityManager.persist(message);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
        }
        entityManager.close();
        return true;
    }

    public List<Message> getMessageUserId(int id) {
        logger.info("MessageDao.getMessageUserId(id)");
        logger.debug("MessageDao.getMessageUserId(id = {})", id);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Message> criteriaQuery = criteriaBuilder.createQuery(Message.class);
        Root<Message> from = criteriaQuery.from(Message.class);
        CriteriaQuery<Message> selectMessage = criteriaQuery.select(from).where(
                criteriaBuilder.equal(from.get("receiverId"), id));
        List<Message> messages = entityManager.createQuery(selectMessage).getResultList();
        entityManager.close();
        return messages;
    }

    public List<Message> getMessageUserIdNameSender(int receiverId) {
        logger.info("MessageDao.getMessageUserIdNameSender(receiverId)");
        logger.debug("MessageDao.getMessageUserIdNameSender(receiverId = {})", receiverId);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Account> query = criteriaBuilder.createQuery(Account.class);
        Root<Account> fromAccount = query.from(Account.class);
        Join<Object, Object> messages = fromAccount.join("message");
        Predicate receiverIdOrSenderId = criteriaBuilder.or(
                criteriaBuilder.equal(messages.get("receiverId"), receiverId),
                criteriaBuilder.equal(messages.get("senderId"), receiverId));
        Predicate andMessageType = criteriaBuilder.and(criteriaBuilder.equal(messages.get("messageType"), 1));
        query.where(criteriaBuilder.and(andMessageType, receiverIdOrSenderId));
        List<Message> userMessage = entityManager.createQuery(query).getSingleResult().getMessage();
        entityManager.close();
        return userMessage;
    }

    public List<Message> getUniqueMessagesForUser(int receiverId) {
        logger.info("MessageDao.getUniqueMessagesForUser(receiverId)");
        logger.info("MessageDao.getUniqueMessagesForUser(receiverId = {})", receiverId);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Message> query = criteriaBuilder.createQuery(Message.class);
        Root<Message> from = query.from(Message.class);
        Predicate receiverIdOrSenderId = criteriaBuilder.or(
                criteriaBuilder.equal(from.get("receiverId"), receiverId),
                criteriaBuilder.equal(from.get("senderId"), receiverId));
        Predicate andMessageType = criteriaBuilder.and(
                criteriaBuilder.equal(from.get("messageType"), 1),
                receiverIdOrSenderId);
        query.where(andMessageType).groupBy(from.get("receiverId"));
        List<Message> messages = entityManager.createQuery(query).getResultList();
        entityManager.close();
        return messages;
    }

    public List<Message> getMessageAccounts(int senderId, int receiverId) {
        logger.info("MessageDao.getMessageAccounts(senderId, receiverId)");
        logger.info("MessageDao.getMessageAccounts(senderId = {}, receiverId = {})", senderId, receiverId);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
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
        List<Message> messages = entityManager.createQuery(query).getResultList();
        entityManager.close();
        return messages;
    }

    public List<Message> getWallMessage(int receiverId) {
        logger.info("MessageDao.getWallMessage(receiverId)");
        logger.debug("MessageDao.getWallMessage(receiverId = {})", receiverId);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Message> query = criteriaBuilder.createQuery(Message.class);
        Root<Message> root = query.from(Message.class);
        Join<Account, Message> accounts = root.join("account", JoinType.INNER);
        Join<Account, Message> accounts1 = root.join("account", JoinType.INNER);
        Predicate and = criteriaBuilder.and(
                criteriaBuilder.equal(root.get("receiverId"), receiverId),
                criteriaBuilder.equal(root.get("messageType"), 0));
        query.where(and);
        List<Message> messages = entityManager.createQuery(query).getResultList();
        entityManager.close();
        return messages;
    }

    public boolean delete(int id) {
        logger.info("MessageDao.delete(receiverId)");
        logger.debug("MessageDao.delete(id = {})", id);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            Message deleteMessage = entityManager.find(Message.class, id);
            entityManager.remove(deleteMessage);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
        }
        entityManager.close();
        return true;
    }

}
package com.getjavajob.training.yakovleva.dao;

import com.getjavajob.training.yakovleva.common.Account;
import com.getjavajob.training.yakovleva.common.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.List;

@Repository
public class MessageDao {
    private static final Logger logger = LogManager.getLogger(MessageDao.class);
    @PersistenceContext
    private EntityManager entityManager;

    public MessageDao() {
        logger.info("MessageDao(sessionFactory)");
    }

    @Transactional
    public boolean create(Message message) {
        logger.info("MessageDao.create(message = {})", message);
        entityManager.merge(message);
        return true;
    }

    public List<Message> getMessageUserId(int id) {
        logger.info("MessageDao.getMessageUserId(id = {})", id);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Message> criteriaQuery = criteriaBuilder.createQuery(Message.class);
        Root<Message> from = criteriaQuery.from(Message.class);
        CriteriaQuery<Message> selectMessage = criteriaQuery.select(from).where(
                criteriaBuilder.equal(from.get("receiverId"), id));
        return entityManager.createQuery(selectMessage).getResultList();
    }

    public List<Message> getMessageUserIdNameSender(int receiverId) {
        logger.info("MessageDao.getMessageUserIdNameSender(receiverId = {})", receiverId);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Account> query = criteriaBuilder.createQuery(Account.class);
        Root<Account> fromAccount = query.from(Account.class);
        Join<Object, Object> messages = fromAccount.join("message");
        Predicate receiverIdOrSenderId = criteriaBuilder.or(
                criteriaBuilder.equal(messages.get("receiverId"), receiverId),
                criteriaBuilder.equal(messages.get("senderId"), receiverId));
        Predicate andMessageType = criteriaBuilder.and(criteriaBuilder.equal(messages.get("messageType"), 1));
        query.where(criteriaBuilder.and(andMessageType, receiverIdOrSenderId));
        return entityManager.createQuery(query).getSingleResult().getMessage();
    }

    public List<Message> getUniqueMessagesForUser(int receiverId) {
        logger.info("MessageDao.getUniqueMessagesForUser(receiverId = {})", receiverId);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Message> query = criteriaBuilder.createQuery(Message.class);
        Root<Message> from = query.from(Message.class);
        Predicate receiverIdOrSenderId = criteriaBuilder.or(
                criteriaBuilder.equal(from.get("receiverId"), receiverId),
                criteriaBuilder.equal(from.get("senderId"), receiverId));
//        Subquery<Number> subQuery = query.subquery(Number.class);
//        Root maxTime = subQuery.from(Message.class);
//        subQuery.select(criteriaBuilder.max(maxTime.get("publicationDate")));
//        Predicate andMaxDate = criteriaBuilder.equal(from.get("publicationDate"), subQuery);
        Predicate andMessageType = criteriaBuilder.and(
                criteriaBuilder.equal(from.get("messageType"), 1),
//                andMaxDate,
                receiverIdOrSenderId);
        query.where(andMessageType).groupBy(from.get("receiverId"));
        return entityManager.createQuery(query).getResultList();
    }

    public List<Message> getGroupMessage(int idGroup) {
        logger.info("MessageDao.getGroupMessage(idGroup = {})", idGroup);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Message> query = criteriaBuilder.createQuery(Message.class);
        Root<Message> root = query.from(Message.class);
        Join<Account, Message> accounts = root.join("account", JoinType.INNER);
        Join<Account, Message> accounts1 = root.join("account", JoinType.INNER);
        Predicate and = criteriaBuilder.and(
                criteriaBuilder.equal(root.get("receiverId"), idGroup),
                criteriaBuilder.equal(root.get("messageType"), 2));
        query.where(and);
        return entityManager.createQuery(query).getResultList();
    }

    public List<Message> getMessageAccounts(int senderId, int receiverId) {
        logger.info("MessageDao.getMessageAccounts(senderId = {}, receiverId = {})", senderId, receiverId);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Message> query = criteriaBuilder.createQuery(Message.class);
        Root<Message> root = query.from(Message.class);
        Join<Account, Message> accounts = root.join("account", JoinType.INNER);
        Join<Account, Message> accounts1 = root.join("account", JoinType.INNER);
        Predicate andSenderReceiverById = criteriaBuilder.and(
                criteriaBuilder.equal(root.get("senderId"), senderId),
                criteriaBuilder.equal(root.get("receiverId"), receiverId));
        Predicate andReceiverSenderById = criteriaBuilder.and(
                criteriaBuilder.equal(root.get("senderId"), receiverId),
                criteriaBuilder.equal(root.get("receiverId"), senderId));
        Predicate orSenderOrReceiver = criteriaBuilder.or(andSenderReceiverById, andReceiverSenderById);
        Predicate allCriteria = criteriaBuilder.and(criteriaBuilder.equal(root.get("messageType"), 1),
                orSenderOrReceiver);
        query.where(allCriteria);
        return entityManager.createQuery(query).getResultList();
    }

    public List<Message> getWallMessage(int receiverId) {
        logger.info("MessageDao.getWallMessage(receiverId = {})", receiverId);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Message> query = criteriaBuilder.createQuery(Message.class);
        Root<Message> root = query.from(Message.class);
        Join<Account, Message> accounts = root.join("account", JoinType.INNER);
        Join<Account, Message> accounts1 = root.join("account", JoinType.INNER);
        Predicate and = criteriaBuilder.and(
                criteriaBuilder.equal(root.get("receiverId"), receiverId),
                criteriaBuilder.equal(root.get("messageType"), 0));
        query.where(and);
        return entityManager.createQuery(query).getResultList();
    }

    @Transactional
    public boolean delete(int id) {
        logger.info("MessageDao.delete(id = {})", id);
        Message deleteMessage = entityManager.find(Message.class, id);
        entityManager.remove(deleteMessage);
        return true;
    }

}
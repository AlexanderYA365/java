package com.getjavajob.training.yakovleva.service;

import com.getjavajob.training.yakovleva.common.Account;
import com.getjavajob.training.yakovleva.common.Message;
import com.getjavajob.training.yakovleva.dao.MessageDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class MessageService {
    private static final Logger logger = LogManager.getLogger();
    private MessageDao messageDao;

    @Autowired
    public MessageService(MessageDao messageDao) {
        logger.info("MessageService(MessageDao messageDao)");
        this.messageDao = messageDao;
    }

    public MessageService() {
    }

    public List<Message> getWallMassageAccount(Account account) {
        logger.info("getWallMassageAccount(Account account)");
        logger.debug("getWallMassageAccount(account = {})", account);
        return messageDao.getWallMessage(account.getId());
    }

    public List<Message> getMessages(Account account) {
        logger.info("getMessages(Account account)");
        logger.debug("getMessages(account = {})", account);
        return messageDao.getMessageUserIdNameSender(account.getId());
    }

    public List<Message> getUniqueMessages(Account account) {
        logger.info("getUniqueMessages(Account account)");
        logger.debug("getUniqueMessages(account = {})", account);
        return messageDao.getUniqueMessagesForUser(account.getId());
    }

    public List<Message> getAccountMessages(int senderId, int receiverId) {
        logger.info("getAccountMessages(int senderId, int receiverId)");
        logger.debug("getAccountMessages(senderId = {}, receiverId = {})", senderId, receiverId);
        return messageDao.getMessageAccounts(senderId, receiverId);
    }

    public boolean createMassage(Message message) {
        logger.info("createMassage(Message message)");
        logger.debug("createMassage(message = {})", message);
        try {
            return messageDao.create(message);
        } catch (Exception ex) {
            logger.debug("createMessage exception - {}", ex);
            return false;
        }
    }

    public boolean delete(int id) {
        logger.info("delete(int id)");
        logger.debug("delete(id = {})", id);
        return messageDao.delete(id);
    }

}
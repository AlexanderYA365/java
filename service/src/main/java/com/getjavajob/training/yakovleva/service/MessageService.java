package com.getjavajob.training.yakovleva.service;

import com.getjavajob.training.yakovleva.common.Account;
import com.getjavajob.training.yakovleva.common.Message;
import com.getjavajob.training.yakovleva.dao.AccountDao;
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
    private AccountDao accountDao;

    @Autowired
    public MessageService(MessageDao messageDao, AccountDao accountDao) {
        logger.info("MessageService(MessageDao messageDao)");
        this.messageDao = messageDao;
        this.accountDao = accountDao;
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
        List<Message> uniqueMessages = messageDao.getUniqueMessagesForUser(account.getId());
        for (Message m : uniqueMessages) {
            m.setUsernameReceiving(accountDao.getAccount(m.getReceiverId()).getUsername());
            m.setUsernameSender(accountDao.getAccount(m.getSenderId()).getUsername());
        }
        return uniqueMessages;
    }

    public List<Message> getAccountMessages(int senderId, int receiverId) {
        logger.info("getAccountMessages(int senderId, int receiverId)");
        logger.debug("getAccountMessages(senderId = {}, receiverId = {})", senderId, receiverId);
        List<Message> uniqueMessages = messageDao.getMessageAccounts(senderId, receiverId);
        for (Message m : uniqueMessages) {
            m.setUsernameReceiving(accountDao.getAccount(m.getReceiverId()).getUsername());
            m.setUsernameSender(accountDao.getAccount(m.getSenderId()).getUsername());
        }
        return uniqueMessages;
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
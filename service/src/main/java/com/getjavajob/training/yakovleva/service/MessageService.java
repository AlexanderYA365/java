package com.getjavajob.training.yakovleva.service;

import com.getjavajob.training.yakovleva.dao.Account;
import com.getjavajob.training.yakovleva.dao.Message;
import com.getjavajob.training.yakovleva.dao.MessageDao;

import java.util.List;

//@Service
public class MessageService {
    private final MessageDao messageDao;

    //@Autowired
    public MessageService(MessageDao messageDao) {
        this.messageDao = messageDao;
    }

    public List<Message> getWallMassageAccount(Account account) {
        System.out.println("readWallMessage, account.getId() - " + account.getId());
        return messageDao.getWallMessage(account.getId());
    }

    public List<Message> getMessages(Account account) {
        System.out.println("read message account - " + account);
        return messageDao.getMessageUserIdNameSender(account.getId());
    }

    public List<Message> getUniqueMessages(Account account) {
        System.out.println("read message account - " + account);
        return messageDao.getUniqueMessagesForUser(account.getId());
    }

    public List<Message> getAccountMessages(int senderId, int receiverId) {
        System.out.println("insertAccountFriends senderId - " + senderId + " ,receiverId - " + receiverId);
        return messageDao.getMessageAccounts(senderId, receiverId);
    }

    public boolean createMassage(Message message) {
        System.out.println("createMessage message - " + message);
        try {
            return messageDao.create(message);
        } catch (Exception ex) {
            System.out.println("createMessage exception - " + ex);
            return false;
        }
    }

    public boolean delete(int id) {
        System.out.println("delete message, id - " + id);
        return messageDao.delete(id);
    }

}
package com.getjavajob.training.yakovleva.service;

import com.getjavajob.training.yakovleva.dao.Account;
import com.getjavajob.training.yakovleva.dao.Message;
import com.getjavajob.training.yakovleva.dao.MessageDao;

import java.util.List;

public class MessageService {
    private final MessageDao messageDao;

    public MessageService() {
        messageDao = new MessageDao();
    }

    public List<Message> readWallMassageAccount(Account account) {
        System.out.println("readWallMessage, account.getId() - " + account.getId());
        return messageDao.readWallMassage(account.getId());
    }

    public List<Message> readMessage(Account account) {
        System.out.println("read message account - " + account);
        return messageDao.readMessageUserIdNameSender(account.getId());
    }

    public List<Message> readUniqueMessages(Account account) {
        System.out.println("read message account - " + account);
        return messageDao.readUniqueMessagesForUser(account.getId());
    }

    public List<Message> accountMessage(int idSender, int idReceiving) {
        System.out.println("insertAccountFriends idSender - " + idSender + " ,idReceiving - " + idReceiving);
        return messageDao.readsMessageAccounts(idSender, idReceiving);
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

}
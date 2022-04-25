import java.util.List;

public class MessageService {
    private final MessageDao messageDao;

    public MessageService() {
        messageDao = new MessageDao();
    }

    public List<Message> readWallMassageAccount(Account account) {
        System.out.println("readWallMassage, account.getId() - " + account.getId());
        return messageDao.readWallMassage(account.getId());
    }

    public List<Message> readMessage(Account account) {
        System.out.println("read massage account - " + account);
        return messageDao.readMessageUserIdNameSender(account.getId());
    }

    public List<Message> accountMessage(int idSender, int idReceiving) {
        System.out.println("insertAccountFriends idSender - " + idSender + " ,idReceiving - " + idReceiving);
        return messageDao.readsMessageAccounts(idSender, idReceiving);
    }

    public boolean createMassage(Message message) {
        System.out.println("createMassage message - " + message);
        try {
            return messageDao.create(message);
        } catch (Exception ex) {
            System.out.println("createMassage exception - " + ex);
            return false;
        }
    }

}
import java.util.List;

public class MessageService {
    private final MessageDao messageDao;
    private final Pool connectionPool;

    public MessageService(){
        messageDao = MessageDao.getInstance();
        connectionPool = ConnectionPool.getInstance();
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
        boolean result = false;
        try {
            result = messageDao.create(message);
            connectionPool.commit();
        }catch (Exception e){
            connectionPool.rollback();
            e.printStackTrace();
        }
        return result;
    }

    public void closeService() {
        connectionPool.returnConnection();
    }

}
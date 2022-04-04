import java.util.List;

public class MessageService {

    public List<Message> readWallMassageAccount(Account account) {
        System.out.println("readWallMassage, account.getId() - " + account.getId());
        MassageDao massageDao = new MassageDao();
        return massageDao.readWallMassage(account.getId());
    }

    public List<Message> readMessage(Account account) {
        System.out.println("read massage account - " + account);
        MassageDao massageDao = new MassageDao();
        return massageDao.readMessageUserIdNameSender(account.getId());
    }

    public List<Message> accountMessage(int idSender, int idReceiving) {
        System.out.println("insertAccountFriends idSender - " + idSender + " ,idReceiving - " + idReceiving);
        MassageDao massageDao = new MassageDao();
        return massageDao.readsMessageAccounts(idSender, idReceiving);
    }

    public boolean createMassage(Message message) {
        MassageDao massageDao = new MassageDao();
        return massageDao.create(message);
    }

}
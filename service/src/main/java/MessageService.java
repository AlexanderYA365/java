import java.util.List;

public class MessageService {

    public boolean createWallMassage(WallMassage massage) {
        WallMassageDao massageDao = new WallMassageDao();
        return massageDao.create(massage);
    }

    public List<WallMassage> readWallMassage(Account account) {
        System.out.println("readWallMassage, account.getId() - " + account.getId());
        WallMassageDao massageDao = new WallMassageDao();
        return massageDao.readWallMassageUserIdNameSender(account.getId());
    }

    public List<Message> readWallMassageAccount(Account account) {
        System.out.println("readWallMassage, account.getId() - " + account.getId());
        MassageDao massageDao = new MassageDao();
        return massageDao.readWallMassage(account.getId());
    }

    public List<Message> readMessage(Account account) { //TODO исправить чьи сообщения, добавить в название и заменить
        System.out.println("read massage account - " + account);
        MassageDao massageDao = new MassageDao();
        return massageDao.readMessageUserIdNameSender(account.getId());
    }

    public List<Message> accountMessage(int idSender, int idReceiving) { //TODO
        System.out.println("insertAccountFriends idSender - " + idSender + " ,idReceiving - " + idReceiving);
        MassageDao massageDao = new MassageDao();
        return massageDao.readsMessageAccounts(idSender, idReceiving);
    }

    public boolean createMassage(Message message) {
        MassageDao massageDao = new MassageDao();
        return massageDao.create(message);
    }

}
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

    public List<Massage> readMassage(Account account){
        System.out.println("read massage account - " + account);
        MassageDao massageDao = new MassageDao();
        return massageDao.readMassageUserIdNameSender(account.getId());
    }

    public List<Massage> accountMassage(int idSender, int idReceiving) { //TODO
        System.out.println("insertAccountFriends idSender - " + idSender + " ,idReceiving - " + idReceiving);
        MassageDao massageDao = new MassageDao();
        return massageDao.readsMassageAccounts(idSender, idReceiving);
    }

    public boolean createMassage(Massage massage){
        MassageDao massageDao = new MassageDao();
        return massageDao.create(massage);
    }

}
import java.util.List;

public class AccountService {

    public boolean create(Account account) {
        System.out.println("Creat new Account from AccountService.create");
        AccountDao accountDAO = new AccountDao();
        try {
            return accountDAO.createAccount(account);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(Account account) {
        AccountDao accountDAO = new AccountDao();
        return accountDAO.updateAccount(account);
    }

    public boolean delete(Account account) {
        AccountDao accountDAO = new AccountDao();
        return accountDAO.deleteAccount(account);
    }

    public Account read(Friend friend) {
        System.out.println("Account read friend.getIdFriendsAccount - " + friend.getIdFriendsAccount());
        AccountDao accountDao = new AccountDao();
        return accountDao.readAccount(friend.getIdFriendsAccount());
    }

    public Account read(int idAccount) {
        System.out.println("Account read idAccount - " + idAccount);
        AccountDao accountDao = new AccountDao();
        return accountDao.readAccount(idAccount);
    }

    public boolean addFriend(Account account, Account friend) {
        FriendDao friendDAO = new FriendDao();
        Friend friend1 = new Friend();
        friend1.setIdAccount(account.getId());
        friend1.setIdFriendsAccount(friend.getId());
        return friendDAO.create(friend1);
    }

    public boolean deleteFriend(Account account, Account friend) {
        FriendDao friendDAO = new FriendDao();
        return friendDAO.deleteFriendIdAccountIdFriendAccount(account.getId(), friend.getId());
    }

    public List<Friend> accountFriends(Account account) {
        FriendDao friendDAO = new FriendDao();
        List<Friend> friendList = friendDAO.readFriends(account.getId());
        return friendList;
    }

    public List<Account> getAllAccounts() {
        AccountDao accountDAO = new AccountDao();
        System.out.println("create account dao");
        List<Account> accounts = accountDAO.readAccounts();
        System.out.println("read from account dao");
        return accounts;
    }

    public boolean createWallMassage(WallMassage massage) {
        WallMassageDao massageDao = new WallMassageDao();
        try {

            massageDao.create(massage);
            return true;
        } catch (Exception e) {
            System.out.println("exception in createWallMassage - " + e);
        }
        return false;
    }

    public List<WallMassage> readWallMassage(Account account) {
        System.out.println("readWallMassage, account.getId() - " + account.getId());
        WallMassageDao massageDao = new WallMassageDao();
        return massageDao.readWallMassageUserId(account.getId());
    }

    public List<Group> readAccountGroups(Account account) {
        System.out.println("readGroup, account.getId() - " + account.getId());
        GroupDao groupDao = new GroupDao();
        return groupDao.readGroupsAccount(account.getId());
    }

    public void createAccountGroups(Group group) {
        System.out.println("createAccountGroups");
        GroupDao groupDao = new GroupDao();
        try {
            groupDao.create(group);
        } catch (Exception e) {
            System.out.println("createAccountGroups Exception - " + e);
        }
    }

    public List<Group> readGroups() {
        System.out.println("readGroups");
        GroupDao groupDao = new GroupDao();
        return groupDao.readGroups();
    }

    public List<Account> getAccountName(String name) throws Exception {
        System.out.println("getAccountName, name - " + name);
        if(name == null) {
            throw new Exception("getAccountName name == null");
        } else {
            AccountDao dao = new AccountDao();
            return dao.readAccountsName(name);
        }
    }

    public List<Massage> readMassage(Account account){
        System.out.println("read massage account - " + account);
        MassageDao massageDao = new MassageDao();
        return massageDao.readMassageUserId(account.getId());
    }

}
import java.util.List;

public class AccountService {

    public boolean create(Account account){
        AccountDao accountDAO = new AccountDao();
        try {
            return accountDAO.createAccount(account);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(Account account){
        AccountDao accountDAO = new AccountDao();
        return accountDAO.updateAccount(account);
    }

    public boolean delete(Account account){
        AccountDao accountDAO = new AccountDao();
        return accountDAO.deleteAccount(account);
    }

    public boolean addFriend(Account account, Account friend){
        FriendDao friendDAO = new FriendDao();
        Friend friend1 = new Friend();
        friend1.setIdAccount(account.getId());
        friend1.setIdFriendsAccount(friend.getId());
        return friendDAO.createFriend(friend1);
    }

    public boolean deleteFriend(Account account, Account friend){
        FriendDao friendDAO = new FriendDao();
        return friendDAO.deleteFriendIdAccountIdFriendAccount(account.getId(), friend.getId());
    }

    public List<Friend> accountFriends(Account account){
        FriendDao friendDAO = new FriendDao();
        List<Friend> friendList = friendDAO.readFriends(account.getId());
        return friendList;
    }

    public List<Account> getAllAccounts(){
        AccountDao accountDAO = new AccountDao();
        System.out.println("create account dao");
        List<Account> accounts = accountDAO.readAccounts();
        System.out.println("read from account dao");
        return accounts;
    }

}
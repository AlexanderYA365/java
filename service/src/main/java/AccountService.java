import java.util.List;

public class AccountService {

    public boolean create(Account account){
        AccountDAO accountDAO = new AccountDAO();
        try {
            return accountDAO.createAccount(account);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(Account account){
        AccountDAO accountDAO = new AccountDAO();
        return accountDAO.updateAccount(account);
    }

    public boolean delete(Account account){
        AccountDAO accountDAO = new AccountDAO();
        return accountDAO.deleteAccount(account);
    }

    public boolean addFriend(Account account, Account friend){
        FriendDAO friendDAO = new FriendDAO();
        Friend friend1 = new Friend();
        friend1.setIdAccount(account.getId());
        friend1.setIdFriendsAccount(friend.getId());
        return friendDAO.createFriend(friend1);
    }

    public boolean deleteFriend(Account account, Account friend){
        FriendDAO friendDAO = new FriendDAO();
        return friendDAO.deleteFriendIdAccountIdFriendAccount(account.getId(), friend.getId());
    }

    public List<Friend> accountFriends(Account account){
        FriendDAO friendDAO = new FriendDAO();
        List<Friend> friendList = friendDAO.readFriends(account.getId());
        return friendList;
    }

    public List<Account> getAllAccounts(){
        AccountDAO accountDAO = new AccountDAO();
        System.out.println("create account dao");
        List<Account> accounts = accountDAO.readAccounts();
        System.out.println("read from account dao");
        return accounts;
    }

}
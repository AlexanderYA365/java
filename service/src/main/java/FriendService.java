import java.util.List;

public class FriendService {
    private AccountDao accountDAO;
    private Pool connectionPool;
    private FriendDao friendDAO;

    public FriendService(){
        accountDAO = AccountDao.getInstance();
        connectionPool = ConnectionPool.getInstance();
        friendDAO = FriendDao.getInstance();
    }

    public Account read(Friend friend) {
        System.out.println("Account read friend.getIdFriendsAccount - " + friend.getIdFriendsAccount());
        return accountDAO.readAccount(friend.getIdFriendsAccount());
    }

    public boolean addFriend(Account account, Account friend) {
        System.out.println("addFriend, accountId - " + account.getId() + ", friendId - " + friend.getId());
        try {
            Friend friend1 = new Friend();
            friend1.setIdAccount(account.getId());
            friend1.setIdFriendsAccount(friend.getId());
            friendDAO.create(friend1);
            connectionPool.commit();
            return true;
        } catch (Exception e){
            connectionPool.rollback();
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteFriend(Account account, Account friend) {
        System.out.println("deleteFriend, accountId - " + account.getId() + ", friendId - " + friend.getId());
        try {
            friendDAO.deleteFriendIdAccountIdFriendAccount(account.getId(), friend.getId());
            connectionPool.commit();
            return true;
        } catch (Exception e){
            connectionPool.rollback();
            e.printStackTrace();
        }
        return false;
    }

    public List<Friend> accountFriends(Account account) {
        System.out.println("accountFriends, accountId - " + account.getId());
        return friendDAO.readFriends(account.getId());
    }

    public List<Friend> readAccountFriends(int idAccount) {
        System.out.println("readAccountFriends idAccount - " + idAccount);
        return friendDAO.readFriendsName(idAccount);
    }

    public Friend read(int id) {
        System.out.println("readAccountFriends id - " + id);
        return friendDAO.read(id);
    }

    public void closeService() {
        connectionPool.returnConnection();
    }

}
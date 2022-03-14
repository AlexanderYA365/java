import java.util.List;

public class FriendService {

    public Account read(Friend friend) {
        System.out.println("Account read friend.getIdFriendsAccount - " + friend.getIdFriendsAccount());
        AccountDao accountDao = new AccountDao();
        return accountDao.readAccount(friend.getIdFriendsAccount());
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
        return friendDAO.readFriends(account.getId());
    }

    public List<Friend> insertAccountFriends(int idAccount) {
        System.out.println("insertAccountFriends idAccount - " + idAccount);
        FriendDao friendDao = new FriendDao();
        return friendDao.readFriendsName(idAccount);
    }

}
package com.getjavajob.training.yakovleva.service;

import com.getjavajob.training.yakovleva.dao.Account;
import com.getjavajob.training.yakovleva.dao.AccountDao;
import com.getjavajob.training.yakovleva.dao.Friend;
import com.getjavajob.training.yakovleva.dao.FriendDao;

import java.util.List;

//@Service
public class FriendService {
    private AccountDao accountDAO;
    private FriendDao friendDAO;

    //@Autowired
    public FriendService(AccountDao accountDAO, FriendDao friendDAO) {
        this.accountDAO = accountDAO;
        this.friendDAO = friendDAO;
    }

    public Account get(Friend friend) {
        System.out.println("Account read friend.getIdFriendsAccount - " + friend.getFriendId());
        return accountDAO.getAccount(friend.getFriendId());
    }

    public boolean addFriend(Account account, Account friend) {
        System.out.println("addFriend, accountId - " + account.getId() + ", friendId - " + friend.getId());
        try {
            Friend friend1 = new Friend();
            friend1.setAccountId(account.getId());
            friend1.setFriendId(friend.getId());
            return friendDAO.create(friend1);
        } catch (Exception ex) {
            System.out.println("read Exception - " + ex);
        }
        return false;
    }

    public boolean deleteFriend(Account account, Account friend) {
        System.out.println("deleteFriend, accountId - " + account.getId() + ", friendId - " + friend.getId());
        try {
            return friendDAO.deleteFriendIdAccountIdFriendAccount(account.getId(), friend.getId());
        } catch (Exception ex) {
            System.out.println("deleteFriend Exception - " + ex);
        }
        return false;
    }

    public List<Friend> accountFriends(Account account) {
        System.out.println("accountFriends, accountId - " + account.getId());
        return friendDAO.getFriends(account.getId());
    }

    public List<Friend> getAccountFriends(int idAccount) {
        System.out.println("readAccountFriends accountId - " + idAccount);
        return friendDAO.getFriendsName(idAccount);
    }

    public Friend get(int id) {
        System.out.println("readAccountFriends id - " + id);
        return friendDAO.get(id);
    }

}
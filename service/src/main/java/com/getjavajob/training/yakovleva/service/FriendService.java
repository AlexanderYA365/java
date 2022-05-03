package com.getjavajob.training.yakovleva.service;

import com.getjavajob.training.yakovleva.dao.Account;
import com.getjavajob.training.yakovleva.dao.AccountDao;
import com.getjavajob.training.yakovleva.dao.Friend;
import com.getjavajob.training.yakovleva.dao.FriendDao;

import java.util.List;

public class FriendService {
    private AccountDao accountDAO;
    private FriendDao friendDAO;

    public FriendService() {
        accountDAO = new AccountDao();
        friendDAO = new FriendDao();
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
            return true;
        } catch (Exception ex) {
            System.out.println("read Exception - " + ex);
        }
        return false;
    }

    public boolean deleteFriend(Account account, Account friend) {
        System.out.println("deleteFriend, accountId - " + account.getId() + ", friendId - " + friend.getId());
        try {
            friendDAO.deleteFriendIdAccountIdFriendAccount(account.getId(), friend.getId());
            return true;
        } catch (Exception ex) {
            System.out.println("deleteFriend Exception - " + ex);
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

}
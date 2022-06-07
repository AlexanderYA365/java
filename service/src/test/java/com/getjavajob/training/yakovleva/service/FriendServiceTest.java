package com.getjavajob.training.yakovleva.service;

import com.getjavajob.training.yakovleva.dao.Account;
import com.getjavajob.training.yakovleva.dao.Friend;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

class FriendServiceTest {

    @Test
    void get() {
        Friend friend = Mockito.mock(Friend.class);
        Account account = Mockito.mock(Account.class);
        FriendService friendService = Mockito.mock(FriendService.class);
        Mockito.when(friendService.get(friend)).thenReturn(account);
        Assert.assertEquals(account, friendService.get(friend));
    }

    @Test
    void addFriendTrue() {
        Account account = Mockito.mock(Account.class);
        FriendService friendService = Mockito.mock(FriendService.class);
        Mockito.when(friendService.addFriend(account, account)).thenReturn(true);
        Assert.assertEquals(true, friendService.addFriend(account, account));
    }

    @Test
    void addFriendFalse() {
        Account account = Mockito.mock(Account.class);
        FriendService friendService = Mockito.mock(FriendService.class);
        Mockito.when(friendService.addFriend(account, account)).thenReturn(false);
        Assert.assertEquals(false, friendService.addFriend(account, account));
    }

    @Test
    void deleteFriendTrue() {
        Account account = Mockito.mock(Account.class);
        FriendService friendService = Mockito.mock(FriendService.class);
        Mockito.when(friendService.deleteFriend(account, account)).thenReturn(true);
        Assert.assertEquals(true, friendService.deleteFriend(account, account));
    }

    @Test
    void deleteFriendFalse() {
        Account account = Mockito.mock(Account.class);
        FriendService friendService = Mockito.mock(FriendService.class);
        Mockito.when(friendService.deleteFriend(account, account)).thenReturn(false);
        Assert.assertEquals(false, friendService.deleteFriend(account, account));
    }

    @Test
    void accountFriends() {
        List<Friend> friends = Mockito.mock(List.class);
        Account account = Mockito.mock(Account.class);
        FriendService friendService = Mockito.mock(FriendService.class);
        Mockito.when(friendService.accountFriends(account)).thenReturn(friends);
        Assert.assertEquals(friends, friendService.accountFriends(account));
    }

    @Test
    void getAccountFriends() {
        List<Friend> friends = Mockito.mock(List.class);
        FriendService friendService = Mockito.mock(FriendService.class);
        Mockito.when(friendService.getAccountFriends(1)).thenReturn(friends);
        Assert.assertEquals(friends, friendService.getAccountFriends(1));
    }

    @Test
    void getId() {
        Friend friend = Mockito.mock(Friend.class);
        FriendService friendService = Mockito.mock(FriendService.class);
        Mockito.when(friendService.get(1)).thenReturn(friend);
        Assert.assertEquals(friend, friendService.get(1));
    }
}
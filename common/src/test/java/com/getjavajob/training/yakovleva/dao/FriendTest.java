package com.getjavajob.training.yakovleva.dao;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class FriendTest {

    @Test
    void getIdAccount() {
        Friend friend = new Friend();
        friend.setAccountId(1);
        Assert.assertEquals(1, friend.getAccountId());
    }

    @Test
    void getIdFriendsAccount() {
        Friend friend = new Friend();
        friend.setFriendId(1);
        Assert.assertEquals(1, friend.getFriendId());
    }

    @Test
    void getIdFriends() {
        Friend friend = new Friend();
        friend.setId(1);
        Assert.assertEquals(1, friend.getId());
    }

    @Test
    void setIdFriends() {
        Friend friend = new Friend();
        friend.setId(1);
        Assert.assertEquals(1, friend.getId());
    }

    @Test
    void setIdAccount() {
        Friend friend = new Friend();
        friend.setAccountId(1);
        Assert.assertEquals(1, friend.getAccountId());
    }

    @Test
    void setIdFriendsAccount() {
        Friend friend = new Friend();
        friend.setFriendId(1);
        Assert.assertEquals(1, friend.getFriendId());
    }
}
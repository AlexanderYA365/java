package com.getjavajob.training.yakovleva.dao;

public class Relations {
    private int accountId;
    private int friendId;

    public Relations(){
    }

    public Relations(int accountId, int friendId){
        this.accountId = accountId;
        this.friendId = friendId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getFriendId() {
        return friendId;
    }

    public void setFriendId(int friendId) {
        this.friendId = friendId;
    }

    @Override
    public String toString() {
        return "Relations{" +
                "account_id=" + accountId +
                ", friend_id=" + friendId +
                '}';
    }
}

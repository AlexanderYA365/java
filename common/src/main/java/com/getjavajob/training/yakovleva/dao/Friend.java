package com.getjavajob.training.yakovleva.dao;

import java.util.Objects;

public class Friend {//TODO поправить таблицу, удалить имя, переделать на многим ко многим
    private int id;
    private int accountId;
    private int friendId;
    private String name;
    private String username;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Friend{" +
                "idFriends = " + id +
                ", accountId = " + accountId +
                ", idFriendsAccount = " + friendId +
                ", name = " + name +
                ", username = " + username +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Friend friend = (Friend) o;
        return id == friend.id &&
                accountId == friend.accountId &&
                friendId == friend.friendId &&
                Objects.equals(name, friend.name) &&
                Objects.equals(username, friend.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountId, friendId, name, username);
    }

}
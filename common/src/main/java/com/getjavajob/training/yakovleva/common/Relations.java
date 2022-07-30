package com.getjavajob.training.yakovleva.common;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "relations")
public class Relations {
    @Id
    @Column(name = "account_id")
    private int accountId;
    @Column(name = "friend_id")
    private int friendId;
    @ManyToMany(mappedBy = "relations")
    private Set<Account> accountSet = new HashSet<>();

    public Relations() {
    }

    public Relations(int accountId, int friendId) {
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

package com.getjavajob.training.yakovleva.common;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@IdClass(RelationId.class)
@Table(name = "relations")
public class Relations {
    @Column(name = "account_id", insertable = false, updatable = false)
    private int accountId;
    @Column(name = "friend_id")
    private int friendId;
    @Id
    private long id = accountId + friendId;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Relations relations = (Relations) o;
        return accountId == relations.accountId &&
                friendId == relations.friendId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, friendId);
    }
}

@Embeddable
class RelationId implements Serializable {
    int accountId;
    int friendId;

    public RelationId() {
    }

    public RelationId(int accountId, int friendId) {
        this.accountId = accountId;
        this.friendId = friendId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RelationId that = (RelationId) o;
        return accountId == that.accountId &&
                friendId == that.friendId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, friendId);
    }

}

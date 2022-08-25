package com.getjavajob.training.yakovleva.common;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "relations")
//@IdClass(Relations.RelationsPK.class)
public class Relations implements Serializable {
    //    private RelationsPK id;
//    @MapsId("accountId")
    @Column(name = "account_id")
    private int accountId;
    //    @MapsId("friendId")
    @Column(name = "friend_id")
    private int friendId;
    @ManyToMany(mappedBy = "relations")
    private Set<Account> accountSet = new HashSet<>();
    @Id
    private int anInt = accountId + friendId;

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

    @Embeddable
    public class RelationsPK implements Serializable {
        protected int accountId;
        protected int friendId;

        public RelationsPK() {
        }

        public RelationsPK(int accountId, int friendId) {
            this.accountId = accountId;
            this.friendId = friendId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            RelationsPK that = (RelationsPK) o;
            return accountId == that.accountId &&
                    friendId == that.friendId;
        }

        @Override
        public int hashCode() {
            return Objects.hash(accountId, friendId);
        }
    }

}

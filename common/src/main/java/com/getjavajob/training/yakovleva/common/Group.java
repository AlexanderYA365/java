package com.getjavajob.training.yakovleva.common;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "socialnetwork.group")
public class Group {
    @Id
    @GeneratedValue
    @Column(name = "group_id")
    private int groupId;
    @Column(name = "account_id")
    private int accountId;
    @Column(name = "group_name")
    private String groupName;
    @Column(name = "logo")
    private String logo;
    @Column(name = "administrator_id")
    private int administratorId;

    public Group(String groupName, String logo, int administratorId, int accountId, Date dateCreateGroup) {
        this.groupName = groupName;
        this.logo = logo;
        this.administratorId = administratorId;
        this.accountId = accountId;
    }

    public Group() {

    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public int getAdministratorId() {
        return administratorId;
    }

    public void setAdministratorId(int administratorId) {
        this.administratorId = administratorId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        return "Group{" +
                "groupId=" + groupId +
                ", accountId=" + accountId +
                ", groupName='" + groupName + '\'' +
                ", logo='" + logo + '\'' +
                ", administratorId=" + administratorId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return groupId == group.groupId &&
                accountId == group.accountId &&
                administratorId == group.administratorId &&
                Objects.equals(groupName, group.groupName) &&
                Objects.equals(logo, group.logo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId, accountId, groupName, logo, administratorId);
    }

}
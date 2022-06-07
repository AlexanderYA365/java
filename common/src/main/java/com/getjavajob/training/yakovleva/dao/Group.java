package com.getjavajob.training.yakovleva.dao;

import java.util.Date;
import java.util.Objects;

public class Group {
    private int groupId;
    private int accountId;
    private String nameAccount;
    private String groupName;
    private String logo;
    private Date dateCreateGroup;
    private int administratorId;

    public Group(String groupName, String logo, int administratorId, int accountId, Date dateCreateGroup) {
        this.groupName = groupName;
        this.logo = logo;
        this.administratorId = administratorId;
        this.accountId = accountId;
        this.dateCreateGroup = dateCreateGroup;
    }

    public Group() {

    }

    public String getNameAccount() {
        return nameAccount;
    }

    public void setNameAccount(String nameAccount) {
        this.nameAccount = nameAccount;
    }

    public Date getDateCreateGroup() {
        return dateCreateGroup;
    }

    public void setDateCreateGroup(Date dateCreateGroup) {
        this.dateCreateGroup = dateCreateGroup;
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
                ", nameAccount='" + nameAccount + '\'' +
                ", groupName='" + groupName + '\'' +
                ", logo='" + logo + '\'' +
                ", dateCreateGroup=" + dateCreateGroup +
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
                Objects.equals(nameAccount, group.nameAccount) &&
                Objects.equals(groupName, group.groupName) &&
                Objects.equals(logo, group.logo) &&
                Objects.equals(dateCreateGroup, group.dateCreateGroup);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId, accountId, nameAccount, groupName, logo, dateCreateGroup, administratorId);
    }

}
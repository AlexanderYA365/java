package com.getjavajob.training.yakovleva.dao;

import java.util.Date;

public class Group {
    private int idGroup;
    private int idAccount;
    private String nameAccount;
    private String groupName;
    private String logo;
    private Date dateCreateGroup;
    private int idAdministrator;

    public Group() {
    }

    public Group(String groupName, String logo, int idAdministrator, int idAccount, Date dateCreateGroup) {
        this.groupName = groupName;
        this.logo = logo;
        this.idAdministrator = idAdministrator;
        this.idAccount = idAccount;
        this.dateCreateGroup = dateCreateGroup;
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

    public int getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(int idAccount) {
        this.idAccount = idAccount;
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

    public int getIdAdministrator() {
        return idAdministrator;
    }

    public void setIdAdministrator(int idAdministrator) {
        this.idAdministrator = idAdministrator;
    }

    public int getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(int idGroup) {
        this.idGroup = idGroup;
    }

    @Override
    public String toString() {
        return "Group{" +
                "idGroup=" + idGroup +
                ", idAccount=" + idAccount +
                ", nameAccount='" + nameAccount + '\'' +
                ", groupName='" + groupName + '\'' +
                ", logo='" + logo + '\'' +
                ", dateCreateGroup=" + dateCreateGroup +
                ", idAdministrator=" + idAdministrator +
                '}';
    }

}
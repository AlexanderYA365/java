package com.getjavajob.training.yakovleva.dao;

public class User {
    private String userName;
    private String password;
    private int idAccount;

    User(String userName, String password, int idAccount) {
        this.userName = userName;
        this.password = password;
        this.idAccount = idAccount;

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(int idAccount) {
        this.idAccount = idAccount;
    }
}
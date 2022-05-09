package com.getjavajob.training.yakovleva.dao;

public class Phone {
    private int id;
    private int idAccount;//TODO
    private String phoneNumber;
    private PhoneType phoneType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPhoneType() {
        return phoneType.getStatus();
    }

    public void setPhoneType(int status) {
        this.phoneType = PhoneType.values()[status];
    }

    public int getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(int idAccount) {
        this.idAccount = idAccount;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Phone{" +
                " id=" + id +
                ", idAccount=" + idAccount +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", phoneType=" + phoneType +
                '}';
    }
}

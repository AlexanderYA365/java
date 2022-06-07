package com.getjavajob.training.yakovleva.dao;

import java.util.Objects;

public class Phone {
    private int id;
    private int accountId;
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

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
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
                ", accountId=" + accountId +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", phoneType=" + phoneType +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Phone phone = (Phone) o;
        return id == phone.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountId, phoneNumber, phoneType);
    }

}
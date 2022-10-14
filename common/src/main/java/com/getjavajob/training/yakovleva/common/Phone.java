package com.getjavajob.training.yakovleva.common;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "phone")
public class Phone implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "account_id", insertable = false, updatable = false)
    private int accountId;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "phone_type")
    private PhoneType phoneType;
//    @ManyToOne(targetEntity = Account.class, fetch = FetchType.EAGER)
//    @JsonIgnore
//    @JoinColumn(updatable = false, insertable = false, name = "account_id", referencedColumnName = "account_id")
//    private Account account;

    public Phone() {
    }

    public Phone(int id, int accountId, String phoneNumber, PhoneType phoneType) {
        this.id = id;
        this.accountId = accountId;
        this.phoneNumber = phoneNumber;
        this.phoneType = phoneType;
    }

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
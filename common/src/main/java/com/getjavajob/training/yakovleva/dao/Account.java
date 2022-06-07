package com.getjavajob.training.yakovleva.dao;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Account {
    private int id;
    private String name;
    private String surname;
    private String lastName;
    private Date date;
    private List<Phone> phones;
    private int icq;
    private String photoFile;
    private byte[] photo;
    private String addressHome;
    private String addressJob;
    private String email;
    private String aboutMe;
    private String username;
    private String password;
    private Role role;

    public Account(String name, String surname, String lastName, Date date, List<Phone> phones,
                   int icq, String photoFile, byte[] photo, String addressHome, String addressJob, String email, String aboutMe, String username,
                   String password, Role role) {
        this.name = name;
        this.surname = surname;
        this.lastName = lastName;
        this.date = date;
        this.phones = phones;
        this.icq = icq;
        this.photoFile = photoFile;
        this.photo = photo;
        this.addressHome = addressHome;
        this.addressJob = addressJob;
        this.email = email;
        this.aboutMe = aboutMe;
        this.username = username;
        this.password = password;
        this.phones = phones;
        this.role = role;
    }

    public Account() {

    }

    public int getRole() {
        return role.getStatus();
    }

    public void setRole(int status) {
        this.role = Role.values()[status];
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    public int getIcq() {
        return icq;
    }

    public void setIcq(int icq) {
        this.icq = icq;
    }

    public String getAddressHome() {
        return addressHome;
    }

    public void setAddressHome(String addressHome) {
        this.addressHome = addressHome;
    }

    public String getAddressJob() {
        return addressJob;
    }

    public void setAddressJob(String addressJob) {
        this.addressJob = addressJob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getPhotoFile() {
        return photoFile;
    }

    public void setPhotoFile(String photoFile) {
        this.photoFile = photoFile;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", lastName='" + lastName + '\'' +
                ", date=" + date +
                ", phones=" + phones +
                ", icq=" + icq +
                ", photoFile='" + photoFile + '\'' +
                ", photo=" + Arrays.toString(photo) +
                ", addressHome='" + addressHome + '\'' +
                ", addressJob='" + addressJob + '\'' +
                ", email='" + email + '\'' +
                ", aboutMe='" + aboutMe + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id == account.id &&
                icq == account.icq &&
                Objects.equals(name, account.name) &&
                Objects.equals(surname, account.surname) &&
                Objects.equals(lastName, account.lastName) &&
                Objects.equals(date, account.date) &&
                Objects.equals(phones, account.phones) &&
                Objects.equals(photoFile, account.photoFile) &&
                Arrays.equals(photo, account.photo) &&
                Objects.equals(addressHome, account.addressHome) &&
                Objects.equals(addressJob, account.addressJob) &&
                Objects.equals(email, account.email) &&
                Objects.equals(aboutMe, account.aboutMe) &&
                Objects.equals(username, account.username) &&
                Objects.equals(password, account.password) &&
                role == account.role;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, name, surname, lastName, date, phones, icq, photoFile, addressHome, addressJob, email, aboutMe, username, password, role);
        result = 31 * result + Arrays.hashCode(photo);
        return result;
    }

}
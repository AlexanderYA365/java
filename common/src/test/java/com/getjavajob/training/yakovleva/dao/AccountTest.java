package com.getjavajob.training.yakovleva.dao;

import org.junit.Assert;
import org.junit.Test;

class AccountTest {
    private String expectedString = "test";

    @Test
    public void getUsername() {
        Account account = new Account();
        account.setUsername("test");
        Assert.assertEquals(expectedString, account.getUsername());
    }

    @Test
    public void getPassword() {
        Account account = new Account();
        account.setPassword("test");
        Assert.assertEquals(expectedString, account.getPassword());
    }

    @Test
    public void getId() {
        Account account = new Account();
        account.setId(1);
        Assert.assertEquals(1, account.getId());
    }

    @Test
    public void getName() {
        Account account = new Account();
        account.setName("test");
        Assert.assertEquals(expectedString, account.getName());
    }

    @Test
    public void getSurname() {
        Account account = new Account();
        account.setSurname("test");
        Assert.assertEquals(expectedString, account.getSurname());
    }

    @Test
    public void getLastName() {
        Account account = new Account();
        account.setLastName("test");
        Assert.assertEquals(expectedString, account.getLastName());
    }

    @Test

    public void getDate() {

    }

    @Test
    public void getPhone() {
        Account account = new Account();
    }

    @Test
    public void getIcq() {
        Account account = new Account();
        account.setIcq(1);
        Assert.assertEquals(1, account.getIcq());
    }

    @Test
    public void getAddressHome() {
        Account account = new Account();
        account.setAddressHome("test");
        Assert.assertEquals(expectedString, account.getAddressHome());
    }

    @Test
    public void getAddressJob() {
        Account account = new Account();
        account.setAddressJob("test");
        Assert.assertEquals(expectedString, account.getAddressJob());
    }

    @Test
    void getEmail() {
        Account account = new Account();
        account.setEmail("test");
        Assert.assertEquals(expectedString, account.getEmail());
    }

    @Test
    public void getAboutMe() {
        Account account = new Account();
        account.setAboutMe("test");
        Assert.assertEquals(expectedString, account.getAboutMe());
    }

    @Test
    public void setUsername() {
        Account account = new Account();
        account.setUsername("test");
        Assert.assertEquals(expectedString, account.getUsername());
    }

    @Test
    public void setPassword() {
        Account account = new Account();
        account.setPassword("test");
        Assert.assertEquals(expectedString, account.getPassword());
    }

    @Test
    public void setRole() {
        Account account = new Account();
        account.setRole(1);
        Assert.assertEquals(1, account.getRole());
    }

    @Test
    public void setId() {
        Account account = new Account();
        account.setId(1);
        Assert.assertEquals(1, account.getId());
    }

    @Test
    public void setName() {
        Account account = new Account();
        account.setName("test");
        Assert.assertEquals(expectedString, account.getName());
    }

    @Test
    public void setSurname() {
        Account account = new Account();
        account.setSurname("test");
        Assert.assertEquals(expectedString, account.getSurname());
    }

    @Test
    public void setLastName() {
        Account account = new Account();
        account.setLastName("test");
        Assert.assertEquals(expectedString, account.getLastName());
    }

    @Test
    public void setDate() {

    }

    @Test
    public void setIcq() {
        Account account = new Account();
        account.setIcq(1);
        Assert.assertEquals(1, account.getIcq());
    }

    @Test
    public void setAddressHome() {
        Account account = new Account();
        account.setAddressHome("test");
        Assert.assertEquals(expectedString, account.getAddressHome());
    }

    @Test
    public void setAddressJob() {
        Account account = new Account();
        account.setAddressJob("test");
        Assert.assertEquals(expectedString, account.getAddressJob());
    }

    @Test
    public void setEmail() {
        Account account = new Account();
        account.setEmail("test");
        Assert.assertEquals(expectedString, account.getEmail());
    }

    @Test
    public void setAboutMe() {
        Account account = new Account();
        account.setAboutMe("test");
        Assert.assertEquals(expectedString, account.getAboutMe());
    }

    @Test
    public void testToString() {
        Account account = new Account();
        account.setUsername("test");
        Assert.assertEquals(expectedString, account.getUsername());
    }

}
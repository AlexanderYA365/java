package com.getjavajob.training.yakovleva.dao;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class UserTest {

    @Test
    void getUserName() {
        User user = new User("test", "test", 1);
        Assert.assertEquals("test", user.getUserName());
    }

    @Test
    void getPassword() {
        User user = new User("test", "test", 1);
        Assert.assertEquals("test", user.getPassword());
    }

    @Test
    void getIdAccount() {
        User user = new User("test", "test", 1);
        Assert.assertEquals(1, user.getIdAccount());
    }

    @Test
    void setUserName() {
        User user = new User("test", "test", 1);
        user.setUserName("test1");
        Assert.assertEquals("test1", user.getUserName());
    }

    @Test
    void setPassword() {
        User user = new User("test", "test", 1);
        user.setPassword("test1");
        Assert.assertEquals("test1", user.getPassword());
    }

    @Test
    void setIdAccount() {
        User user = new User("test", "test", 1);
        user.setIdAccount(2);
        Assert.assertEquals(2, user.getIdAccount());
    }

}
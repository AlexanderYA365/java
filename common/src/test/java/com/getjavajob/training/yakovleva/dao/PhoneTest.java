package com.getjavajob.training.yakovleva.dao;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class PhoneTest {

    @Test
    void getIdPhone() {
        Phone phone = new Phone();
        phone.setAccountId(1);
        Assert.assertEquals(1, phone.getAccountId());
    }

    @Test
    void getPhoneNumber() {
        Phone phone = new Phone();
        phone.setPhoneNumber("test");
        Assert.assertEquals("1", phone.getPhoneNumber());
    }

    @Test
    void getPhoneType() {
        Phone phone = new Phone();
        phone.setPhoneType(1);
        Assert.assertEquals(1, phone.getPhoneType());
    }

}
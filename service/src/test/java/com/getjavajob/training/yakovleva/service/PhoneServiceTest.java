package com.getjavajob.training.yakovleva.service;

import com.getjavajob.training.yakovleva.dao.Account;
import com.getjavajob.training.yakovleva.dao.Message;
import com.getjavajob.training.yakovleva.dao.Phone;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PhoneServiceTest {

    @Test
    void createTrue() {
        Phone phone = Mockito.mock(Phone.class);
        PhoneService phoneService = Mockito.mock(PhoneService.class);
        Mockito.when(phoneService.create(phone)).thenReturn(true);
        Assert.assertEquals(true, phoneService.create(phone));
    }

    @Test
    void createFalse() {
        Phone phone = Mockito.mock(Phone.class);
        PhoneService phoneService = Mockito.mock(PhoneService.class);
        Mockito.when(phoneService.create(phone)).thenReturn(false);
        Assert.assertEquals(false, phoneService.create(phone));
    }

    @Test
    void get() {
        List<Phone> phones = Mockito.mock(List.class);
        PhoneService phoneService = Mockito.mock(PhoneService.class);
        Mockito.when(phoneService.get(1)).thenReturn(phones);
        Assert.assertEquals(phones, phoneService.get(1));
    }

    @Test
    void updateTrue() {
        Phone phone = Mockito.mock(Phone.class);
        PhoneService phoneService = Mockito.mock(PhoneService.class);
        Mockito.when(phoneService.update(phone)).thenReturn(false);
        Assert.assertEquals(false, phoneService.update(phone));
    }

    @Test
    void updateFalse() {
        Phone phone = Mockito.mock(Phone.class);
        PhoneService phoneService = Mockito.mock(PhoneService.class);
        Mockito.when(phoneService.update(phone)).thenReturn(false);
        Assert.assertEquals(false, phoneService.update(phone));
    }

    @Test
    void deleteTrue() {
        Phone phone = Mockito.mock(Phone.class);
        PhoneService phoneService = Mockito.mock(PhoneService.class);
        Mockito.when(phoneService.delete(phone)).thenReturn(true);
        Assert.assertEquals(true, phoneService.delete(phone));
    }

    @Test
    void deleteFalse() {
        Phone phone = Mockito.mock(Phone.class);
        PhoneService phoneService = Mockito.mock(PhoneService.class);
        Mockito.when(phoneService.delete(phone)).thenReturn(false);
        Assert.assertEquals(false, phoneService.delete(phone));
    }

}
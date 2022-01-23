import org.junit.Assert;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {
    private String expectedString = "test";

    @org.junit.jupiter.api.Test
    void getUsername() {
        Account account = new Account();
        account.setUsername("test");
        Assert.assertEquals(expectedString, account.getUsername());
    }

    @org.junit.jupiter.api.Test
    void getPassword() {
        Account account = new Account();
        account.setPassword("test");
        Assert.assertEquals(expectedString, account.getPassword());
    }

    @org.junit.jupiter.api.Test
    void getId() {
        Account account = new Account();
        account.setId(1);
        Assert.assertEquals(1, account.getId());
    }

    @org.junit.jupiter.api.Test
    void getName() {
        Account account = new Account();
        account.setName("test");
        Assert.assertEquals(expectedString, account.getName());
    }

    @org.junit.jupiter.api.Test
    void getSurname() {
        Account account = new Account();
        account.setSurname("test");
        Assert.assertEquals(expectedString, account.getSurname());
    }

    @org.junit.jupiter.api.Test
    void getLastName() {
        Account account = new Account();
        account.setLastName("test");
        Assert.assertEquals(expectedString, account.getLastName());
    }

    @org.junit.jupiter.api.Test
    void getDate() {
        //TODO
    }

    @org.junit.jupiter.api.Test
    void getPhone() {
        Account account = new Account();
        account.setPhone("test");
        Assert.assertEquals(expectedString, account.getPhone());
    }

    @org.junit.jupiter.api.Test
    void getIcq() {
        Account account = new Account();
        account.setIcq(1);
        Assert.assertEquals(1, account.getIcq());
    }

    @org.junit.jupiter.api.Test
    void getAddressHome() {
        Account account = new Account();
        account.setAddressHome("test");
        Assert.assertEquals(expectedString, account.getAddressHome());
    }

    @org.junit.jupiter.api.Test
    void getAddressJob() {
        Account account = new Account();
        account.setAddressJob("test");
        Assert.assertEquals(expectedString, account.getAddressJob());
    }

    @org.junit.jupiter.api.Test
    void getEmail() {
        Account account = new Account();
        account.setEmail("test");
        Assert.assertEquals(expectedString, account.getEmail());
    }

    @org.junit.jupiter.api.Test
    void getAboutMe() {
        Account account = new Account();
        account.setAboutMe("test");
        Assert.assertEquals(expectedString, account.getAboutMe());
    }

    @org.junit.jupiter.api.Test
    void setUsername() {
        Account account = new Account();
        account.setUsername("test");
        Assert.assertEquals(expectedString, account.getUsername());
    }

    @org.junit.jupiter.api.Test
    void setPassword() {
        Account account = new Account();
        account.setPassword("test");
        Assert.assertEquals(expectedString, account.getPassword());
    }

    @org.junit.jupiter.api.Test
    void setRole() {
        Account account = new Account();
        account.setRole(1);
        Assert.assertEquals(1, account.getRole());
    }

    @org.junit.jupiter.api.Test
    void setId() {
        Account account = new Account();
        account.setId(1);
        Assert.assertEquals(1, account.getId());
    }

    @Test
    void setName() {
        Account account = new Account();
        account.setName("test");
        Assert.assertEquals(expectedString, account.getName());
    }

    @org.junit.jupiter.api.Test
    void setSurname() {
        Account account = new Account();
        account.setSurname("test");
        Assert.assertEquals(expectedString, account.getSurname());
    }

    @org.junit.jupiter.api.Test
    void setLastName() {
        Account account = new Account();
        account.setLastName("test");
        Assert.assertEquals(expectedString, account.getLastName());
    }

    @org.junit.jupiter.api.Test
    void setDate() {
        //TODO
    }

    @org.junit.jupiter.api.Test
    void setPhone() {
        Account account = new Account();
        account.setPhone("test");
        Assert.assertEquals(expectedString, account.getPhone());
    }

    @org.junit.jupiter.api.Test
    void setIcq() {
        Account account = new Account();
        account.setIcq(1);
        Assert.assertEquals(1, account.getIcq());
    }

    @org.junit.jupiter.api.Test
    void setAddressHome() {
        Account account = new Account();
        account.setAddressHome("test");
        Assert.assertEquals(expectedString, account.getAddressHome());
    }

    @org.junit.jupiter.api.Test
    void setAddressJob() {
        Account account = new Account();
        account.setAddressJob("test");
        Assert.assertEquals(expectedString, account.getAddressJob());
    }

    @org.junit.jupiter.api.Test
    void setEmail() {
        Account account = new Account();
        account.setEmail("test");
        Assert.assertEquals(expectedString, account.getEmail());
    }

    @org.junit.jupiter.api.Test
    void setAboutMe() {
        Account account = new Account();
        account.setAboutMe("test");
        Assert.assertEquals(expectedString, account.getAboutMe());
    }

    @org.junit.jupiter.api.Test
    void testToString() {
        Account account = new Account();
        account.setUsername("test");
        Assert.assertEquals(expectedString, account.getUsername());
    }

}
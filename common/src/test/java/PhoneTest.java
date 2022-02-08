import org.junit.Assert;
import org.junit.jupiter.api.Test;

class PhoneTest {

    @Test
    void getIdPhone() {
        Phone phone = new Phone();
        phone.setIdPhone(1);
        Assert.assertEquals(1, phone.getIdPhone());
    }

    @Test
    void getPhoneNumber() {
        Phone phone = new Phone();
        phone.setPhoneNumber("test");
        Assert.assertEquals("1", phone.getPhoneNumber());
    }

    @Test
    void isSign() {
        Phone phone = new Phone();
        phone.setSign(true);
        Assert.assertEquals(true, phone.getIsSign());
    }
}
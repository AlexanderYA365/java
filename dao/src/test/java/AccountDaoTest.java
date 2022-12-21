import com.getjavajob.training.yakovleva.common.Account;
import com.getjavajob.training.yakovleva.common.Enum.PhoneType;
import com.getjavajob.training.yakovleva.common.Phone;
import com.getjavajob.training.yakovleva.common.Role;
import com.getjavajob.training.yakovleva.dao.AccountDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:socnet-context-test.xml"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Transactional
public class AccountDaoTest {

    private AccountDao accountDao;

    @Autowired
    public void ffff(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Test
    void createAccount() {
        List<Phone> phones = new ArrayList<>();
        Phone phone = new Phone(1, 1, "+766666666", PhoneType.HOME);
        phones.add(phone);
        Account account = new Account(0, "Alex", "Yyy", "uuuu", new Date(), phones, 1, "photo",
                new byte[]{01, 02}, "home", "job", "email", "about", "username",
                "password", Role.ROLE_USER);
        boolean result = accountDao.create(account);
        String expectedResultNewAccount = "Account{id=4, email='kolya1@mail', password='123', firstName='Nikolay', " +
                "lastName='Malcev', middleName='Nikolaevich', birthday='1982-12-13', photo=null, skype='dddd', " +
                "icq=1111, regDate='2018-06-13', role=USER, phones=null}";
        assertTrue(result);
        assertEquals(expectedResultNewAccount, accountDao.getAccount(0).toString());
    }


}
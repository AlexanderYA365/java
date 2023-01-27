import com.getjavajob.training.yakovleva.common.Account;
import com.getjavajob.training.yakovleva.common.Enum.PhoneType;
import com.getjavajob.training.yakovleva.common.Phone;
import com.getjavajob.training.yakovleva.common.Role;
import com.getjavajob.training.yakovleva.dao.AccountDao;
import org.h2.tools.RunScript;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(locations = {"classpath:socnet-context-test.xml"})
//@ContextConfiguration(classes = {DaoConfig.class})
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
//@Transactional


//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath:socnet-context-test.xml"})
@ComponentScan(basePackages = {"com.getjavajob.training.yakovleva.dao.*"})
@EnableJpaRepositories(basePackages = "com.getjavajob.training.yakovleva.dao")
public class AccountDaoTest {
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private DataSource dataSource;
    private Account accountCreateFroTest;
    private Account test1;
    private Account test2;


    @Before
    public void init() throws FileNotFoundException, SQLException {
        File file = new File(new AccountDaoTest().getClass().getResource("/create-data-model.sql").getPath());
        Reader reader = new FileReader(file);
        try (Connection connection = dataSource.getConnection();) {
            RunScript.execute(connection, reader);
        } catch (Exception e) {
            System.err.println(e);
        }
        List<Phone> phones = new ArrayList<>();
        Phone phone = new Phone(1, 1, "+766666666", PhoneType.HOME);
        phones.add(phone);
        accountCreateFroTest = new Account(0, "Alex", "Yyy", "uuuu", new Date(), phones, 1, "photo",
                new byte[]{01, 02}, "home", "job", "email", "about", "username",
                "password", Role.ROLE_USER);
        test1 = new Account(0, "test1", "test1", "test1", new Date(), phones, 1, "test1",
                new byte[]{01, 02}, "test1", "test1", "test1", "test1", "test1",
                "test1", Role.ROLE_USER);
        test2 = new Account(0, "test2", "test2", "test2", new Date(), phones, 1, "test2",
                new byte[]{01, 02}, "test2", "test2", "test2", "test2", "test2",
                "test2", Role.ROLE_USER);
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
import com.getjavajob.training.yakovleva.common.Account;
import com.getjavajob.training.yakovleva.common.Phone;
import com.getjavajob.training.yakovleva.common.PhoneType;
import com.getjavajob.training.yakovleva.common.Role;
import com.getjavajob.training.yakovleva.dao.AccountDao;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:socnet-context-test.xml")
@SqlGroup({
        @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = {"classpath:create-data-model.sql",
                "classpath:fillDB.sql"}),
        @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:remove.sql")
})
@Transactional
class AccountDaoTest {

    @Autowired
    private AccountDao accountDAO;

    @Test
    void createAccount() {
        List<Phone> phones = new ArrayList<>();
        Phone phone = new Phone(1, 1, "+766666666", PhoneType.HOME);
        phones.add(phone);
        Account account = new Account(0, "Alex", "Yyy", "uuuu", new Date(), phones, 1, "photo",
                new byte[]{01, 02}, "home", "job", "email", "about", "username",
                "password", Role.USER);
        boolean result = accountDAO.create(account);
        String expectedResultNewAccount = "Account{id=4, email='kolya1@mail', password='123', firstName='Nikolay', " +
                "lastName='Malcev', middleName='Nikolaevich', birthday='1982-12-13', photo=null, skype='dddd', " +
                "icq=1111, regDate='2018-06-13', role=USER, phones=null}";
        assertTrue(result);
        assertEquals(expectedResultNewAccount, accountDAO.getAccount(0).toString());
    }

    @Test
    void updateAccount() {

    }

    @Configuration
    @PropertySource("classpath:database-test.properties")
    static class Config {

        @Autowired
        private Environment env;

        @Bean
        public DataSource dataSource() {
            BasicDataSource dataSource = new BasicDataSource();
            dataSource.setUrl(env.getProperty("database.url"));
            dataSource.setUsername(env.getProperty("database.user"));
            dataSource.setPassword(env.getProperty("database.password"));
            return dataSource;
        }
    }

}
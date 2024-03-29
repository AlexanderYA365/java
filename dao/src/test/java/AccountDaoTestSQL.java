import com.getjavajob.training.yakovleva.common.Account;
import com.getjavajob.training.yakovleva.dao.AccountDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "classpath:socnet-context-test.xml")
//@SqlGroup({
//        @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = {"classpath:create-data-model.sql", "classpath:fillDB.sql"}),
//        @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:remove.sql")
//})
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
//@Transactional

//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = {DaoConfig.class, DaoConfig.class})
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
//@Transactional

public class AccountDaoTestSQL {

    @Autowired
    private static final Logger logger = LogManager.getLogger(AccountDaoTestSQL.class);
    @Autowired
    private AccountDao accountDao;

    @Test
    public void create() {
        Account account = new Account();
        account.setName("Alex");
        account.setLastName("Alexandrov");
        account.setSurname("test");
        accountDao.create(account);
    }
//    @Before
//    public void createDB() {
//        System.out.println("ggggg");
//    }

//    @Test
//    public void cr() {
//        System.out.println("uuuuu");
//    }

//    @Configuration
//    @PropertySource("classpath:database-test.properties")
//    static class Config {
//
//        @Autowired
//        private Environment env;
//
//        @Bean
//        public DataSource dataSource() {
//            BasicDataSource dataSource = new BasicDataSource();
//            dataSource.setUrl(env.getProperty("database.url"));
//            dataSource.setUsername(env.getProperty("database.user"));
//            dataSource.setPassword(env.getProperty("database.password"));
//            System.out.println("dataSource - " + dataSource);
//            return dataSource;
//        }
//
//    }

}

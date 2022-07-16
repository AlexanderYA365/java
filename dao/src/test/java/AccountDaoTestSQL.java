import com.getjavajob.training.yakovleva.dao.AccountDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:socnet-context-test.xml")
@SqlGroup({
        @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = {"classpath:create-data-model.sql", "classpath:fillDB.sql"}),
        @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:remove.sql")
})
public class AccountDaoTestSQL {

    @Autowired
    private AccountDao accountDao;

    @Before
    public void createDB() {
        System.out.println("ggggg");
    }

    @Test
    public void cr() {
        System.out.println("uuuuu");
    }

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
//        @Bean
//        public JdbcTemplate jdbcTemplate() {
//            return new JdbcTemplate(dataSource());
//        }
//    }

}

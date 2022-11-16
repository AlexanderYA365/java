package config;

import org.springframework.context.annotation.Configuration;

@Configuration

public class DaoConfig {

//    @Value("${datasource.driver_class_name}")
//    private String driverClassName;
//
//    @Value("${datasource.url}")
//    private String url;
//
//    @Value("${datasource.username}")
//    private String userName;
//
//    @Value("${datasource.password}")
//    private String password;
//
//    @Value("${hibernate.hbm2ddl.auto}")
//    private String hibernateHbm2ddlAuto;
//
//    @Value("${hibernate.dialect}")
//    private String hibernateDialect;
//
//    @Value("${hibernate.show_sql}")
//    private String hibernateShowSQL;
//
//    @Value("${hibernate.format_sql}")
//    private String hibernateFormatSQL;
//
//    @Bean
//    public DataSource dataSource() {
//        BasicDataSource dataSource = new BasicDataSource();
//        dataSource.setDriverClassName(driverClassName);
//        dataSource.setUrl(url);
//        dataSource.setUsername(userName);
//        dataSource.setPassword(password);
//        return dataSource;
//    }
//
//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
//        em.setDataSource(dataSource());
//        em.setPackagesToScan("com.getjavajob.common");
//        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//        em.setJpaVendorAdapter(vendorAdapter);
//        em.setJpaProperties(hibernateProperties());
//        return em;
//    }
//
//    @Bean
//    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
//        JpaTransactionManager transactionManager = new JpaTransactionManager();
//        transactionManager.setEntityManagerFactory(emf);
//        return transactionManager;
//    }
//
//    private Properties hibernateProperties() {
//        Properties properties = new Properties();
//        properties.put("hibernate.hbm2ddl.auto", hibernateHbm2ddlAuto);
//        properties.put("hibernate.dialect", hibernateDialect);
//        properties.put("hibernate.show_sql", hibernateShowSQL);
//        properties.put("hibernate.format_sql", hibernateFormatSQL);
//        return properties;
//    }
}

package com.getjavajob.training.yakovleva.dao;

import com.getjavajob.training.yakovleva.common.Account;
import com.getjavajob.training.yakovleva.common.Relations;
import com.getjavajob.training.yakovleva.config.DaoConfig;
import config.DaoConfigTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {DaoConfig.class, DaoConfigTest.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Transactional
@ActiveProfiles("test")
class RelationsDaoTest {
    @Autowired
    private RelationsDao relationsDao;
    @Autowired
    private AccountDao accountDao;
    private String testString = "test";
    private int testInt = 1;

    @BeforeEach
    void createTestData() {
        Account account = new Account();
        Account account1 = new Account();
        account.setUsername(testString);
        account.setPassword(testString);
        account1.setUsername(testString);
        account1.setPassword(testString);
        accountDao.create(account);
        accountDao.create(account1);
        Relations relations = new Relations();
        relations.setFriendId(1);
        relations.setAccountId(2);
        relationsDao.create(relations);
    }

    @Test
    void create() {
        Account account = new Account();
        account.setUsername(testString);
        account.setName(testString);
        account.setPassword(testString);
        account.setSurname(testString);
        account.setLastName(testString);
        account.setRole(testInt);
        account.setAddressJob(testString);
        account.setAddressHome(testString);
        account.setPhotoFileName(testString);
        account.setEmail(testString);
        account.setAboutMe(testString);
        account.setIcq(testInt);
        accountDao.create(account);
        Relations relations = new Relations(testInt, testInt);
        relationsDao.create(relations);
    }

    @Test
    void getByAccountID() {
    }

    @Test
    void getByFriendId() {
    }

    @Test
    void update() {
    }

    @Test
    void deleteByAccountId() {
    }
}
package com.getjavajob.training.yakovleva.service;

import com.getjavajob.training.yakovleva.common.Account;
import com.getjavajob.training.yakovleva.common.Application;
import com.getjavajob.training.yakovleva.common.Phone;
import com.getjavajob.training.yakovleva.dao.AccountDao;
import com.getjavajob.training.yakovleva.dao.ApplicationDao;
import com.getjavajob.training.yakovleva.dao.PhoneDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AccountService {
    private static final Logger logger = LogManager.getLogger(AccountService.class);
    private AccountDao accountDao;
    private PhoneDao phoneDao;
    private ApplicationDao applicationDao;

    @Autowired
    public AccountService(AccountDao accountDao, PhoneDao phoneDao, ApplicationDao applicationDao) {
        logger.info("AccountService(AccountDao accountDao, PhoneDao phoneDao)");
        this.accountDao = accountDao;
        this.phoneDao = phoneDao;
        this.applicationDao = applicationDao;
    }

    public AccountService() {
    }

    public boolean create(Account account, List<Phone> phones) {
        logger.info("create(account = {}, phones = {})", account, phones);
        try {
            boolean result = accountDao.create(account);
            logger.info("result = {}", result);
            int id = accountDao.getIdAccount(account);
            logger.info("id = {}", id);
            for (Phone phone : phones) {
                phone.setAccountId(id);
                phoneDao.create(phone);
            }
            return id > 0 || result;
        } catch (Exception e) {
            logger.info("AccountService.create Exception - {}", e.toString());
            return false;
        }
    }

    @Transactional
    public void createAccounts(List<Account> accounts) {
        logger.info("createAccounts(List<Account> accounts)");
        logger.debug("createAccounts(accounts - {})", accounts);
        try {
            accountDao.createAccounts(accounts);
        } catch (Exception ex) {
            logger.error("create accounts exception - {}", ex);
        }
    }

    public int getId(Account account) {
        logger.info("getId(Account account)");
        logger.debug("get(account - {})", account);
        return accountDao.getIdAccount(account);
    }

    @Transactional
    public boolean update(Account account) {
        logger.info("update(account - {})", account);
        try {
            int id = account.getId() == 0 ? accountDao.getIdAccount(account) : account.getId();
            boolean result = false;
            if (account.getPhones().size() != 0) {
                for (Phone phone : account.getPhones()) {
                    phone.setAccountId(id);
                    result = phoneDao.update(phone);
                }
            }
            return accountDao.updateAccount(account) || result;
        } catch (Exception e) {
            logger.error("create exception =" + e);
            return false;
        }
    }

    @Transactional
    public boolean delete(Account account) {
        logger.info("delete(Account account)");
        logger.debug("delete(account - {})", account);
        try {
            int id = accountDao.getIdAccount(account);
            boolean result = false;
            for (Phone phone : account.getPhones()) {
                phone.setAccountId(id);
                result = phoneDao.delete(phone);
            }
            return accountDao.deleteAccount(account) || result;
        } catch (Exception e) {
            logger.error("create Exception - {}", e);
            return false;
        }
    }

    @Transactional
    public boolean deleteById(int accountId) {
        logger.info("deleteById(accountId = {})", accountId);
        try {
            Account account = accountDao.getAccount(accountId);
            boolean result = false;
            for (Phone phone : account.getPhones()) {
                phone.setAccountId(accountId);
                result = phoneDao.delete(phone);
            }
            return accountDao.deleteAccount(account) || result;
        } catch (Exception e) {
            logger.error("create Exception - {}", e);
            return false;
        }
    }

    public Account get(int accountId) {
        logger.info("get(accountId = {})", accountId);
        Account account = accountDao.getAccount(accountId);
        return account;
    }

    public List<Account> getFriendRequests(Account account) {
        logger.info("getFriendRequests(account = {})", account);
        List<Application> applications = applicationDao.get(account);
        List<Account> accounts = new ArrayList<>();
        for (int i = 0; i < applications.size(); i++) {
            accounts.add(accountDao.getAccount(applications.get(i).getApplicantId()));
        }
        return accounts;
    }

    public List<Account> getAllAccounts() {
        logger.info("getAllAccounts()");
        return accountDao.getAllAccounts();
    }

    public List<Account> getAccountsCriteriaLimit(int start, int end, String criteriaName) {
        logger.info("getAccountsCriteriaLimit(int start, int end, String criteriaName)");
        logger.debug("getAccountsCriteriaLimit(start = {}, end = {}, criteriaName = {})", start, end, criteriaName);
        return accountDao.getAccountsCriteriaLimit(start, end, criteriaName);
    }

    public List<Account> getAllAccountsLimit(int start, int end) {
        logger.info("getAllAccountsLimit(int start, int end)");
        logger.debug("getAllAccountsLimit(start = {}, end = {})", start, end);
        return accountDao.getAccountsLimit(start, end);
    }

    public long getSizeRecords() {
        logger.info("getSizeRecords()");
        return accountDao.getSizeRecords();
    }

    public long getSizeRecords(String search) {
        logger.info("getSizeRecords()");
        return accountDao.getSizeRecords(search);
    }

    public List<Account> getAccountName(String name) {
        logger.info("getAccountName(String name)");
        logger.debug("getAccountName(name = {})", name);
        return accountDao.getAccountsName(name);
    }

    public Account getByUsername(String username) {
        logger.info("getAccountName(String name)");
        logger.debug("getAccountName(name = {})", username);
        return accountDao.getByUsername(username);
    }

    public List<Account> getFriendsAccount(int accountId) {
        logger.info("getFriendsAccount(int accountId)");
        logger.debug("getFriendsAccount(accountId = {})", accountId);
        return accountDao.getFriendsAccount(accountId);
    }

}
package com.getjavajob.training.yakovleva.service;

import com.getjavajob.training.yakovleva.common.Account;
import com.getjavajob.training.yakovleva.common.Phone;
import com.getjavajob.training.yakovleva.dao.AccountDao;
import com.getjavajob.training.yakovleva.dao.PhoneDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class AccountService {
    private AccountDao accountDao;
    private PhoneDao phoneDao;

    @Autowired
    public AccountService(AccountDao accountDao, PhoneDao phoneDao) {
        this.accountDao = accountDao;
        this.phoneDao = phoneDao;
    }

    public boolean create(Account account) {
        System.out.println("Creat new Account from AccountService.create");
        try {
            accountDao.create(account);
            int id = accountDao.getIdAccount(account);
            boolean result = false;
            for (Phone phone : account.getPhones()) {
                phone.setAccountId(id);
                phoneDao.create(phone);
            }
            return id > 0 || result;
        } catch (Exception e) {
            System.out.println("create Exception - " + e);
            return false;
        }
    }

    public void createAccounts(List<Account> accounts) {
        System.out.println("create accounts");
        try {
            accountDao.createAccounts(accounts);
        } catch (Exception ex) {
            System.out.println("create accounts exception - " + ex);
        }
    }

    public int getId(Account account) {
        System.out.println("get id - " + account);
        return accountDao.getIdAccount(account);
    }

    public boolean update(Account account) {
        System.out.println("Account update accountId - " + account.getId());
        try {
            int id = accountDao.getIdAccount(account);
            boolean result = false;
            if (account.getPhones().size() != 0) {
                for (Phone phone : account.getPhones()) {
                    phone.setAccountId(id);
                    result = phoneDao.update(phone);
                }
            }
            return accountDao.updateAccount(account) || result;
        } catch (Exception e) {
            System.out.println("create Exception - " + e);
            return false;
        }
    }

    public boolean delete(Account account) {
        System.out.println("Account delete accountId - " + account);
        try {
            int id = accountDao.getIdAccount(account);
            boolean result = false;
            for (Phone phone : account.getPhones()) {
                phone.setAccountId(id);
                result = phoneDao.delete(phone);
            }
            return accountDao.deleteAccount(account) || result;
        } catch (Exception e) {
            System.out.println("create Exception - " + e);
            return false;
        }
    }

    public Account get(int accountId) {
        System.out.println("Account read accountId - " + accountId);
        Account account = accountDao.getAccount(accountId);
        List<Phone> phones = phoneDao.get(accountId);
        System.out.println(phones);
        account.setPhones(phones);
        return account;
    }

    public List<Account> getAllAccounts() {
        System.out.println("read all account dao");
        return accountDao.getAccounts();
    }

    public List<Account> getAllAccountsLimit(int start, int end) {
        System.out.println("getAllAccountsLimit");
        return accountDao.getAccountsLimit(start, end);
    }

    public List<Account> getAccountName(String name) {
        System.out.println("getAccountName, name - " + name);
        return accountDao.getAccountsName(name);
    }

    public Account getAccount(String username, String password) {
        System.out.println("getAccount(String username, String password)");
        Account account = accountDao.getAccount(username, password);
        List<Phone> phones = phoneDao.get(account.getId());
        account.setPhones(phones);
        return account;
    }

    public List<Account> getFriendsAccount(int accountId) {
        System.out.println("List<Account> getFriendAccount");
        return accountDao.getFriendsAccount(accountId);
    }
}
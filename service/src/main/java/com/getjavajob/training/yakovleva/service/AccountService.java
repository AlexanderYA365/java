package com.getjavajob.training.yakovleva.service;

import com.getjavajob.training.yakovleva.dao.*;

import java.util.List;

public class AccountService {
    private AccountDao accountDAO;
    private PhoneDao phoneDao;

    public AccountService(AccountDao accountDao, PhoneDao phoneDao) {
        this.accountDAO = accountDao;
        this.phoneDao = phoneDao;
    }

    public boolean create(Account account) {
        System.out.println("Creat new Account from AccountService.create");
        try {
            accountDAO.create(account);
            int id = accountDAO.getIdAccount(account);
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

    public boolean update(Account account) {
        System.out.println("Account update accountId - " + account.getId());
        try {
            int id = accountDAO.getIdAccount(account);
            boolean result = false;
            for (Phone phone : account.getPhones()) {
                phone.setAccountId(id);
                result = phoneDao.update(phone);
            }
            return accountDAO.updateAccount(account) || result;
        } catch (Exception e) {
            System.out.println("create Exception - " + e);
            return false;
        }
    }

    public boolean delete(Account account) {
        System.out.println("Account delete accountId - " + account);
        try {
            int id = accountDAO.getIdAccount(account);
            boolean result = false;
            for (Phone phone : account.getPhones()) {
                phone.setAccountId(id);
                result = phoneDao.delete(phone);
            }
            return accountDAO.deleteAccount(account) || result;
        } catch (Exception e) {
            System.out.println("create Exception - " + e);
            return false;
        }
    }

    public Account get(int accountId) {
        System.out.println("Account read accountId - " + accountId);
        Account account = accountDAO.getAccount(accountId);
        List<Phone> phones = phoneDao.get(accountId);
        System.out.println(phones);
        account.setPhones(phones);
        return account;
    }

    public List<Account> getAllAccounts() {
        System.out.println("read all account dao");
        return accountDAO.getAccounts();
    }

    public List<Account> getAccountName(String name) {
        System.out.println("getAccountName, name - " + name);
        return accountDAO.getAccountsName(name);
    }

    public Account getAccount(String username, String password) {
        System.out.println("getAccount(String username, String password)");
        Account account = accountDAO.getAccount(username, password);
        List<Phone> phones = phoneDao.get(account.getId());
        account.setPhones(phones);
        return account;
    }

    public List<Account> getFriendsAccount(int accountId) {
        System.out.println("List<Account> getFriendAccount");
        return accountDAO.getFriendsAccount(accountId);
    }
}
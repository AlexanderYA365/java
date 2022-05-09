package com.getjavajob.training.yakovleva.service;

import com.getjavajob.training.yakovleva.dao.Account;
import com.getjavajob.training.yakovleva.dao.AccountDao;
import com.getjavajob.training.yakovleva.dao.Phone;
import com.getjavajob.training.yakovleva.dao.PhoneDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    private AccountDao accountDAO;
    private PhoneDao phoneDao;

    @Autowired
    public AccountService(AccountDao accountDao, PhoneDao phoneDao) {
        this.accountDAO = accountDao;
        this.phoneDao = phoneDao;
    }

    public boolean create(Account account) {
        System.out.println("Creat new Account from AccountService.create");
        try {
            accountDAO.createAccount(account);
            int id = accountDAO.readIdAccount(account);
            for (Phone phone : account.getPhones()) {
                phone.setIdAccount(id);
                phoneDao.create(phone);
            }
            return true;
        } catch (Exception e) {
            System.out.println("create Exception - " + e);
            return false;
        }
    }

    public boolean update(Account account) {
        System.out.println("Account update idAccount - " + account.getId());
        try {
            accountDAO.updateAccount(account);
            int id = accountDAO.readIdAccount(account);
            for (Phone phone : account.getPhones()) {
                phone.setIdAccount(id);
                phoneDao.update(phone);
            }
            return true;
        } catch (Exception e) {
            System.out.println("create Exception - " + e);
            return false;
        }
    }

    public boolean delete(Account account) {
        System.out.println("Account delete idAccount - " + account);
        try {
            accountDAO.deleteAccount(account);
            int id = accountDAO.readIdAccount(account);
            for (Phone phone : account.getPhones()) {
                phone.setIdAccount(id);
                phoneDao.delete(phone);
            }
            return true;
        } catch (Exception e) {
            System.out.println("create Exception - " + e);
            return false;
        }
    }

    public Account read(int idAccount) {
        System.out.println("Account read idAccount - " + idAccount);
        return accountDAO.readAccount(idAccount);
    }

    public List<Account> getAllAccounts() {
        System.out.println("read all account dao");
        return accountDAO.readAccounts();
    }

    public List<Account> getAccountName(String name) {
        System.out.println("getAccountName, name - " + name);
        return accountDAO.readAccountsName(name);
    }

    public Account getAccount(String username, String password) {
        System.out.println("getAccount(String username, String password)");
        Account account = accountDAO.readAccount(username, password);
        List<Phone> phones = phoneDao.read(account.getId());
        account.setPhones(phones);
        return account;
    }

}
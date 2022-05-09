package com.getjavajob.training.yakovleva.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class AccountDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public void createAccount(Account account) {
        String sql = "INSERT INTO account(name, surname, lastname, date, icq, addresshome, " +
                "addressjob, email, aboutme, username, password, role) " +
                "VALUES (?,?,?,NOW(),?,?,?,?,?,?,?,?);";
        System.out.println(sql);
        jdbcTemplate.update(sql,
                account.getName(), account.getSurname(), account.getLastName(), account.getIcq(),
                account.getAddressHome(), account.getAddressJob(), account.getEmail(), account.getAboutMe(),
                account.getUsername(), account.getPassword(), account.getRole());
    }

    public int readIdAccount(Account account) {
        System.out.println("readIdAccount - account = " + account);
        String sql = "SELECT idAccount FROM account WHERE username LIKE ? AND password LIKE ?";
        return jdbcTemplate.queryForObject(sql,
                new Object[]{account.getUsername(), account.getPassword()},
                (resultSet, i) -> fillAccount(resultSet)).getId();
    }

    public Account readAccount(String username, String password) {
        System.out.println("readAccount - username = " + username + " ,password = " + password);
        String sql = "SELECT * FROM account WHERE username LIKE ? AND password LIKE ?";
        return jdbcTemplate.query(sql,
                new Object[]{username, password}, (resultSet, i) -> fillAccount(resultSet)).get(0);
    }

    public Account readAccount(int id) {
        System.out.println("readAccount(int id)");
        String sql = "SELECT * FROM account WHERE idAccount = ?";
        return jdbcTemplate.query(sql,
                new Object[]{id}, (resultSet, i) -> fillAccount(resultSet)).get(0);
    }

    public List<Account> readAccountsName(String name) {
        System.out.println("readAccountsName");
        String sql = "SELECT * FROM account WHERE name LIKE ?";
        System.out.println(sql);
        return jdbcTemplate.query(sql,
                new Object[]{name}, (resultSet, i) -> fillAccount(resultSet));
    }

    public List<Account> readAccounts() {
        System.out.println("readAccountsName");
        String sql = "SELECT * FROM account";
        return jdbcTemplate.query(sql, (resultSet, i) -> fillAccount(resultSet));
    }

    @Transactional
    public void deleteAccount(Account account) {
        System.out.println("readAccountsName");
        String sql = "DELETE FROM account WHERE idAccount = ?";
        jdbcTemplate.update(sql, account.getId());
    }

    @Transactional
    public void updateAccount(Account account) {
        System.out.println("updateAccount");
        String sql = "UPDATE account SET name ='?', surname ='?', lastname = '?', date = NOW(), icq = ?, " +
                "addresshome ='?', addressjob ='?'," +
                " email ='?', aboutme ='?', role = ? WHERE idAccount = ?";
        jdbcTemplate.update(sql,
                account.getName(), account.getSurname(), account.getLastName(), account.getIcq(),
                account.getAddressHome(), account.getAddressJob(), account.getEmail(), account.getAboutMe(),
                account.getRole(), account.getId());
    }

    private Account fillAccount(ResultSet resultSet) {
        Account account = new Account();
        try {
            account.setId(resultSet.getInt(1));
            account.setName(resultSet.getString(2));
            account.setSurname(resultSet.getString(3));
            account.setLastName(resultSet.getString(4));
            account.setDate(resultSet.getDate(5));
            account.setIcq(resultSet.getInt(6));
            account.setAddressHome(resultSet.getString(7));
            account.setAddressJob(resultSet.getString(8));
            account.setEmail(resultSet.getString(9));
            account.setAboutMe(resultSet.getString(10));
            account.setUsername(resultSet.getString(11));
            account.setPassword(resultSet.getString(12));
            account.setRole(resultSet.getInt(13));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return account;
    }

}
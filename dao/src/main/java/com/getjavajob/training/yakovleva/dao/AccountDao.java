package com.getjavajob.training.yakovleva.dao;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class AccountDao {
    private final JdbcTemplate jdbcTemplate;

    public AccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create(Account account) {
        String sql = "INSERT INTO account(name, surname, lastname, date, icq, address_home, " +
                "address_job, email, about_me, username, password, role, photo, photo_file_name) " +
                "VALUES (?,?,?,NOW(),?,?,?,?,?,?,?,?,?,?);";
        System.out.println(sql);
        jdbcTemplate.update(sql,
                account.getName(), account.getSurname(), account.getLastName(), account.getIcq(),
                account.getAddressHome(), account.getAddressJob(), account.getEmail(), account.getAboutMe(),
                account.getUsername(), account.getPassword(), account.getRole(),
                account.getPhoto(), account.getPhotoFileName());
    }

    public int getIdAccount(Account account) {
        System.out.println("getIdAccount - account = " + account);
        String sql = "SELECT * FROM account WHERE username LIKE ? AND password LIKE ?";
        return Objects.requireNonNull(jdbcTemplate.queryForObject(sql,
                new Object[]{account.getUsername(), account.getPassword()},
                (resultSet, i) -> fillAccount(resultSet))).getId();
    }

    public Account getAccount(String username, String password) {
        System.out.println("getAccount - username = " + username + " ,password = " + password);
        String sql = "SELECT * FROM account WHERE username LIKE ? AND password LIKE ?";
        return jdbcTemplate.query(sql,
                new Object[]{username, password}, (resultSet, i) -> fillAccount(resultSet)).get(0);
    }

    public Account getAccount(int id) {
        System.out.println("getAccount(int id)");
        String sql = "SELECT * FROM account WHERE account_id = ?";
        return jdbcTemplate.query(sql,
                new Object[]{id}, (resultSet, i) -> fillAccount(resultSet)).get(0);
    }

    public List<Account> getAccountsName(String name) {
        System.out.println("getAccountsName");
        String sql = "SELECT * FROM account WHERE name LIKE ?";
        System.out.println(sql);
        return jdbcTemplate.query(sql,
                new Object[]{name}, (resultSet, i) -> fillAccount(resultSet));
    }

    public List<Account> getAccounts() {
        System.out.println("getAccounts");
        String sql = "SELECT * FROM account";
        return jdbcTemplate.query(sql, (resultSet, i) -> fillAccount(resultSet));
    }

    public boolean deleteAccount(Account account) {
        System.out.println("readAccountsName");
        String sql = "DELETE FROM account WHERE account_id = ?";
        return jdbcTemplate.update(sql, account.getId()) > 0;
    }

    public boolean updateAccount(Account account) {
        System.out.println("updateAccount - " + account);
        String sql = "UPDATE account SET name = ?," +
                " surname = ?," +
                " lastname = ?, date = ?, icq = ?, " +
                "address_home = ?, address_job = ?," +
                " email = ?, about_me = ?," +
                " role = ?," +
                " photo = ?," +
                "photo_file_name = ?" +
                " WHERE account_id = ?";
        boolean result = false;
        System.out.println("photo - " + account.getPhoto());
        System.out.println("photoFileName - " + account.getPhotoFileName());
        try {
            result = jdbcTemplate.update(sql, account.getName(),
                    account.getSurname(),
                    account.getLastName(), account.getDate(), account.getIcq(),
                    account.getAddressHome(), account.getAddressJob(),
                    account.getEmail(), account.getAboutMe(),
                    account.getRole(),
                    account.getPhoto(),
                    account.getPhotoFileName(),
                    account.getId()) > 0;
        } catch (Exception ex) {
            System.out.println("updateAccount exception - " + ex);
        }
        return result;
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
            account.setPhoto(resultSet.getBytes(14));
            account.setPhotoFileName(resultSet.getString(15));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return account;
    }

    public List<Account> getFriendsAccount(int accountId) {
        System.out.println("List<Account> getFriendsAccount");
        String sql = "SELECT a.account_id, a.name, a.surname, a.lastname, " +
                "a.date, a.icq, a.address_home, a.address_job, a.email, " +
                "a.about_me, a.username, a.password, a.role, a.photo, a.photo_file_name FROM account a, " +
                "relations r WHERE a.account_id = r.friend_id and r.account_id = ?";
        return jdbcTemplate.query(sql,
                new Object[]{accountId},
                (resultSet, i) -> fillAccount(resultSet));
    }

    public void createAccounts(List<Account> accounts) {
        String sql = "INSERT INTO account(name, surname, lastname, date, icq, address_home, " +
                "address_job, email, about_me, " +
                "username, password, role, photo, photo_file_name) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
        System.out.println(sql);
        jdbcTemplate.batchUpdate(sql,
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, accounts.get(i).getName());
                        ps.setString(2, accounts.get(i).getSurname());
                        ps.setString(3, accounts.get(i).getLastName());
                        ps.setDate(4, new java.sql.Date(accounts.get(i).getDate().getTime()));
                        ps.setInt(5, accounts.get(i).getIcq());
                        ps.setString(6, accounts.get(i).getAddressHome());
                        ps.setString(7, accounts.get(i).getAddressJob());
                        ps.setString(8, accounts.get(i).getEmail());
                        ps.setString(9, accounts.get(i).getAboutMe());
                        ps.setString(10, accounts.get(i).getUsername());
                        ps.setString(11, accounts.get(i).getPassword());
                        ps.setInt(12, accounts.get(i).getRole());
                        ps.setBytes(13, accounts.get(i).getPhoto());
                        ps.setString(14, accounts.get(i).getPhotoFileName());
                    }

                    @Override
                    public int getBatchSize() {
                        return accounts.size();
                    }
                });
    }
}
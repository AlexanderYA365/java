package com.getjavajob.training.yakovleva.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jndi.JndiTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AccountDao {
    private JndiTemplate jndiTemplate;

    @Autowired
    public AccountDao(JndiTemplate jndiTemplate){
        this.jndiTemplate = jndiTemplate;
    }

    public void createAccount(Account account) {
        String sql = "INSERT INTO account(name, surname, lastname, date, icq, addresshome, " +
                "addressjob, email, aboutme, username, password, role) " +
                "VALUES (?,?,?,NOW(),?,?,?,?,?,?,?,?);";
        System.out.println(sql);
        try (Connection connection = connectionPool.getConnection();//TODO
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            fillAllFieldQuery(account, preparedStatement);
            System.out.println(preparedStatement);
            int rows = preparedStatement.executeUpdate();
            System.out.println("Added " + rows + " rows");
        } catch (Exception ex) {
            System.out.println("createAccount Exception - " + ex);
        }
    }

    public int readIdAccount(Account account) {
        System.out.println("readIdAccount - account = " + account);
        String sql = "SELECT idAccount FROM account WHERE username = ? AND password = ?";
        try (Connection connection = connectionPool.getConnection();//TODO
             PreparedStatement query = connection.prepareStatement(sql)) {
            query.setString(1, account.getUsername());
            query.setString(2, account.getPassword());
            ResultSet resultSet = query.executeQuery();
            while (resultSet.next()) {
                account.setId(resultSet.getInt(1));
            }
        } catch (Exception ex) {
            System.out.println("readIdAccount Exception - " + ex);
        }
        return account.getId();
    }

    public Account readAccount(String username, String password) {
        System.out.println("readAccount - username = " + username + " ,password = " + password);
        Account account = new Account();
        String sql = "SELECT * FROM account WHERE username = ? AND password = ?";
        try (Connection connection = connectionPool.getConnection();//TODO
             PreparedStatement query = connection.prepareStatement(sql)) {
            query.setString(1, username);
            query.setString(2, password);
            fillFieldResult(account, query);
        } catch (Exception ex) {
            System.out.println("readAccount Exception - " + ex);
        }
        return account;
    }

    public Account readAccount(int id) {
        System.out.println("readAccount(int id)");
        Account account = new Account();
        String sql = "SELECT * FROM account WHERE idAccount = ?";
        try (Connection connection = connectionPool.getConnection();//TODO
             PreparedStatement query = connection.prepareStatement(sql)) {
            query.setInt(1, id);
            fillFieldResult(account, query);
        } catch (Exception ex) {
            System.out.println("readAccount Exception - " + ex);
        }
        return account;
    }

    public List<Account> readAccountsName(String name) {
        System.out.println("readAccountsName");
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM account WHERE name = ?";
        System.out.println(sql);
        try (Connection connection = connectionPool.getConnection();//TODO
             PreparedStatement query = connection.prepareStatement(sql)) {
            query.setString(1, name);
            try (ResultSet resultSet = query.executeQuery()) {
                while (resultSet.next()) {
                    Account account = new Account();
                    fillAccountInfoResult(account, resultSet);
                    accounts.add(account);
                }
            }
        } catch (Exception ex) {
            System.out.println("readAccountsName Exception - " + ex);
        }
        return accounts;
    }

    public List<Account> readAccounts() {//TODO
        System.out.println("readAccountsName");
        List<Account> accounts = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery("SELECT * FROM account")) {
                    while (resultSet.next()) {
                        Account account = new Account();
                        fillAccountInfoResult(account, resultSet);
                        accounts.add(account);
                    }
                }
            } catch (Exception ex) {
                System.out.println("readAccounts Exception - " + ex);
            }
        } catch (Exception ex) {
            System.out.println("readAccounts Exception - " + ex);
        }
        return accounts;
    }

    public void deleteAccount(Account account) {
        try (Connection connection = connectionPool.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate("DELETE FROM account WHERE idAccount = " + account.getId());
            } catch (SQLException ex) {
                System.out.println("deleteAccount Exception - " + ex);
            }
        } catch (SQLException ex) {
            System.out.println("deleteAccount Exception - " + ex);
        }
    }

    public void updateAccount(Account account) {
        System.out.println("updateAccount");
        String sql = "UPDATE account SET name ='?', surname ='?', lastname = '?', date = NOW(), icq = ?, " +
                "addresshome ='?', addressjob ='?'," +
                " email ='?', aboutme ='?', role = ? WHERE idAccount = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement query = connection.prepareStatement(sql)) {
                fillAccountInfoQuery(account, query);
                query.setInt(9, account.getRole());
                query.setInt(10, account.getId());
                System.out.println("updateAccount - " + sql);
                int rows = query.executeUpdate();
                System.out.println("Updated rows = " + rows);
            } catch (Exception ex) {
                System.out.println("Connection failed...updateAccount - " + ex);
            }
        } catch (Exception ex) {
            System.out.println("Connection failed...updateAccount - " + ex);
        }
    }

    private void fillAllFieldQuery(Account account, PreparedStatement preparedStatement) {
        try {
            fillAccountInfoQuery(account, preparedStatement);
            preparedStatement.setString(9, account.getUsername());
            preparedStatement.setString(10, account.getPassword());
            preparedStatement.setInt(11, account.getRole());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void fillAccountInfoQuery(Account account, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, account.getName());
        preparedStatement.setString(2, account.getSurname());
        preparedStatement.setString(3, account.getLastName());
        preparedStatement.setInt(4, account.getIcq());
        preparedStatement.setString(5, account.getAddressHome());
        preparedStatement.setString(6, account.getAddressJob());
        preparedStatement.setString(7, account.getEmail());
        preparedStatement.setString(8, account.getAboutMe());
    }

    private void fillFieldResult(Account account, PreparedStatement query) {
        try (ResultSet resultSet = query.executeQuery()) {
            while (resultSet.next()) {
                fillAccountInfoResult(account, resultSet);
            }
        } catch (Exception ex) {
            System.out.println("fillFieldResult exception - " + ex);
        }
    }

    private void fillAccountInfoResult(Account account, ResultSet resultSet) throws SQLException {
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
    }

}
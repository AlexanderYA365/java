import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDao {
    private Connection connection;
    private Pool connectionPool;
    private PhoneDao phoneDao;

    public AccountDao() {
        connectionPool = ConnectionPool.getInstance();
        phoneDao = new PhoneDao();
    }

    public boolean createAccount(Account account) throws Exception {
        if (account.getName() == null) {
            throw new Exception("user name equals null");
        }
        String sql = "INSERT INTO account(name, surname, lastname, date, icq, addresshome, " +
                "addressjob, email, aboutme, username, password, role) " +
                "VALUES (?,?,?,NOW(),?,?,?,?,?,?,?,?);";
        System.out.println(sql);
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {//TODO
            preparedStatement.setString(1, account.getName());
            preparedStatement.setString(2, account.getSurname());
            preparedStatement.setString(3, account.getLastName());
            preparedStatement.setDate(4, (Date) account.getDate());
            preparedStatement.setInt(5, account.getIcq());
            preparedStatement.setString(6, account.getAddressHome());
            preparedStatement.setString(7, account.getAddressJob());
            preparedStatement.setString(8, account.getEmail());
            preparedStatement.setString(9, account.getAboutMe());
            preparedStatement.setString(10, account.getUsername());
            preparedStatement.setString(11, account.getPassword());
            preparedStatement.setInt(12, account.getRole());
            System.out.println(preparedStatement);
            int rows = preparedStatement.executeUpdate();
            System.out.println("Added " + rows + " rows");
        } catch (Exception ex) {
            System.out.println("Connection failed... createAccount");
            System.out.println(ex);
        } finally {
            try {
                connection.commit();
                connectionPool.returnConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public Account readAccount(String username, String password){
        System.out.println("readAccount - username = " + username + " ,password = " + password);
        Account account = new Account();
        connection = connectionPool.getConnection();
        String sql = "SELECT * FROM account WHERE username = ? AND password = ?";
        try (PreparedStatement query = connection.prepareStatement(sql)) {
            query.setString(1, username);
            query.setString(2, password);
            ResultSet resultSet = query.executeQuery();
            while (resultSet.next()) {
                account.setId(resultSet.getInt(1));
                account.setName(resultSet.getString(2));
                account.setSurname(resultSet.getString(3));
                account.setLastName(resultSet.getString(4));
                account.setDate(resultSet.getDate(5));
                account.setPhones(phoneDao.read(resultSet.getInt(1)));
                account.setIcq(resultSet.getInt(6));
                account.setAddressHome(resultSet.getString(7));
                account.setAddressJob(resultSet.getString(8));
                account.setEmail(resultSet.getString(9));
                account.setAboutMe(resultSet.getString(10));
                account.setUsername(resultSet.getString(11));
                account.setPassword(resultSet.getString(12));
                account.setRole(resultSet.getInt(13));
            }
        } catch (Exception ex) {
            System.out.println("Connection failed...at readAccount");
            System.out.println(ex);
        }
        connectionPool.returnConnection(connection);
        return account;
    }

    public Account readAccount(int id) {
        System.out.println("readAccount(int id)");
        Account account = new Account();
        connection = connectionPool.getConnection();
        try (PreparedStatement query = connection.prepareStatement("SELECT * FROM account WHERE idAccount = ?")) {
            query.setInt(1, id);
            ResultSet resultSet = query.executeQuery();
            while (resultSet.next()) {
                account.setId(resultSet.getInt(1));
                account.setName(resultSet.getString(2));
                account.setSurname(resultSet.getString(3));
                account.setLastName(resultSet.getString(4));
                account.setDate(resultSet.getDate(5));
                account.setPhones(phoneDao.read(resultSet.getInt(1)));
                account.setIcq(resultSet.getInt(6));
                account.setAddressHome(resultSet.getString(7));
                account.setAddressJob(resultSet.getString(8));
                account.setEmail(resultSet.getString(9));
                account.setAboutMe(resultSet.getString(10));
                account.setUsername(resultSet.getString(11));
                account.setPassword(resultSet.getString(12));
                account.setRole(resultSet.getInt(13));
            }
        } catch (Exception ex) {
            System.out.println("Connection failed...at readAccount");
            System.out.println(ex);
        }
        connectionPool.returnConnection(connection);
        return account;
    }

    public List<Account> readAccountsName(String name){
        List<Account> accounts = new ArrayList<Account>();
        String sql = "SELECT * FROM account WHERE name = ?";
        System.out.println(sql);
        connection = connectionPool.getConnection();
        try (PreparedStatement query = connection.prepareStatement(sql)) {
            System.out.println("readAccountsName");
            query.setString(1, name);
            ResultSet resultSet = query.executeQuery();
            while (resultSet.next()) {
                Account account = new Account();
                account.setId(resultSet.getInt(1));
                account.setName(resultSet.getString(2));
                account.setSurname(resultSet.getString(3));
                account.setLastName(resultSet.getString(4));
                account.setDate(resultSet.getDate(5));
                account.setPhones(phoneDao.read(resultSet.getInt(1)));
                account.setIcq(resultSet.getInt(6));
                account.setAddressHome(resultSet.getString(7));
                account.setAddressJob(resultSet.getString(8));
                account.setEmail(resultSet.getString(9));
                account.setAboutMe(resultSet.getString(10));
                account.setUsername(resultSet.getString(11));
                account.setPassword(resultSet.getString(12));
                account.setRole(resultSet.getInt(13));
                accounts.add(account);
            }
            System.out.println("get account from readAccounts");
        } catch (Exception ex) {
            System.out.println("Connection failed...readAccounts");
            System.out.println(ex);
        }
        connectionPool.returnConnection(connection);
        return accounts;
    }

    public List<Account> readAccounts() {
        List<Account> accounts = new ArrayList<Account>();
        connection = connectionPool.getConnection();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM account");
            while (resultSet.next()) {
                Account account = new Account();
                account.setId(resultSet.getInt(1));
                account.setName(resultSet.getString(2));
                account.setSurname(resultSet.getString(3));
                account.setLastName(resultSet.getString(4));
                account.setDate(resultSet.getDate(5));
                account.setPhones(phoneDao.read(resultSet.getInt(1)));
                account.setIcq(resultSet.getInt(6));
                account.setAddressHome(resultSet.getString(7));
                account.setAddressJob(resultSet.getString(8));
                account.setEmail(resultSet.getString(9));
                account.setAboutMe(resultSet.getString(10));
                account.setUsername(resultSet.getString(11));
                account.setPassword(resultSet.getString(12));
                account.setRole(resultSet.getInt(13));
                accounts.add(account);
            }
            System.out.println("get account from readAccounts");

        } catch (Exception ex) {
            System.out.println("Connection failed...readAccounts");
            System.out.println(ex);
        }
        connectionPool.returnConnection(connection);
        return accounts;
    }

    public boolean updateAccount(Account account) {
        connection = connectionPool.getConnection();
        String sql = "UPDATE account SET name ='?', surname ='?', lastname = '?', date = NOW(), icq = ?, " +
                "addresshome ='?', addressjob ='?'," +
                " email ='?', aboutme ='?', role = ? WHERE idAccount = ?";
        try (PreparedStatement query = connection.prepareStatement(sql)) {
            query.setString(1, account.getName());
            query.setString(2, account.getSurname());
            query.setString(3, account.getLastName());
            query.setInt(4, account.getIcq());
            query.setString(5, account.getAddressHome());
            query.setString(6, account.getAddressJob());
            query.setString(7, account.getEmail());
            query.setString(8, account.getAboutMe());
            query.setInt(9, account.getRole());
            query.setInt(10, account.getId());
            System.out.println("updateAccount - " + sql);
            int rows = query.executeUpdate();
            System.out.println("Updated rows = " + rows);
        } catch (Exception ex) {
            System.out.println("Connection failed...updateAccount");
            System.out.println(ex);
        } finally {
            try {
                connection.commit();
                connectionPool.returnConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public boolean deleteAccount(Account account) {
        connection = connectionPool.getConnection();
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM account WHERE idAccount = " + account.getId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                connection.commit();
                connectionPool.returnConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

}

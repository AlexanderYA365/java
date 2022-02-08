import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class AccountDao {
    private String url;
    private String username;
    private String password;
    private Connection connection;

    public AccountDao() {
        try (InputStream input = AccountDao.class.getClassLoader().getResourceAsStream("database.properties")) {
            Properties prop = new Properties();
            if (input == null) {
                throw new Exception("Sorry, unable to find database.properties");
            }
            System.out.println("try read properties ");
            prop.load(input);
            url = prop.getProperty("url");
            username = prop.getProperty("username");
            password = prop.getProperty("password");
            System.out.println("url = " + url);
            System.out.println("username = " + username);
            System.out.println("password = " + password);

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);//TODO connection pool
            connection.setAutoCommit(false);
        } catch (SQLException | ClassNotFoundException throwables) {
            System.out.println("Connection failed... AccountDAO");
            throwables.printStackTrace();
        }
    }

    public boolean createAccount(Account account) throws Exception {
        if (account.getName() == null) {
            throw new Exception("user name equals null");
        }
        String sql = "INSERT INTO account(name, surname, lastname, date, phone, icq, addresshome, " +
                "addressjob, email, aboutme, username, password) " +
                "VALUES (?,?,?,NOW(),?,?,?,?,?,?,?,?);";
        System.out.println(sql);

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {//TODO
            preparedStatement.setString(1, account.getName());
            preparedStatement.setString(2, account.getSurname());
            preparedStatement.setString(3, account.getLastName());
            //preparedStatement.setDate(5, (Date) account.getDate());
            preparedStatement.setString(4, account.getPhone());
            preparedStatement.setInt(5, account.getIcq());
            preparedStatement.setString(6, account.getAddressHome());
            preparedStatement.setString(7, account.getAddressJob());
            preparedStatement.setString(8, account.getEmail());
            preparedStatement.setString(9, account.getAboutMe());
            preparedStatement.setString(10, account.getUsername());
            preparedStatement.setString(11, account.getPassword());
            System.out.println(preparedStatement);
            int rows = preparedStatement.executeUpdate();
            System.out.println("Added " + rows + " rows");
        } catch (Exception ex) {
            System.out.println("Connection failed... createAccount");
            System.out.println(ex);
        } finally {
            try {
                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public Account readAccount(int id) {
        Account account = new Account();
        try (PreparedStatement query = connection.prepareStatement("SELECT * FROM account WHERE idAccount = ?")) {
            System.out.println("Test connection to server at readAccount");
            query.setInt(1, id);
            ResultSet resultSet = query.executeQuery();
            while (resultSet.next()) {
                account.setId(resultSet.getInt(1));
                account.setName(resultSet.getString(2));
                account.setSurname(resultSet.getString(3));
                account.setLastName(resultSet.getString(4));
                account.setDate(resultSet.getDate(5));
                account.setPhone(resultSet.getString(6));
                account.setIcq(resultSet.getInt(7));
                account.setAddressHome(resultSet.getString(8));
                account.setAddressJob(resultSet.getString(9));
                account.setEmail(resultSet.getString(10));
                account.setAboutMe(resultSet.getString(11));
                account.setUsername(resultSet.getString(12));
                account.setPassword(resultSet.getString(13));
            }
        } catch (Exception ex) {
            System.out.println("Connection failed...at readAccount");
            System.out.println(ex);
        }
        return account;
    }

    public List<Account> readAccountsName(String name){
        List<Account> accounts = new ArrayList<Account>();
        String sql = "SELECT * FROM account WHERE name = ?";
        System.out.println(sql);
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
                account.setPhone(resultSet.getString(6));
                account.setIcq(resultSet.getInt(7));
                account.setAddressHome(resultSet.getString(8));
                account.setAddressJob(resultSet.getString(9));
                account.setEmail(resultSet.getString(10));
                account.setAboutMe(resultSet.getString(11));
                account.setUsername(resultSet.getString(12));
                account.setPassword(resultSet.getString(13));
                accounts.add(account);
            }
            System.out.println("get account from readAccounts");

        } catch (Exception ex) {
            System.out.println("Connection failed...readAccounts");
            System.out.println(ex);
        }
        return accounts;
    }

    public List<Account> readAccounts() {
        List<Account> accounts = new ArrayList<Account>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM account");
            while (resultSet.next()) {
                Account account = new Account();
                account.setId(resultSet.getInt(1));
                account.setName(resultSet.getString(2));
                account.setSurname(resultSet.getString(3));
                account.setLastName(resultSet.getString(4));
                account.setDate(resultSet.getDate(5));
                account.setPhone(resultSet.getString(6));
                account.setIcq(resultSet.getInt(7));
                account.setAddressHome(resultSet.getString(8));
                account.setAddressJob(resultSet.getString(9));
                account.setEmail(resultSet.getString(10));
                account.setAboutMe(resultSet.getString(11));
                account.setUsername(resultSet.getString(12));
                account.setPassword(resultSet.getString(13));
                accounts.add(account);
            }
            System.out.println("get account from readAccounts");

        } catch (Exception ex) {
            System.out.println("Connection failed...readAccounts");
            System.out.println(ex);
        }
        return accounts;
    }

    public boolean updateAccount(Account account) {
        try (Statement statement = connection.createStatement()) {
            String sql = "UPDATE account" +
                    " SET name =" + "'" + account.getName() + "'," +
                    " surname =" + "'" + account.getSurname() + "'," +
                    " lastname =" + "'" + account.getLastName() + "'," +
                    " date =" + "" + "NOW()" + "," +
                    " phone =" + "'" + account.getPhone() + "'," +
                    " icq =" + "" + account.getIcq() + "," +
                    " addresshome =" + "'" + account.getAddressHome() + "'," +
                    " addressjob =" + "'" + account.getAddressJob() + "'," +
                    " email =" + "'" + account.getEmail() + "'," +
                    " aboutme =" + "'" + account.getAboutMe() + "'" +
                    " WHERE idAccount = " + account.getId() + ";";
            System.out.println("updateAccount - " + sql);
            int rows = statement.executeUpdate(sql);
            System.out.println("Updated rows = " + rows);
        } catch (Exception ex) {
            System.out.println("Connection failed...updateAccount");
            System.out.println(ex);
        } finally {
            try {
                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public boolean deleteAccount(Account account) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM account WHERE idAccount = " + account.getId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

}

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class AccountDAO {
    private String url;
    private String username;
    private String password;
    private Connection connection;

    public AccountDAO(){
        try (InputStream input = AccountDAO.class.getClassLoader().getResourceAsStream("database.properties")) {
            Properties prop = new Properties();
            if (input == null) {
                throw new Exception("Sorry, unable to find database.properties");
            }
            prop.load(input);
            url = prop.getProperty("url");
            username = prop.getProperty("username");
            password = prop.getProperty("password");
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
            connection.setAutoCommit(false);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public boolean createAccount(Account account) throws Exception {
        if(account.getName()  == null){
            throw new Exception("user name equals null");
        }
        try (Statement statement = connection.createStatement()){
            String text = "INSERT INTO account(name, surname, lastname, date, phone, icq, addresshome, " +
                    "addressjob, email, aboutme) " +
                    "VALUES (" + "'" + account.getName() + "','" + account.getSurname() +
                    "','" + account.getLastName() + "'," + "NOW()" +
                    ",'" + account.getPhone() + "','" + account.getIcq() + "','" + account.getAddressHome() +
                    "','" + account.getAddressJob() + "','" + account.getEmail() + "','" + account.getAboutMe() + "');";
            int rows = statement.executeUpdate(text);
            System.out.println("Added " + rows + " rows");
        } catch (Exception ex) {
            System.out.println("Connection failed...");
            System.out.println(ex);
        }finally {
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
        try (PreparedStatement query = connection.prepareStatement("SELECT * FROM account WHERE idAccount = ?")){
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
            }
        } catch (Exception ex) {
            System.out.println("Connection failed...");
            System.out.println(ex);
        }
        return account;
    }

    public List<Account> readAccounts() {
        List<Account> accountList = new ArrayList<Account>();
        try (Statement statement = connection.createStatement()){
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
                accountList.add(account);
            }
        } catch (Exception ex) {
            System.out.println("Connection failed...");
            System.out.println(ex);
        }
        return accountList;
    }

    public boolean updateAccount(Account account) {
        try (Statement statement = connection.createStatement()){
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
            int rows = statement.executeUpdate(sql);
            System.out.println("Updated rows = " + rows);
        } catch (Exception ex) {
            System.out.println("Connection failed...");
            System.out.println(ex);
        }finally {
            try {
                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public boolean deleteAccount(Account account) {
        try (Statement statement = connection.createStatement()){
            statement.executeUpdate("DELETE FROM account WHERE idAccount = " + account.getId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            try {
                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

}

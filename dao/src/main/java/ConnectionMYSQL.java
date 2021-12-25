import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.Scanner;

public class ConnectionMYSQL {
    private static final String user = "root";
    private static final String password = "root";
    private static final String url = "jdbc:mysql://localhost:3306/socialnetwork";
    private static Connection connection;

    public static void connectDataBase() {
        try {
            connection = DriverManager.getConnection(url, user, password);
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //connectDataBase();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try {
            connectionPool.create();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connection = connectionPool.getConnection();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Select options:");
            System.out.println("1 - create new account");
            System.out.println("2 - update account");
            System.out.println("3 - read account");
            System.out.println("4 - delete account");
            System.out.println("5 - create new group");
            System.out.println("6 - update group");
            System.out.println("7 - read group");
            System.out.println("8 - delete group");
            System.out.println("0 - exit");
            int option = scanner.nextInt();
            switch (option) {
                case 1: {
                    Account account = new Account("name", "surname", "lastName", new Date(), "phone", 2,
                            "addressHome", "addressJob", "email", "aboutMe");
                    AccountDAO accountDAO = new AccountDAO();
                    boolean result = false;
                    try {
                        result = accountDAO.createAccount(account);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println(result);
                    break;
                }
                case 2: {
                    Account account = new Account("name1", "surname1", "lastName1", new Date(), "phone1", 2,
                            "addressHome1", "addressJob1", "email1", "aboutMe1");
                    System.out.println("insert id to update");
                    account.setId(scanner.nextInt());
                    AccountDAO accountDAO = new AccountDAO();
                    boolean result = accountDAO.updateAccount(account);
                    System.out.println(result);
                    break;
                }
                case 3: {
                    AccountDAO accountDAO = new AccountDAO();
                    Account account = accountDAO.readAccount(5);
                    System.out.println(account);
                    break;
                }
                case 4: {
                    AccountDAO accountDAO = new AccountDAO();
                    System.out.println("Insert id to delete");
                    Account account = new Account();
                    account.setId(scanner.nextInt());
                    boolean result = accountDAO.deleteAccount(account);
                    System.out.println(result);
                    break;
                }
                case 5: {
                    GroupDAO groupDAO = new GroupDAO();
                    Group group = new Group("test", "c", 1, 3);
                    try {
                        boolean result = groupDAO.createGroup(group);
                        System.out.println(result);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case 6: {
                    GroupDAO groupDAO = new GroupDAO();
                    Group group = new Group("test1", "c1", 1, 3);
                    group.setIdGroup(3);
                    try {
                        boolean result = groupDAO.updateGroup(group);
                        System.out.println(result);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case 7: {
                    GroupDAO groupDAO = new GroupDAO();
                    try {
                        Group group = groupDAO.readGroup(5);
                        System.out.println(group);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case 8: {
                    GroupDAO groupDAO = new GroupDAO();
                    Group group = new Group("test1", "c1", 1, 3);
                    group.setIdGroup(3);
                    try {
                        boolean result = groupDAO.deleteGroup(group);
                        System.out.println(result);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
            if (option == 0) {
                break;
            }
        }
    }

}
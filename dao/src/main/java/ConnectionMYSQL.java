import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
        Connection connection;
        Pool connectionPool;
        connectionPool = ConnectionPool.getInstance();
        for (int i = 0; i < 10; i++) {
            connection = connectionPool.getConnection();
            connectionPool.returnConnection(connection);
        }
        //testConnection();
    }

    private static void testConnection() {
        /*Scanner scanner = new Scanner(System.in);
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
                            "addressHome", "addressJob", "email", "aboutMe", "1", "1");
                    AccountDao accountDAO = new AccountDao();
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
                            "addressHome1", "addressJob1", "email1", "aboutMe1", "333", "333");
                    System.out.println("insert id to update");
                    account.setId(scanner.nextInt());
                    AccountDao accountDAO = new AccountDao();
                    boolean result = accountDAO.updateAccount(account);
                    System.out.println(result);
                    break;
                }
                case 3: {
                    AccountDao accountDAO = new AccountDao();
                    Account account = accountDAO.readAccount(5);
                    System.out.println(account);
                    break;
                }
                case 4: {
                    AccountDao accountDAO = new AccountDao();
                    System.out.println("Insert id to delete");
                    Account account = new Account();
                    account.setId(scanner.nextInt());
                    boolean result = accountDAO.deleteAccount(account);
                    System.out.println(result);
                    break;
                }
                case 5: {
                    GroupDao groupDAO = new GroupDao();
                    Group group = new Group("test", "c", 1, 3);
                    try {
                        boolean result = groupDAO.create(group);
                        System.out.println(result);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case 6: {
                    GroupDao groupDAO = new GroupDao();
                    Group group = new Group("test1", "c1", 1, 3);
                    group.setIdGroup(3);
                    try {
                        boolean result = groupDAO.update(group);
                        System.out.println(result);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case 7: {
                    GroupDao groupDAO = new GroupDao();
                    try {
                        Group group = groupDAO.read(5);
                        System.out.println(group);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case 8: {
                    GroupDao groupDAO = new GroupDao();
                    Group group = new Group("test1", "c1", 1, 3);
                    group.setIdGroup(3);
                    try {
                        boolean result = groupDAO.delete(group);
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

         */
    }

}
import java.util.List;

public class AccountService {

    public boolean create(Account account) {
        System.out.println("Creat new Account from AccountService.create");
        AccountDao accountDAO = new AccountDao();
        try {
            return accountDAO.createAccount(account);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(Account account) {
        System.out.println("Account update idAccount - " + account);
        AccountDao accountDAO = new AccountDao();
        return accountDAO.updateAccount(account);
    }

    public boolean delete(Account account) {
        System.out.println("Account delete idAccount - " + account);
        AccountDao accountDAO = new AccountDao();
        return accountDAO.deleteAccount(account);
    }

    public Account read(int idAccount) {
        System.out.println("Account read idAccount - " + idAccount);
        AccountDao accountDao = new AccountDao();
        return accountDao.readAccount(idAccount);
    }

    public List<Account> getAllAccounts() {
        System.out.println("read all account dao");
        AccountDao accountDAO = new AccountDao();
        return accountDAO.readAccounts();
    }

    public List<Account> getAccountName(String name) {
        System.out.println("getAccountName, name - " + name);
        AccountDao dao = new AccountDao();
        return dao.readAccountsName(name);
    }

    public Account getAccount(String username, String password) {
        System.out.println("getAccount(String username, String password)");
        AccountDao dao = new AccountDao();
        return dao.readAccount(username, password);
    }
}
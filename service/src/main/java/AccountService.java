import java.sql.Connection;
import java.util.List;

public class AccountService {
    AccountDao accountDAO;
    PhoneDao phoneDao;
    private Pool connectionPool;

    public AccountService(){
        accountDAO = AccountDao.getInstance();
        phoneDao = PhoneDao.getInstance();
        connectionPool = ConnectionPool.getInstance();
    }

    public boolean create(Account account) {
        System.out.println("Creat new Account from AccountService.create");
        try {
            accountDAO.createAccount(account);
            int id = accountDAO.readIdAccount(account);
            for(Phone phone : account.getPhones()) {
                phone.setIdAccount(id);
                phoneDao.create(phone);
            }
            connectionPool.commit();
            return true;
        } catch (Exception e) {
            connectionPool.rollback();
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(Account account) {
        System.out.println("Account update idAccount - " + account.getId());
        try {
            accountDAO.updateAccount(account);
            int id = accountDAO.readIdAccount(account);
            for(Phone phone : account.getPhones()) {
                phone.setIdAccount(id);
                phoneDao.update(phone);
            }
            connectionPool.commit();
            return true;
        } catch (Exception e) {
            connectionPool.rollback();
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(Account account) {
        System.out.println("Account delete idAccount - " + account);
        try {
            accountDAO.deleteAccount(account);
            int id = accountDAO.readIdAccount(account);
            for(Phone phone : account.getPhones()) {
                phone.setIdAccount(id);
                phoneDao.delete(phone);
            }
            connectionPool.commit();
            return true;
        } catch (Exception e) {
            connectionPool.rollback();
            e.printStackTrace();
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
        return accountDAO.readAccount(username, password);
    }

    public void closeService() {
        connectionPool.returnConnection();
    }

}
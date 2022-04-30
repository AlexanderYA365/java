import java.util.List;

public class AccountService {
    AccountDao accountDAO;
    PhoneDao phoneDao;

    public AccountService() {
        phoneDao = new PhoneDao();
        accountDAO = new AccountDao();
    }

    public boolean create(Account account) {
        System.out.println("Creat new Account from AccountService.create");
        try {
            accountDAO.createAccount(account);
            int id = accountDAO.readIdAccount(account);
            for (Phone phone : account.getPhones()) {
                phone.setIdAccount(id);
                phoneDao.create(phone);
            }
            return true;
        } catch (Exception e) {
            System.out.println("AccountService.create Exception - " + e);
            return false;
        }
    }

    public boolean update(Account account) {
        System.out.println("Account update idAccount - " + account.getId());
        try {
            accountDAO.updateAccount(account);
            int id = accountDAO.readIdAccount(account);
            for (Phone phone : account.getPhones()) {
                phone.setIdAccount(id);
                phoneDao.update(phone);
            }
            return true;
        } catch (Exception e) {
            System.out.println("AccountService.create Exception - " + e);
            return false;
        }
    }

    public boolean delete(Account account) {
        System.out.println("Account delete idAccount - " + account);
        try {
            accountDAO.deleteAccount(account);
            int id = accountDAO.readIdAccount(account);
            for (Phone phone : account.getPhones()) {
                phone.setIdAccount(id);
                phoneDao.delete(phone);
            }
            return true;
        } catch (Exception e) {
            System.out.println("AccountService.create Exception - " + e);
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

}
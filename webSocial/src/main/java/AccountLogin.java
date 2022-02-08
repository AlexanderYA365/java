import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/")
public class AccountLogin extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        System.out.println("AccountLogin doGet");
        AccountDao accountDao = new AccountDao();
        List<Account> accounts = accountDao.readAccounts();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println("username - " + username + " password - " + password);
        Account registeredAccount = idAccount(accounts, username, password);
        HttpSession session = request.getSession();
        session.setAttribute("account", registeredAccount);
        if (registeredAccount.getId() != 0) {
            List<WallMassage> massages = printMassage(registeredAccount);
            System.out.println("wallMassage - " + massages);
            request.setAttribute("massages", massages);
            getServletContext().getRequestDispatcher("/main.jsp").forward(request, response);
        } else {
            System.out.println("AccountLogin.doGet -> else");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/index.jsp");
            requestDispatcher.forward(request, response);
        }
    }

    private List<WallMassage> printMassage(Account registeredAccount) {
        System.out.println("printMassage");
        AccountService service = new AccountService();
        List<WallMassage> massageList = service.readWallMassage(registeredAccount);
        return massageList;
    }

    private Account idAccount(List<Account> accounts, String username, String password) {
        Account findAccount = new Account();
        for (Account account : accounts) {
            if (account.getUsername().equals(username) && account.getPassword().equals(password)) {
                findAccount = account;
                break;
            }
        }
        return findAccount;
    }
}

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/login")
public class AccountPage extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException, ServletException {
        System.out.println("AccountPage doGet");
        AccountDao accountDao = new AccountDao();
        List<Account> accounts = accountDao.readAccounts();
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        System.out.println("username - " + username + " password - " + password);
        boolean isRegistered = haveUser(accounts, username, password);
        if (isRegistered){
            try {
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("jsp/main.jsp");
                requestDispatcher.forward(req, response);
            } catch (Exception e) {
                System.out.println("AccountPage.doGet Exception - " + e);
            }
        } else{
            System.out.println("AccountPage.doGet -> else");
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("jsp/index.jsp");
            requestDispatcher.forward(req, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse response) throws IOException, ServletException {
        System.out.println("AccountPage doPost");
        try{
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("jsp/main.jsp");
            requestDispatcher.forward(req, response);
        } catch (Exception e){
            System.out.println("AccountPage.doPost Exception - " + e);
        }
    }

    private boolean haveUser(List<Account> accounts, String username, String password){
        boolean flag = false;
        System.out.println("flag = " + flag);

        for (Account account: accounts){
            if(account.getUsername().equals(username) && account.getPassword().equals(password) && flag==false){
               flag = true;
            }
        }
        System.out.println("flag = " + flag);
        return flag;
    }
}

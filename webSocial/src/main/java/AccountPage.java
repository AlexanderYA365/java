import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class AccountPage extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException, ServletException {
        System.out.println("AccountPage doGet");
        try{
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("jsp/main.jsp");
            requestDispatcher.forward(req, response);
        } catch (Exception e){
            System.out.println("AccountPage.doPost Exception - " + e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse response) throws IOException, ServletException {
        System.out.println("AccountPage doPost");
//        try{
//            RequestDispatcher requestDispatcher = req.getRequestDispatcher("jsp/main.jsp");
//            requestDispatcher.forward(req, response);
//        } catch (Exception e){
//            System.out.println("AccountPage.doPost Exception - " + e);
//        }
    }

    private List<WallMassage> printMassage(Account registeredAccount){
        System.out.println("printMassage");
        AccountService service = new AccountService();
        List<WallMassage> massageList = service.readWallMassage(registeredAccount);
        return  massageList;
    }

    private Account idAccount(List<Account> accounts, String username, String password){
        Account findAccount = new Account();
        for (Account account: accounts){
            if(account.getUsername().equals(username) && account.getPassword().equals(password)){
                findAccount = account;
                break;
            }
        }
        return findAccount;
    }
}

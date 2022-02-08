import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class AccountPage extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("AccountPage doGet");
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        List<WallMassage> massages = printMassage(account);
        System.out.println("AccountPage - " + massages);
        request.setAttribute("massages", massages);
        try {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/main.jsp");
            requestDispatcher.forward(request, response);
        } catch (Exception e) {
            System.out.println("AccountPage.doPost Exception - " + e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse response) {
        System.out.println("AccountPage doPost");
        doGet(req, response);
    }

    private List<WallMassage> printMassage(Account account) {
        System.out.println("printMassage");
        AccountService service = new AccountService();
        List<WallMassage> massageList = service.readWallMassage(account);
        return massageList;
    }

}

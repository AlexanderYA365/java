import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AccountSettings extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("AccountSettings doGet");
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        request.setAttribute("account", account);
        try {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/myAccount.jsp");
            requestDispatcher.forward(request, response);
        } catch (Exception e) {
            System.out.println("AccountSettings.doPost Exception - " + e);
        }
    }

}

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class AccountGroup extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("AccountSettings doGet");
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        AccountService service = new AccountService();
        List<Group> groups = service.readAccountGroups(account);
        System.out.println(groups);
        session.setAttribute("groups", groups);
        request.setAttribute("groups", groups);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/AccountGroup.jsp");
        requestDispatcher.forward(request, response);
    }

}

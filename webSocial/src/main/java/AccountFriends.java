import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class AccountFriends extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("AccountFriends doGet");
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        AccountService service = new AccountService();
        List<Friend> friends = service.insertAccountFriends(account.getId());
        System.out.println(friends);
        request.setAttribute("friends", friends);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/AccountFriends.jsp");
        requestDispatcher.forward(request, response);
    }

}

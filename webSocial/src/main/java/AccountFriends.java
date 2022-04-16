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
        FriendService service = new FriendService();
        try {
            List<Friend> friends = service.readAccountFriends(account.getId());
            System.out.println("friends - " + friends);
            request.setAttribute("friends", friends);
        } catch (Exception e) {
            System.out.println(e);//send redirect
        } finally {
            service.closeService();
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/AccountFriends.jsp");
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("AccountFriends doPost");
        String[] idFriendsAccount = request.getParameterValues("idFriendsAccount");
        System.out.println("idFriendsAccount - " + idFriendsAccount);
        doGet(request, response);
    }

}
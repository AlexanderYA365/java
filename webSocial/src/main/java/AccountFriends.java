import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class AccountFriends extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("AccountFriends doGet");
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        AccountService service = new AccountService();
        List<Friend> friends = service.accountFriends(account);
        System.out.println(friends);
        List<String> friendsName = getAccountFriendsName(friends);
        System.out.println(friendsName);
        request.setAttribute("friendsname", friendsName);
        request.setAttribute("friends", friends);

        try {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/AccountFriends.jsp");
            requestDispatcher.forward(request, response);
        } catch (Exception e) {
            System.out.println("AccountSettings.doPost Exception - " + e);
        }
    }

    private List<String> getAccountFriendsName(List<Friend> friends) {
        List<String> friendsName = new ArrayList<String>();
        AccountService service = new AccountService();
        for (Friend friend : friends) {
            friendsName.add(service.read(friend).getName());
        }
        return friendsName;
    }

}

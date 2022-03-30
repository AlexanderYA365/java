import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ShowFriend extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("ShowFriend doGet");
        AccountService accountService = new AccountService();
        ApplicationService applicationService = new ApplicationService();
        System.out.println(req.getParameter("id"));
        FriendService friendService = new FriendService();
        Friend friend = friendService.read(Integer.parseInt(req.getParameter("id")));
        System.out.println(friend);
        Account friendAccount = accountService.read(friend.getIdFriendsAccount());
        System.out.println("ShowFriend friendAccount - " + friendAccount);
        Application application = applicationService.readAccount(friend);
        if (application != null) {
            int friendFlag = application.getStatus();
            req.setAttribute("friendFlag", friendFlag);
            req.setAttribute("friendAccount", friendAccount);
            MessageService messageService = new MessageService();
            List<Message> messages = messageService.readWallMassageAccount(friendAccount);
            System.out.println("messages - " + messages);
            req.setAttribute("messages", messages);
        }
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("jsp/ShowFriend.jsp");
        requestDispatcher.forward(req, response);
    }

}

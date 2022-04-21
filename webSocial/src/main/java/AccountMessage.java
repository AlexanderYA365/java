import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class AccountMessage extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("AccountMassage doGet");
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        request.setAttribute("account", account);
        MessageService service = new MessageService();
        try {
            List<Message> messageList = service.readMessage(account);
            if (messageList.size() != 0) {
                int haveMessage = 0;
                request.setAttribute("haveMessage", haveMessage);
                request.setAttribute("messageList", messageList);
            } else {
                int haveMessage = 1;
                request.setAttribute("haveMessage", haveMessage);
            }
        } catch (Exception e) {
            System.out.println(e);//send redirect
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/AccountMessage.jsp");
        requestDispatcher.forward(request, response);
    }

}

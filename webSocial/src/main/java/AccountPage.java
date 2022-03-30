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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("AccountPage doGet");
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        List<Message> messages = printMassage(account);
        System.out.println("AccountPage - " + messages);
        request.setAttribute("messages", messages);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/main.jsp");
        requestDispatcher.forward(request, response);
    }

    private List<Message> printMassage(Account account) {
        System.out.println("printMassage");
        MessageService service = new MessageService();
        List<Message> messageList = service.readWallMassageAccount(account);
        return messageList;
    }

}

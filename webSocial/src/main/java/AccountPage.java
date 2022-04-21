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
        List<Message> messages = printMessage(account);
        System.out.println("AccountPage - " + messages);
        request.setAttribute("messages", messages);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/main.jsp");
        requestDispatcher.forward(request, response);
    }

    private List<Message> printMessage(Account account) {
        System.out.println("printMassage");
        MessageService service = new MessageService();
        List<Message> messageList = service.readWallMassageAccount(account);
        return messageList;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("AccountPage doPost");
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        String newMessage = request.getParameter("NewWallMessage");
        System.out.println("NewWallMessage - " + newMessage);
        MessageService service = new MessageService();
        try {
            Message message = new Message();
            message.setIdReceiving(account.getId());
            message.setIdSender(account.getId());
            message.setMessage(newMessage);
            message.setMessageType(0);
            System.out.println("message - " + message);
            service.createMassage(message);
        } catch (Exception e) {
            System.out.println(e);//send redirect
        }
        doGet(request, response);
    }

}

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("AccountWriteMessage.jsp")
public class AccountWriteMessage extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("AccountWriteMessage doGet");
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        String selectUser = request.getParameter("selectUser");
        System.out.println("selectUser - " + selectUser);
        int idSender = Integer.parseInt(selectUser);
        request.setAttribute("account", account);
        MessageService service = new MessageService();
        List<Message> personalMail = service.accountMessage(idSender, account.getId());
        System.out.println("AccountWriteMessage.personalMail" + personalMail);
        request.setAttribute("personalMail", personalMail);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/AccountWriteMessage.jsp");
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("AccountWriteMessage doPost");
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        String newMessage = request.getParameter("NewMessage");
        System.out.println("NewMessage - " + newMessage);
        String selectUser = request.getParameter("selectUser");
        System.out.println("selectUser - " + selectUser);
        int IdReceiving = Integer.parseInt(selectUser);
        System.out.println("IdReceiving - " + IdReceiving);
        MessageService service = new MessageService();
        try {
            Message message = new Message();
            message.setIdReceiving(IdReceiving);
            message.setIdSender(account.getId());
            message.setMessage(newMessage);
            message.setMessageType(1);
            service.createMassage(message);
        } catch (Exception e) {
            System.out.println(e);//send redirect
        } finally {
            service.closeService();
        }
        doGet(request, response);
    }

}

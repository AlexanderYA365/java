import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("AccountWriteMassage.jsp")
public class AccountWriteMassage extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("AccountWriteMassage doGet");
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        String selectUser = request.getParameter("selectUser");
        System.out.println("selectUser - " + selectUser);
        int idSender = Integer.parseInt(selectUser);
        request.setAttribute("account", account);
        MessageService service = new MessageService();
        List<Massage> massageList = service.accountMassage(idSender, account.getId());
        System.out.println("AccountWriteMassage.massageList" + massageList);
        request.setAttribute("massageList", massageList);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/AccountWriteMassage.jsp");
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("AccountWriteMassage doPost");
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        String message = request.getParameter("NewMessage");
        System.out.println("NewMessage - " + message);
        String selectUser = request.getParameter("selectUser");
        System.out.println("selectUser - " + selectUser);
        int IdReceiving = Integer.parseInt(selectUser);
        System.out.println("IdReceiving - " + IdReceiving);
        MessageService service = new MessageService();
        Massage massage = new Massage();
        massage.setIdReceiving(IdReceiving);
        massage.setIdSender(account.getId());
        massage.setMassage(message);
        service.createMassage(massage);
        doGet(request, response);
    }

}

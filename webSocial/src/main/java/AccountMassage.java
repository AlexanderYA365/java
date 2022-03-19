import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class AccountMassage extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("AccountMassage doGet");
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        request.setAttribute("account", account);
        MessageService service = new MessageService();
        List<Massage> massageList = service.readMassage(account);
        System.out.println("AccountMassage.massageList" + massageList);
        request.setAttribute("massageList", massageList);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/AccountMassage.jsp");
        requestDispatcher.forward(request, response);
    }

}

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class RegisterAccount extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Register Account doGet");
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("jsp/RegistrationAccount.jsp");
        requestDispatcher.forward(req, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("RegisterAccount doPost");
        Account account = new Account();
        account.setUsername(request.getParameter("username"));
        account.setPassword(request.getParameter("password"));
        account.setName(request.getParameter("name"));
        account.setSurname(request.getParameter("surname"));
        account.setLastName(request.getParameter("lastName"));//TODO phone
        account.setIcq(Integer.parseInt(request.getParameter("icq")));
        account.setAddressHome(request.getParameter("addressHome"));
        account.setAddressJob(request.getParameter("addressJob"));
        account.setEmail(request.getParameter("email"));
        account.setAboutMe(request.getParameter("aboutMe"));
        System.out.println(account);
        AccountService service = new AccountService();
        try {
            service.create(account);
        } catch (Exception e) {
            System.out.println(e);//send redirect
        }
        HttpSession session = request.getSession();
        session.setAttribute("account", account);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/main.jsp");
        requestDispatcher.forward(request, response);
    }

}
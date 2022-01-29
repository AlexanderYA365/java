import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class RegisterAccount extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException, ServletException {
        System.out.println("Register Account doGet");
        try{
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("jsp/Account.jsp");
            requestDispatcher.forward(req, response);
        } catch (Exception e){
            System.out.println("RegisterAccount.doGet Exception - " + e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Register Account doPost");
        try {
            Account account = new Account();
            account.setUsername(request.getParameter("username"));
            account.setPassword(request.getParameter("password"));
            account.setName(request.getParameter("name"));
            account.setSurname(request.getParameter("surname"));
            account.setLastName(request.getParameter("lastName"));
            account.setPhone(request.getParameter("phone"));
            account.setIcq(Integer.parseInt(request.getParameter("icq")));
            account.setAddressHome(request.getParameter("addressHome"));
            account.setAddressJob(request.getParameter("addressJob"));
            account.setEmail(request.getParameter("email"));
            account.setAboutMe(request.getParameter("aboutMe"));
            System.out.println(account);
            AccountService service = new AccountService();
            service.create(account);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/main.jsp");
            requestDispatcher.forward(request, response);
        }catch (Exception e){
            System.out.println("RegisterAccount.doPost Exception - " + e);
        }
    }

}
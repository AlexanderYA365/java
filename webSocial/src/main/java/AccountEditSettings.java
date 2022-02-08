import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AccountEditSettings extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        System.out.println("AccountEditSettings doGet");
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/EditAccountSettings.jsp");
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("AccountEditSettings doPost");
        try {
            Account account = new Account();
            HttpSession session = request.getSession();
            Account editAccount = (Account) session.getAttribute("account");
            System.out.println("editAccount - " + editAccount);
            account.setUsername(editAccount.getUsername());
            account.setPassword(editAccount.getPassword());
            account.setId(editAccount.getId());
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
            service.update(account);
            session.setAttribute("account", account);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/myAccount.jsp");
            requestDispatcher.forward(request, response);
        } catch (Exception e) {
            System.out.println("AccountEditSettings.doPost Exception - " + e);
        }
    }

}

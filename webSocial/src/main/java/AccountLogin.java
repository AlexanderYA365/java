import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/")
public class AccountLogin extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        System.out.println("AccountLogin doGet");
        String username = null;
        String password = null;
        boolean useCookies = Boolean.parseBoolean(request.getParameter("cookie"));
        if (useCookies) {
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("username")) {
                    username = cookie.getValue();
                }
                if (cookie.getName().equals("password")) {
                    password = cookie.getValue();
                }
            }
        } else {
            username = request.getParameter("username");
            password = request.getParameter("password");
            System.out.println("username - " + username + " password - " + password);
        }
        AccountService service = new AccountService();
        Account registeredAccount = service.getAccount(username, password);
        if (registeredAccount.getId() != 0) {
            HttpSession session = request.getSession();
            session.setAttribute("account", registeredAccount);
            session.setAttribute("username", registeredAccount.getUsername());
            Cookie cookieUsername = new Cookie("username", username);
            Cookie cookiePassword = new Cookie("password", password);
            response.addCookie(cookieUsername);
            response.addCookie(cookiePassword);
            getServletContext().getRequestDispatcher("/main.jsp").forward(request, response);
        } else {
            System.out.println("AccountLogin.doGet -> else");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/index.jsp");
            int errorLogin = 1;
            request.setAttribute("errorLogin", errorLogin);
            requestDispatcher.forward(request, response);
        }
    }

}
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;


public class AccountLogout extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        System.out.println("AccountLogout doGet");
        HttpSession session = request.getSession();
        session.invalidate();
        Cookie cookieUsername = new Cookie("username", null);
        cookieUsername.setMaxAge(0);
        cookieUsername.setPath("/");
        Cookie cookiePassword = new Cookie("password", null);
        cookiePassword.setMaxAge(0);
        cookiePassword.setPath("/");
        Cookie cookieId = new Cookie("id", null);
        cookieId.setMaxAge(0);
        cookieId.setPath("/");
        response.addCookie(cookieUsername);
        response.addCookie(cookiePassword);
        response.addCookie(cookieId);
        RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/index.jsp");
        dispatcher.forward(request, response);
    }

}

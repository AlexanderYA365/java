import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ShowGroup extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("ShowGroup doGet");
        GroupService service = new GroupService();
        Group group = service.readGroupID(Integer.parseInt(req.getParameter("id")));
        System.out.println(group);
        req.setAttribute("group", group);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("jsp/showGroup.jsp");
        requestDispatcher.forward(req, response);
    }

}

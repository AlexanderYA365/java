import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class AccountFindGroup extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("AccountFindGroup doGet");
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/AccountFindGroup.jsp");
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] groupId = request.getParameterValues("groupId");
        System.out.println("groupId - " + groupId);
        System.out.println("AccountFindGroup doPost");
        String groupName = request.getParameter("GroupName");
        AccountService service = new AccountService();
        if (groupId == null) {
            try {
                List<Group> groups = service.getGroupName(groupName);
                System.out.println(groups);
                request.setAttribute("findGroups", groups);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (groupId != null) {
            HttpSession session = request.getSession();
            Account account = (Account) session.getAttribute("account");
            Group addGroup = service.readGroupID(Integer.parseInt(groupId[0]));//TODO
            System.out.println("Account - " + account);
            System.out.println("addGroup - " + addGroup);
            service.insertAccountGroup(addGroup, account.getId());
        }
        doGet(request, response);
    }

}

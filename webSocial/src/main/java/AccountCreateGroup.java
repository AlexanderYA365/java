import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class AccountCreateGroup extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        System.out.println("AccountCreateGroup doGet");
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/CreateGroup.jsp");
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("AccountCreateGroup doPost");
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        AccountService service = new AccountService();
        List<Group> groups = service.readGroups();
        if (createGroup(groups, request.getParameter("name"))) {
            Group group = new Group();
            group.setIdAdministrator(account.getId());
            group.setIdAccount(account.getId());
            group.setGroupName(request.getParameter("name"));
            group.setLogo(request.getParameter("logo"));
            System.out.println("AccountCreateGroup doPost - " + group);
            service.createAccountGroups(group);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/AccountGroup.jsp");
            requestDispatcher.forward(request, response);
        }
    }

    private boolean createGroup(List<Group> groups, String groupName) {
        boolean flag = false;
        System.out.println("createGroup, group name - " + groupName);
        for (Group group : groups) {
            if (!group.getGroupName().equals(groupName)) {
                flag = true;
            }
        }
        System.out.println("createGroup flag - " + flag);
        return flag;
    }

}
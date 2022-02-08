import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class AddFriendAccount extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("AddFriendAccount doGet");
        try {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/AddFriendAccount.jsp");
            requestDispatcher.forward(request, response);
        } catch (Exception e) {
            System.out.println("AccountPage.doPost Exception - " + e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String[] accountId = request.getParameterValues("accountId");
        System.out.println("AddFriendAccount doPost");
        String name = request.getParameter("name");
        AccountService service = new AccountService();
        if (accountId == null) {
            try {
                List<Account> accounts = service.getAccountName(name);
                System.out.println(accounts);
                request.setAttribute("accounts", accounts);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (accountId != null) {
            HttpSession session = request.getSession();
            Account account = (Account) session.getAttribute("account");
            Account accountFriend = service.read(Integer.parseInt(accountId[0]));//TODO
            System.out.println("account - " + account);
            System.out.println("friend - " + accountFriend);
            service.addFriend(account, accountFriend);
        }
        doGet(request, response);
    }


}

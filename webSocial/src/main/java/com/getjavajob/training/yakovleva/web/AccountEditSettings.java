package com.getjavajob.training.yakovleva.web;

import com.getjavajob.training.yakovleva.dao.Account;
import com.getjavajob.training.yakovleva.service.AccountService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/AccountEditSettings")
public class AccountEditSettings extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        System.out.println("AccountEditSettings doGet");
        request.getRequestDispatcher("jsp/EditAccountSettings.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("AccountEditSettings doPost");
        Account account = new Account();
        HttpSession session = request.getSession();
        Account editAccount = (Account) session.getAttribute("account");
        System.out.println("editAccount - " + editAccount);
        account.setUsername(editAccount.getUsername());
        account.setPassword(editAccount.getPassword());
        account.setId(editAccount.getId());
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
            service.update(account);
        } catch (Exception e) {
            System.out.println(e);//send redirect
        }
        session.setAttribute("account", account);
        request.getRequestDispatcher("jsp/myAccount.jsp").forward(request, response);
    }

}

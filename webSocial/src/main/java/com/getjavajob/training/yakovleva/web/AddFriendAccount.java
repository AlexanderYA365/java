package com.getjavajob.training.yakovleva.web;

import com.getjavajob.training.yakovleva.dao.Account;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/AddFriendAccount")
public class AddFriendAccount extends ApplicationContextServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("AddFriendAccount doGet");
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/AddFriendAccount.jsp");
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] accountId = request.getParameterValues("accountId");
        System.out.println("AddFriendAccount doPost");
        String name = request.getParameter("name");
        try {
            if (accountId == null) {
                try {
                    List<Account> accounts = accountService.getAccountName(name);
                    System.out.println(accounts);
                    request.setAttribute("accounts", accounts);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (accountId != null) {
                HttpSession session = request.getSession();
                Account account = (Account) session.getAttribute("account");
                Account accountFriend = accountService.read(Integer.parseInt(accountId[0]));//TODO
                System.out.println("account - " + account);
                System.out.println("friend - " + accountFriend);
                friendService.addFriend(account, accountFriend);
            }
        } catch (Exception e) {
            System.out.println(e);//send redirect
        }
        doGet(request, response);
    }

}
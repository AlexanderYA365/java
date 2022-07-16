package com.getjavajob.training.yakovleva.web;

import com.getjavajob.training.yakovleva.dao.Account;
import com.getjavajob.training.yakovleva.dao.Relations;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

//@WebServlet("/add-friend-account")
public class AddFriendAccount extends ApplicationContextServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("AddFriendAccount doGet");
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/friend/add-friend-account.jsp");
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("add-friend-account doPost");
        String accountId = request.getParameter("friendId");
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
                Account accountFriend = accountService.get(Integer.parseInt(accountId));
                System.out.println("account - " + account);
                System.out.println("friend - " + accountFriend);
                Relations relations = new Relations();
                relations.setAccountId(account.getId());
                relations.setFriendId(accountFriend.getId());
                relationsService.create(relations);
            }
        } catch (Exception e) {
            System.out.println(e);//send redirect
        }
        doGet(request, response);
    }

}
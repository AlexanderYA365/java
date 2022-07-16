package com.getjavajob.training.yakovleva.web;

import com.getjavajob.training.yakovleva.dao.Account;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

//@WebServlet("/account-friends")
public class AccountFriends extends ApplicationContextServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("AccountFriends doGet");
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        try {
            List<Account> friends = accountService.getFriendsAccount(account.getId());
            System.out.println("friends - " + friends);
            request.setAttribute("friends", friends);
        } catch (Exception e) {
            System.out.println(e);//send redirect
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/friend/account-friends.jsp");
        requestDispatcher.forward(request, response);
    }

}
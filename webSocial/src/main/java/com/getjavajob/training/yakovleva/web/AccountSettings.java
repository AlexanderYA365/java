package com.getjavajob.training.yakovleva.web;

import com.getjavajob.training.yakovleva.common.Account;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//@WebServlet("/my-account")
public class AccountSettings extends ApplicationContextServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("AccountSettings doGet");
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        Account readAccount = accountService.get(account.getId());
        request.setAttribute("account", readAccount);
        System.out.println("account - " + readAccount);
        System.out.println("phones - " + readAccount.getPhones());
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/account/my-account.jsp");
        requestDispatcher.forward(request, response);
    }

}

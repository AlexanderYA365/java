package com.getjavajob.training.yakovleva.web;

import com.getjavajob.training.yakovleva.dao.Account;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/myAccount")
public class AccountSettings extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("AccountSettings doGet");
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        request.setAttribute("account", account);
        System.out.println("account - " + account);
        System.out.println("phones - " + account.getPhones());
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/myAccount.jsp");
        requestDispatcher.forward(request, response);
    }

}

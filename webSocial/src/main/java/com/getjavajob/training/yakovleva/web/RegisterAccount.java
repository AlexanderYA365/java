package com.getjavajob.training.yakovleva.web;

import com.getjavajob.training.yakovleva.dao.Account;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/registration-account")
public class RegisterAccount extends ApplicationContextServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Register Account doGet");
        request.getRequestDispatcher("jsp/account/registration-account.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("RegisterAccount doPost");
        Account account = new Account();
        account.setUsername(request.getParameter("username"));
        account.setPassword(request.getParameter("password"));
        account.setName(request.getParameter("name"));
        account.setSurname(request.getParameter("surname"));
        account.setLastName(request.getParameter("lastName"));
        account.setLastName(request.getParameter("phone"));
        account.setIcq(Integer.parseInt(request.getParameter("icq")));
        account.setAddressHome(request.getParameter("addressHome"));
        account.setAddressJob(request.getParameter("addressJob"));
        account.setEmail(request.getParameter("email"));
        account.setAboutMe(request.getParameter("aboutMe"));
        System.out.println(account);
        try {
            accountService.create(account);
        } catch (Exception e) {
            System.out.println(e);//send redirect
        }
        HttpSession session = request.getSession();
        session.setAttribute("account", account);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/main.jsp");
        requestDispatcher.forward(request, response);
    }

}
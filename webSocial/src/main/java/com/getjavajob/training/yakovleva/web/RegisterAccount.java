package com.getjavajob.training.yakovleva.web;

import com.getjavajob.training.yakovleva.common.Account;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//@WebServlet("/registration-account")
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
        account.setDate(getDateFromForm(request));
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

    private Date getDateFromForm(HttpServletRequest request) {
        System.out.println("getDateFromForm");
        Date date = new Date();
        try {
            String dateFromForm = request.getParameter("date");
            System.out.println("dateFromForm - " + dateFromForm);
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            date = format.parse(dateFromForm);
            System.out.println("date - " + date);
        } catch (ParseException e) {
            System.out.println("getDateFromForm exception - " + e);
        }
        return date;
    }

}
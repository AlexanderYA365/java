package com.getjavajob.training.yakovleva.web;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/account-logout")
public class AccountLogout extends ApplicationContextServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        System.out.println("AccountLogout doGet");
        HttpSession session = request.getSession();
        session.invalidate();
        Cookie cookieUsername = new Cookie("username", null);
        cookieUsername.setMaxAge(0);
        cookieUsername.setPath("/");
        Cookie cookiePassword = new Cookie("password", null);
        cookiePassword.setMaxAge(0);
        cookiePassword.setPath("/");
        Cookie cookieId = new Cookie("id", null);
        cookieId.setMaxAge(0);
        cookieId.setPath("/");
        response.addCookie(cookieUsername);
        response.addCookie(cookiePassword);
        response.addCookie(cookieId);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.jsp");
        requestDispatcher.forward(request, response);
    }

}

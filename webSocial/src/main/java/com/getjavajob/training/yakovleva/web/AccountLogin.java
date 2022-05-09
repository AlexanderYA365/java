package com.getjavajob.training.yakovleva.web;

import com.getjavajob.training.yakovleva.dao.Account;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "login", urlPatterns = {"/login"})
public class AccountLogin extends ApplicationContextServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("AccountLogin doPost");
        String username = null;
        String password = null;
        boolean useCookies = Boolean.parseBoolean(request.getParameter("cookie"));
        if (useCookies) {
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("username")) {
                    username = cookie.getValue();
                }
                if (cookie.getName().equals("password")) {
                    password = cookie.getValue();
                }
            }
            System.out.println("Cookie = username - " + username + " password - " + password);
        } else {
            username = request.getParameter("username");
            password = request.getParameter("password");
            System.out.println("username - " + username + " password - " + password);
        }
        if (username == null && password == null) {
            response.sendRedirect("index.jsp");
        } else {
            try {
                Account registeredAccount = accountService.getAccount(username, password);
                System.out.println("registeredAccount - " + registeredAccount);
                if (registeredAccount.getId() != 0) {
                    HttpSession session = request.getSession();
                    session.setAttribute("account", registeredAccount);
                    session.setAttribute("username", registeredAccount.getUsername());
                    String rememberMeActive = request.getParameter("rememberMe");
                    if ("active".equals(rememberMeActive)) {
                        Cookie cookieUsername = new Cookie("username", username);
                        Cookie cookiePassword = new Cookie("password", password);
                        Cookie cookieId = new Cookie("id", String.valueOf(registeredAccount.getId()));
                        response.addCookie(cookieUsername);
                        response.addCookie(cookiePassword);
                        response.addCookie(cookieId);
                    }
                    request.getRequestDispatcher("main").forward(request, response);
                } else {
                    System.out.println("AccountLogin.doGet -> else");
                    int errorLogin = 1;
                    request.setAttribute("errorLogin", errorLogin);
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                }
            } catch (Exception e) {
                System.out.println(e);//send redirect
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        System.out.println("AccountLogin doGet");
        doPost(request, response);
    }

}
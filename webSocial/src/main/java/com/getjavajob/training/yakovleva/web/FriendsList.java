package com.getjavajob.training.yakovleva.web;

import com.getjavajob.training.yakovleva.dao.Account;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class FriendsList extends ApplicationContextServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        System.out.println("FriendsList");
        List<Account> accounts = accountService.getAllAccounts();
        PrintWriter out = resp.getWriter();
        out.print("<html>");
        out.print("<title>" + "Users account" + "</title>");
        out.print("<table border=5 CELLSPACING=2 >");
        out.print("<caption>" + "Users account INFO" + "</caption>");
        out.print("<tr>");
        out.print("<th>" + "name" + "</th>");
        out.print("<th>" + "Surname" + "</th>");
        out.print("<th>" + "LastName" + "</th>");
        out.print("<th>" + "email" + "</th>");
        out.print("<th>" + "home address" + "</th>");
        out.print("</tr>");
        for (int i = 0; i < accounts.size(); i++) {
            out.print("<tr>");
            out.print("<th> " + accounts.get(i).getName() + "</th>");
            out.print("<th> " + accounts.get(i).getSurname() + "</th>");
            out.print("<th> " + accounts.get(i).getLastName() + "</th>");
            out.print("<th> " + accounts.get(i).getEmail() + "</th>");
            out.print("<th> " + accounts.get(i).getAddressHome() + "</th>");
            out.print("</tr>");
        }
        out.print("</table>");
        out.print("</html>");
    }
}
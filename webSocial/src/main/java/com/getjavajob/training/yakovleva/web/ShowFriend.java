package com.getjavajob.training.yakovleva.web;

import com.getjavajob.training.yakovleva.dao.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/show-friend")
public class ShowFriend extends ApplicationContextServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("ShowFriend doGet");
        HttpSession session = req.getSession();
        Account account = (Account) session.getAttribute("account");
        try {
            int friendId = Integer.parseInt(req.getParameter("id"));
            System.out.println("friendId - " + friendId);
            Account friendAccount = accountService.get(friendId);
            System.out.println("ShowFriend friendAccount - " + friendAccount);
            Relations relations = new Relations(account.getId(), friendId);
            Application application = applicationService.getAccount(relations);
            System.out.println("application - " + application);
            int friendFlag = application.getId() != 0 ? application.getStatus() : 1;
            System.out.println("friendFlag - " + friendFlag);
            List<Message> messages = messageService.getWallMassageAccount(friendAccount);
            req.setAttribute("friendFlag", friendFlag);
            req.setAttribute("friendAccount", friendAccount);
            req.setAttribute("messages", messages);
        } catch (Exception e) {
            System.out.println(e);//send redirect
        }
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("jsp/friend/show-friend.jsp");
        requestDispatcher.forward(req, response);
    }

}
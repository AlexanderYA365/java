package com.getjavajob.training.yakovleva.web;

import com.getjavajob.training.yakovleva.dao.Account;
import com.getjavajob.training.yakovleva.dao.Application;
import com.getjavajob.training.yakovleva.dao.Friend;
import com.getjavajob.training.yakovleva.dao.Message;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/ShowFriend")
public class ShowFriend extends ApplicationContextServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("ShowFriend doGet");
        System.out.println(req.getParameter("id"));
        try {
            Friend friend = friendService.read(Integer.parseInt(req.getParameter("id")));
            System.out.println(friend);
            Account friendAccount = accountService.read(friend.getIdFriendsAccount());
            System.out.println("ShowFriend friendAccount - " + friendAccount);
            Application application = applicationService.readAccount(friend);
            if (application != null) {
                int friendFlag = application.getStatus();
                req.setAttribute("friendFlag", friendFlag);
                req.setAttribute("friendAccount", friendAccount);
                List<Message> messages = messageService.readWallMassageAccount(friendAccount);
                System.out.println("messages - " + messages);
                req.setAttribute("messages", messages);
            }
        } catch (Exception e) {
            System.out.println(e);//send redirect
        }
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("jsp/ShowFriend.jsp");
        requestDispatcher.forward(req, response);
    }

}
package com.getjavajob.training.yakovleva.web;

import com.getjavajob.training.yakovleva.common.Account;
import com.getjavajob.training.yakovleva.common.Message;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

//@WebServlet("/account-message")
public class AccountMessage extends ApplicationContextServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("AccountMessage doGet");
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        request.setAttribute("account", account);
        try {
            List<Message> uniqueMessages = messageService.getUniqueMessages(account);
            List<Message> messageList = messageService.getMessages(account);
            if (messageList.size() != 0) {
                int haveMessage = 0;
                request.setAttribute("haveMessage", haveMessage);
                request.setAttribute("uniqueMessages", uniqueMessages);
                request.setAttribute("messageList", messageList);
            } else {
                int haveMessage = 1;
                request.setAttribute("haveMessage", haveMessage);
            }
        } catch (Exception e) {
            System.out.println(e);//send redirect
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/account/account-message.jsp");
        requestDispatcher.forward(request, response);
    }

}

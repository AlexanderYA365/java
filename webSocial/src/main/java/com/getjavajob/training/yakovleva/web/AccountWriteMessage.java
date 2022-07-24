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

//@WebServlet("/account-write-message")
public class AccountWriteMessage extends ApplicationContextServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("AccountWriteMessage doGet");
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        String selectUser = request.getParameter("selectUser");
        System.out.println("selectUser - " + selectUser);
        int idSender = Integer.parseInt(selectUser);
        request.setAttribute("account", account);
        List<Message> personalMail = messageService.getAccountMessages(idSender, account.getId());
        System.out.println("AccountWriteMessage.personalMail" + personalMail);
        request.setAttribute("personalMail", personalMail);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/account/account-write-message.jsp");
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("AccountWriteMessage doPost");
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        String newMessage = request.getParameter("NewMessage");
        System.out.println("NewMessage - " + newMessage);
        String selectUser = request.getParameter("selectUser");
        System.out.println("selectUser - " + selectUser);
        int IdReceiving = Integer.parseInt(selectUser);
        System.out.println("IdReceiving - " + IdReceiving);
        try {
            Message message = new Message();
            message.setReceiverId(IdReceiving);
            message.setSenderId(account.getId());
            message.setMessage(newMessage);
            message.setMessageType(1);
            messageService.createMassage(message);
        } catch (Exception e) {
            System.out.println(e);//send redirect
        }
        doGet(request, response);
    }

}

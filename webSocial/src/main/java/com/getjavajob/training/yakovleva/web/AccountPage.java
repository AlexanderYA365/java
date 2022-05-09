package com.getjavajob.training.yakovleva.web;

import com.getjavajob.training.yakovleva.dao.Account;
import com.getjavajob.training.yakovleva.dao.Message;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/main")
public class AccountPage extends ApplicationContextServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("AccountPage doGet");
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        List<Message> messages = printMessage(account);
        System.out.println("AccountPage - " + messages);
        request.setAttribute("messages", messages);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/main.jsp");
        requestDispatcher.forward(request, response);
    }

    private List<Message> printMessage(Account account) {
        System.out.println("printMassage");
        List<Message> messageList = messageService.readWallMassageAccount(account);
        return messageList;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("AccountPage doPost");
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        String newMessage = request.getParameter("NewWallMessage");
        String replyAccount = request.getParameter("replyAccount");
        String deleteText = request.getParameter("deleteText");

        System.out.println(request.getParameter("messageId"));
        System.out.println("NewWallMessage - " + newMessage);
        System.out.println("replyAccount - " + replyAccount);
        System.out.println("deleteText - " + deleteText);
        try {
            Message message = new Message();
            message.setIdReceiving(account.getId());
            message.setIdSender(account.getId());
            message.setMessage(newMessage);
            message.setMessageType(0);
            System.out.println("message - " + message);
            messageService.createMassage(message);
        } catch (Exception e) {
            System.out.println(e);//send redirect
        }
        doGet(request, response);
    }

}

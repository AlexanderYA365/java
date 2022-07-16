package com.getjavajob.training.yakovleva.web.controllers;

import com.getjavajob.training.yakovleva.dao.Account;
import com.getjavajob.training.yakovleva.dao.Message;
import com.getjavajob.training.yakovleva.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class MessageController {
    private MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @RequestMapping(value = "/account-message", method = RequestMethod.GET)
    public ModelAndView list(HttpSession session, HttpServletRequest request) {
        System.out.println("list");
        ModelAndView modelAndView = new ModelAndView("/account/account-message");
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
            System.out.println(e);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/account-write-message", method = RequestMethod.GET)
    public ModelAndView write(HttpSession session, HttpServletRequest request) {
        System.out.println("write");
        ModelAndView modelAndView = new ModelAndView("/account/account-write-message");
        Account account = (Account) session.getAttribute("account");
        String selectUser = request.getParameter("selectUser");
        System.out.println("selectUser - " + selectUser);
        int idSender = Integer.parseInt(selectUser);
        request.setAttribute("account", account);
        List<Message> personalMail = messageService.getAccountMessages(idSender, account.getId());
        System.out.println("AccountWriteMessage.personalMail" + personalMail);
        request.setAttribute("personalMail", personalMail);
        return modelAndView;
    }

    @RequestMapping(value = "/account-write-message", method = RequestMethod.POST)
    public ModelAndView send(HttpSession session, HttpServletRequest request) {
        System.out.println("send");
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
            System.out.println(e);
        }
        return write(session, request);
    }

}
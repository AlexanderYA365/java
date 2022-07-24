package com.getjavajob.training.yakovleva.web.controllers;

import com.getjavajob.training.yakovleva.common.Account;
import com.getjavajob.training.yakovleva.common.Message;
import com.getjavajob.training.yakovleva.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@ControllerAdvice
@SessionAttributes({"account", "friend", "selectUser"})
public class MessageController {
    private MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @RequestMapping(value = "/account-message", method = RequestMethod.GET)
    public ModelAndView messages(@ModelAttribute("account") Account account) {
        System.out.println("list");
        ModelAndView modelAndView = new ModelAndView("/account/account-message");
        System.out.println("account - " + account);
        try {
            List<Message> uniqueMessages = messageService.getUniqueMessages(account);
            System.out.println("unique - " + uniqueMessages);
            if (uniqueMessages.size() != 0) {
                modelAndView.addObject("haveMessage", 0);
                modelAndView.addObject("uniqueMessages", uniqueMessages);
            } else {
                modelAndView.addObject("haveMessage", 1);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/account-message", method = RequestMethod.POST)
    public ModelAndView redirectToChart(@RequestParam("selectUser") int idFriend) {
        System.out.println("redirectToChart");
        System.out.println("idFriend - " + idFriend);
        ModelAndView modelAndView = new ModelAndView("redirect:/account-write-message");
        modelAndView.addObject("idFriend", idFriend);
        return modelAndView;
    }

    @RequestMapping(value = "/account-write-message", method = RequestMethod.GET)
    public ModelAndView write(@ModelAttribute("idFriend") int senderId,
                              @ModelAttribute("account") Account account) {
        System.out.println("write");
        System.out.println("senderId - " + senderId);
        System.out.println("account" + account);
        ModelAndView modelAndView = new ModelAndView("/account/account-write-message");
        List<Message> personalMail = messageService.getAccountMessages(senderId, account.getId());
        System.out.println("AccountWriteMessage.personalMail" + personalMail);
        modelAndView.addObject("personalMail", personalMail);
        modelAndView.addObject("selectUser", senderId);
        return modelAndView;
    }

    @RequestMapping(value = "/account-write-message", method = RequestMethod.POST)
    public ModelAndView send(@ModelAttribute("account") Account account,
                             @RequestParam(value = "NewMessage", required = false) String newMessage,
                             @ModelAttribute("selectUser") int receivingId) {
        System.out.println("send");
        System.out.println("new message - " + newMessage);
        System.out.println("select user - " + receivingId);
        try {
            Message message = new Message();
            message.setReceiverId(receivingId);
            message.setSenderId(account.getId());
            message.setMessage(newMessage);
            message.setMessageType(1);
            messageService.createMassage(message);
        } catch (Exception e) {
            System.out.println(e);
        }
        ModelAndView modelAndView = new ModelAndView("redirect:/account-write-message");
        modelAndView.addObject("idFriend", receivingId);
        modelAndView.addObject("account", account);
        return modelAndView;
    }

}
package com.getjavajob.training.yakovleva.web.controllers;

import com.getjavajob.training.yakovleva.common.Account;
import com.getjavajob.training.yakovleva.common.Message;
import com.getjavajob.training.yakovleva.service.MessageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

@Controller
@ControllerAdvice
@SessionAttributes({"account", "friend", "selectUser"})
public class MessageController {
    private MessageService messageService;
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
        logger.info("MessageController");
    }

    @RequestMapping(value = "/account-message", method = RequestMethod.GET)
    public ModelAndView messages(@ModelAttribute("account") Account account) {
        logger.info("messages(account = {})", account);
        ModelAndView modelAndView = new ModelAndView("/account/account-message");
        try {
            List<Message> uniqueMessages = messageService.getUniqueMessages(account);
            logger.info("uniqueMessages = {}", uniqueMessages);
            if (uniqueMessages.size() != 0) {
                modelAndView.addObject("haveMessage", 0);
                modelAndView.addObject("uniqueMessages", uniqueMessages);
            } else {
                modelAndView.addObject("haveMessage", 1);
            }
        } catch (Exception e) {
            logger.error("exception = {}", e);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/account-message", method = RequestMethod.POST)
    public ModelAndView redirectToChart(@RequestParam("selectUser") int idFriend) {
        logger.info("redirectToChart(idFriend = {})", idFriend);
        ModelAndView modelAndView = new ModelAndView("redirect:/account-write-message");
        modelAndView.addObject("idFriend", idFriend);
        return modelAndView;
    }

    @RequestMapping(value = "/account-write-message", method = RequestMethod.GET)
    public ModelAndView write(@ModelAttribute("idFriend") int senderId,
                              @ModelAttribute("account") Account account) {
        logger.info("write(senderId = {}, account = {})", senderId, account);
        ModelAndView modelAndView = new ModelAndView("/account/account-write-message");
        List<Message> personalMail = messageService.getAccountMessages(senderId, account.getId());
        logger.info("personalMail = {}", personalMail);
        modelAndView.addObject("personalMail", personalMail);
        modelAndView.addObject("selectUser", senderId);
        return modelAndView;
    }

    @RequestMapping(value = "/account-write-message", method = RequestMethod.POST)
    public ModelAndView send(@ModelAttribute("account") Account account,
                             @RequestParam(value = "NewMessage", required = false) String newMessage,
                             @ModelAttribute("selectUser") int receivingId) {
        logger.info("send(account = {}, newMessage = {}, receivingId = {})", account, newMessage, receivingId);
        try {
            Message message = new Message();
            message.setReceiverId(receivingId);
            message.setSenderId(account.getId());
            message.setMessage(newMessage);
            message.setPublicationDate(new Date());
            message.setMessageType(1);
            message.setAccount(account);
            messageService.createMassage(message);
        } catch (Exception e) {
            logger.error("Exception = {}", e);
        }
        ModelAndView modelAndView = new ModelAndView("redirect:/account-write-message");
        modelAndView.addObject("idFriend", receivingId);
        modelAndView.addObject("account", account);
        return modelAndView;
    }

}
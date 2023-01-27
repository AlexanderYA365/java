package com.getjavajob.training.yakovleva.web.controllers;

import com.getjavajob.training.yakovleva.common.Account;
import com.getjavajob.training.yakovleva.common.Message;
import com.getjavajob.training.yakovleva.service.AccountService;
import com.getjavajob.training.yakovleva.service.MessageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@ControllerAdvice
@SessionAttributes({"account", "friend", "selectUser"})
public class MessageController {
    private static final Logger logger = LogManager.getLogger(MessageController.class);
    private MessageService messageService;
    private AccountService accountService;

    @Autowired
    public MessageController(MessageService messageService, AccountService accountService) {
        this.messageService = messageService;
        this.accountService = accountService;
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
    public ModelAndView goChat(@ModelAttribute("account") Account account, @RequestParam("selectUser") int idFriend) {
        logger.info("goChat(account = {}, idFriend = {})", account, idFriend);
        ModelAndView modelAndView = new ModelAndView("/chat");
        Account friendId = accountService.get(idFriend);
        List<Message> personalMail = messageService.getAccountMessages(account.getId(), idFriend);
        modelAndView.addObject("username", account.getUsername());
        modelAndView.addObject("usernameReceiving", friendId.getUsername());
        modelAndView.addObject("usernameSender", account.getUsername());
        modelAndView.addObject("receiverId", friendId.getId());
        modelAndView.addObject("senderId", account.getId());
        modelAndView.addObject("personalMail", personalMail);
        return modelAndView;
    }

}
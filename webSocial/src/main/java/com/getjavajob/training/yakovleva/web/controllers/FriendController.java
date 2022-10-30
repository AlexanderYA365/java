package com.getjavajob.training.yakovleva.web.controllers;

import com.getjavajob.training.yakovleva.common.*;
import com.getjavajob.training.yakovleva.service.AccountService;
import com.getjavajob.training.yakovleva.service.ApplicationService;
import com.getjavajob.training.yakovleva.service.MessageService;
import com.getjavajob.training.yakovleva.service.RelationsService;
import com.getjavajob.training.yakovleva.web.controllers.utils.SocialNetworkUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
@ControllerAdvice
@SessionAttributes({"account", "friend"})
public class FriendController {
    private static final Logger logger = LogManager.getLogger();
    private RelationsService relationsService;
    private ApplicationService applicationService;
    private AccountService accountService;
    private MessageService messageService;

    @Autowired
    public FriendController(RelationsService relationsService,
                            ApplicationService applicationService,
                            AccountService accountService,
                            MessageService messageService) {
        this.relationsService = relationsService;
        this.applicationService = applicationService;
        this.accountService = accountService;
        this.messageService = messageService;
        logger.info("FriendController");
    }

    @RequestMapping(value = "/show-friend", method = RequestMethod.GET)
    public ModelAndView friendPage(@RequestParam("id") int friendId,
                                   HttpSession session,
                                   @SessionAttribute("account") Account account) {
        logger.info("friendPage(friendId = {}, account = {})", friendId, account);
        ModelAndView modelAndView = new ModelAndView("/friend/show-friend");
        try {
            Account friendAccount = accountService.get(friendId);
            logger.info("friendAccount = {}", friendAccount);
            Relations relations = new Relations(account.getId(), friendId);
            Application application = applicationService.getAccount(relations);
            logger.info("application = {}", application);
            int friendFlag = application.getId() != 0 ? application.getStatus() : 1;
            logger.info("friendFlag = {}", friendFlag);
            List<Message> messages = messageService.getWallMassageAccount(friendAccount);
            SocialNetworkUtils socialNetworkUtils = new SocialNetworkUtils();
            String encodedPhoto = socialNetworkUtils.loadPhoto(friendAccount);
            modelAndView.addObject("encodedPhoto", encodedPhoto);
            modelAndView.addObject("friendFlag", friendFlag);
            modelAndView.addObject("friendAccount", friendAccount);
            session.setAttribute("friend", friendAccount);
            modelAndView.addObject("account", account);
            modelAndView.addObject("messages", messages);
        } catch (Exception e) {
            logger.info("Exception = {}", e);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/show-friend", method = RequestMethod.POST)
    public ModelAndView addApplication(@RequestParam("id") int id,
                                       @SessionAttribute("friend") Account friend,
                                       @SessionAttribute("account") Account account,
                                       @RequestParam(value = "write-message", required = false) String writeMessage,
                                       @RequestParam(value = "delete", required = false) String delete,
                                       @RequestParam(value = "add", required = false) String add) {
        logger.info("addApplication(id = {}, friend = {}, account = {}, writeMessage = {}, delete = {}, add = {})",
                id, friend, account, writeMessage, delete, add);
        Relations relations = new Relations(account.getId(), friend.getId());
        Application application = applicationService.getAccount(relations);
        if (add != null) {
            relationsService.create(relations);
        }
        if (delete != null) {
            applicationService.delete(application);
            relationsService.deleteByAccountId(relations);
        }
        if (writeMessage != null) {
            logger.info("write message");
            ModelAndView modelAndView = new ModelAndView("redirect:/account-write-message");
            modelAndView.addObject("idFriend", friend.getId());
            modelAndView.addObject("account", account);
            return modelAndView;
        }
        ModelAndView modelAndView = new ModelAndView("/friend/show-friend");
        int friendFlag = application.getId() != 0 ? application.getStatus() : 1;
        logger.info("friendFlag = {}", friendFlag);
        List<Message> messages = messageService.getWallMassageAccount(friend);
        modelAndView.addObject("friendFlag", friendFlag);
        modelAndView.addObject("friendAccount", friend);
        modelAndView.addObject("messages", messages);
        modelAndView.addObject("account", account);
        return modelAndView;
    }

    @RequestMapping(value = "/add-friend-account", method = RequestMethod.GET)
    public ModelAndView addFriend() {
        logger.info("addFriend()");
        ModelAndView modelAndView = new ModelAndView("/friend/add-friend-account");
        return modelAndView;
    }

    @RequestMapping(value = "/friend-add-wall-message", method = RequestMethod.POST)
    public ModelAndView addFriendWallMassage(@ModelAttribute("account") Account account,
                                             @ModelAttribute("friend") Account friend,
                                             @RequestParam("NewWallMessage") String message) {
        logger.info("addFriendWallMassage(account = {}, friend = {}, message = {})",
                account, friend, message);
        Message wallMessage = new Message();
        wallMessage.setMessageType(MessageType.WALL);
        wallMessage.setMessage(message);
        wallMessage.setPublicationDate(new Date());
        wallMessage.setEdited(false);
        wallMessage.setUsernameSender(account.getUsername());
        wallMessage.setUsernameReceiving(friend.getUsername());
        wallMessage.setPicture("");
        wallMessage.setReceiverId(friend.getId());
        wallMessage.setSenderId(account.getId());
        messageService.createMassage(wallMessage);
        ModelAndView modelAndView = new ModelAndView("redirect:/show-friend");
        modelAndView.addObject("id", friend.getId());
        return modelAndView;
    }

    @RequestMapping(value = "/account-friends", method = RequestMethod.GET)
    public ModelAndView friends(@ModelAttribute("account") Account account) {
        logger.info("friends( account = {})", account);
        ModelAndView modelAndView = new ModelAndView("/friend/account-friends");
        List<Account> friends = accountService.getFriendsAccount(account.getId());
        logger.info("friends = {}", friends);
        modelAndView.addObject("friends", friends);
        return modelAndView;
    }

    @RequestMapping(value = "/account-friends", method = RequestMethod.POST)
    public ModelAndView friendsRedirect(@ModelAttribute("account") Account account,
                                        @ModelAttribute Account friend) {
        logger.info("friendsRedirect( account = {}, friend)", account, friend);
        ModelAndView modelAndView = new ModelAndView("/friend/account-friends");
        return modelAndView;
    }

    @RequestMapping(value = "/add-friend-account", method = RequestMethod.POST)
    public ModelAndView saveFriend(HttpSession session, HttpServletRequest request) {
        logger.info("saveFriend()");
        ModelAndView modelAndView = new ModelAndView("/friend/add-friend-account");
        String accountId = request.getParameter("friendId");
        String name = request.getParameter("name");
        try {
            if (accountId == null) {
                try {
                    List<Account> accounts = accountService.getAccountName(name);
                    System.out.println(accounts);
                    logger.info("accounts = {}", accounts);
                    request.setAttribute("accounts", accounts);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (accountId != null) {
                Account account = (Account) session.getAttribute("account");
                Account accountFriend = accountService.get(Integer.parseInt(accountId));
                System.out.println("account - " + account);
                System.out.println("friend - " + accountFriend);
                Relations relations = new Relations();
                relations.setAccountId(account.getId());
                relations.setFriendId(accountFriend.getId());
                relationsService.create(relations);
            }
        } catch (Exception e) {
            logger.info("Exception = {}", e);
        }
        return modelAndView;
    }

}
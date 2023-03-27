package com.getjavajob.training.yakovleva.web.controllers;

import com.getjavajob.training.yakovleva.common.Account;
import com.getjavajob.training.yakovleva.common.Application;
import com.getjavajob.training.yakovleva.common.Enum.ApplicationStatusType;
import com.getjavajob.training.yakovleva.common.Enum.ApplicationType;
import com.getjavajob.training.yakovleva.common.Enum.MessageType;
import com.getjavajob.training.yakovleva.common.Message;
import com.getjavajob.training.yakovleva.common.Relations;
import com.getjavajob.training.yakovleva.service.AccountService;
import com.getjavajob.training.yakovleva.service.ApplicationService;
import com.getjavajob.training.yakovleva.service.MessageService;
import com.getjavajob.training.yakovleva.service.RelationsService;
import com.getjavajob.training.yakovleva.web.controllers.utils.FriendTableUtils;
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
    private static final Logger logger = LogManager.getLogger(FriendController.class);
    private final RelationsService relationsService;
    private final ApplicationService applicationService;
    private final AccountService accountService;
    private final MessageService messageService;
    private Account friendAccount;

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
    public ModelAndView showFriend(@RequestParam("id") int id,
                                   @SessionAttribute("friend") Account friend,
                                   @SessionAttribute("account") Account account,
                                   @RequestParam(value = "write-message", required = false) String writeMessage,
                                   @RequestParam(value = "delete", required = false) String delete,
                                   @RequestParam(value = "add", required = false) String add) {
        logger.info("showFriend(id = {}, friend = {}, account = {}, writeMessage = {}, delete = {}, add = {})",
                id, friend.getId(), account.getId(), writeMessage, delete, add);
        Relations relations = new Relations(account.getId(), friend.getId());
        if (add != null) {
            addFriendAccount(friend, account, relations);
        }
        if (delete != null) {
            logger.info("delete friend");
            Application application = applicationService.getAccount(relations);
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
        Application application = applicationService.getAccount(relations);
        int friendFlag = application.getId() != 0 ? application.getStatus() : 1;
        logger.info("friendFlag = {}", friendFlag);
        List<Message> messages = messageService.getWallMassageAccount(friend);
        modelAndView.addObject("friendFlag", friendFlag);
        modelAndView.addObject("friendAccount", friend);
        modelAndView.addObject("messages", messages);
        modelAndView.addObject("account", account);
        return modelAndView;
    }

    private void addFriendAccount(Account friend, Account account, Relations relations) {
        logger.info("addFriend(friend = {}, account = {}, relations = {})", friend, account, relations);
        Application newApplication = new Application();
        newApplication.setStatus(ApplicationStatusType.CONFIRMATION);
        newApplication.setApplicationType(ApplicationType.USER);
        newApplication.setRecipientId(friend.getId());
        newApplication.setApplicantId(account.getId());
        applicationService.create(newApplication);
        relationsService.create(relations);
    }

    @RequestMapping(value = "/add-friend-account", method = RequestMethod.GET)
    public ModelAndView addFriend() {
        logger.info("addFriend()");
        return new ModelAndView("/friend/add-friend-account");
    }

    @RequestMapping(value = "/denied-application", method = RequestMethod.GET)
    public ModelAndView deniedApplication(@SessionAttribute("account") Account account) {
        logger.info("deniedApplication(account = {})", account);
        List<Account> deniedFriends = accountService.getFriendRejected(account);
        ModelAndView modelAndView = new ModelAndView("/friend/denied-application");
        modelAndView.addObject("deniedFriends", deniedFriends);
        logger.info("deniedFriends = {}", deniedFriends);
        return modelAndView;
    }

    @RequestMapping(value = "/friend-requests", method = RequestMethod.GET)
    public ModelAndView applicationFriends(@ModelAttribute("account") Account account) {
        logger.info("applicationFriends(account = {})", account);
        friendAccount = account;
        return new ModelAndView("/friend/friend-requests");
    }

    @RequestMapping(value = "/get-friend-requests", method = RequestMethod.GET)
    @ResponseBody
    public FriendTableUtils getFriendRequests(final @RequestParam("draw") int draw,
                                              final @RequestParam("start") int start,
                                              final @RequestParam("length") int length) {
        logger.info("getFriendRequests(draw = {}, start = {}, length = {})", draw, start, length);
        logger.info("getFriendRequests(friendAccount = {})", friendAccount);
        List<Account> friendRequests = accountService.getFriendRequests(friendAccount);
        long size = friendRequests.size();
        logger.info("records size = {}", size);
        return new FriendTableUtils(draw, size, size, friendRequests);
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

    @RequestMapping(value = "/denied-application/{id}",
            produces = "application/json",
            method = RequestMethod.GET)
    public void deniedApplication(@PathVariable String id, @SessionAttribute("account") Account account) {
        logger.info("deniedApplication(id = {}, account = {})", id, account);
        Application application = applicationService.get(Integer.parseInt(id), account.getId());
        application.setStatus(ApplicationStatusType.REJECTED);
        applicationService.update(application);
    }

    @RequestMapping(value = "/accept-application-friend/{id}",
            produces = "application/json",
            method = RequestMethod.GET)
    public void acceptApplicationFriend(@PathVariable String id, @SessionAttribute("account") Account account) {
        logger.info("acceptApplicationFriend(id = {}, account = {})", id, account);
        Application application = applicationService.get(Integer.parseInt(id), account.getId());
        application.setStatus(ApplicationStatusType.ACCEPTED);
        Relations relations = new Relations(account.getId(), Integer.parseInt(id));
        relationsService.create(relations);
        applicationService.update(application);
    }

    @RequestMapping(value = "/account-friends", method = RequestMethod.POST)
    public ModelAndView friendsRedirect(@ModelAttribute("account") Account account,
                                        @ModelAttribute("friend") Account friend) {
        logger.info("friendsRedirect( account = {}, friend = {})", account, friend);
        return new ModelAndView("/friend/account-friends");
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
                    logger.info("accounts = {}", accounts);
                    request.setAttribute("accounts", accounts);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (accountId != null) {
                Account account = (Account) session.getAttribute("account");
                Account accountFriend = accountService.get(Integer.parseInt(accountId));
                logger.info("account = {}", account);
                logger.info("friend = {}", accountFriend);
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
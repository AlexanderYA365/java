package com.getjavajob.training.yakovleva.web.controllers;

import com.getjavajob.training.yakovleva.dao.Account;
import com.getjavajob.training.yakovleva.dao.Application;
import com.getjavajob.training.yakovleva.dao.Message;
import com.getjavajob.training.yakovleva.dao.Relations;
import com.getjavajob.training.yakovleva.service.AccountService;
import com.getjavajob.training.yakovleva.service.ApplicationService;
import com.getjavajob.training.yakovleva.service.MessageService;
import com.getjavajob.training.yakovleva.service.RelationsService;
import com.getjavajob.training.yakovleva.web.controllers.utils.SocialNetworkUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@ControllerAdvice
@SessionAttributes({"account", "friend"})
public class FriendController {
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
    }

    @RequestMapping(value = "/show-friend", method = RequestMethod.GET)
    public ModelAndView friendPage(@RequestParam("id") int friendId,
                                   HttpSession session,
                                   @SessionAttribute("account") Account account) {
        System.out.println("friendPage");
        System.out.println(account);
        System.out.println("id - " + friendId);
        ModelAndView modelAndView = new ModelAndView("/friend/show-friend");
        try {
            System.out.println("friendId - " + friendId);
            Account friendAccount = accountService.get(friendId);
            System.out.println("ShowFriend friendAccount - " + friendAccount);
            Relations relations = new Relations(account.getId(), friendId);
            Application application = applicationService.getAccount(relations);
            System.out.println("application - " + application);
            int friendFlag = application.getId() != 0 ? application.getStatus() : 1;
            System.out.println("friendFlag - " + friendFlag);
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
            System.out.println(e);//send redirect
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
            System.out.println("write message");
            ModelAndView modelAndView = new ModelAndView("redirect:/account-write-message");
            modelAndView.addObject("idFriend", friend.getId());
            modelAndView.addObject("account", account);
            return modelAndView;
        }
        ModelAndView modelAndView = new ModelAndView("/friend/show-friend");
        int friendFlag = application.getId() != 0 ? application.getStatus() : 1;
        System.out.println("friendFlag - " + friendFlag);
        List<Message> messages = messageService.getWallMassageAccount(friend);
        modelAndView.addObject("friendFlag", friendFlag);
        modelAndView.addObject("friendAccount", friend);
        modelAndView.addObject("messages", messages);
        modelAndView.addObject("account", account);
        return modelAndView;
    }

    @RequestMapping(value = "/add-friend-account", method = RequestMethod.GET)
    public ModelAndView addFriend() {
        System.out.println("addFriend");
        ModelAndView modelAndView = new ModelAndView("/friend/add-friend-account");
        return modelAndView;
    }

    @RequestMapping(value = "/friend-wall-message", method = RequestMethod.POST)
    public void addFriendWallMassage(@ModelAttribute("account") Account account,
                                     @ModelAttribute("friend") Account friend,
                                     @RequestParam("NewWallMessage") String message) {
        System.out.println("account - " + account);
        System.out.println("friend - " + friend);
        System.out.println("message - " + message);
        System.out.println("addFriendWallMassage");
//        ModelAndView modelAndView = new ModelAndView("redirect:/show-friend");
//        return modelAndView;
    }

    @RequestMapping(value = "/account-friends", method = RequestMethod.GET)
    public ModelAndView friends(@ModelAttribute("account") Account account) {
        System.out.println("friends");
        ModelAndView modelAndView = new ModelAndView("/friend/account-friends");
        List<Account> friends = accountService.getFriendsAccount(account.getId());
        System.out.println("friends - " + friends);
        modelAndView.addObject("friends", friends);
        return modelAndView;
    }

    @RequestMapping(value = "/account-friends", method = RequestMethod.POST)
    public ModelAndView friendsRedirect(@ModelAttribute("account") Account account,
                                        @ModelAttribute Account friend) {
        System.out.println("friendsRedirect");
        ModelAndView modelAndView = new ModelAndView("/friend/account-friends");
        return modelAndView;
    }

    @RequestMapping(value = "/add-friend-account", method = RequestMethod.POST)
    public ModelAndView saveFriend(HttpSession session, HttpServletRequest request) {
        System.out.println("saveFriend");
        ModelAndView modelAndView = new ModelAndView("/friend/add-friend-account");
        String accountId = request.getParameter("friendId");
        String name = request.getParameter("name");
        try {
            if (accountId == null) {
                try {
                    List<Account> accounts = accountService.getAccountName(name);
                    System.out.println(accounts);
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
            System.out.println(e);//send redirect
        }
        return modelAndView;
    }

}
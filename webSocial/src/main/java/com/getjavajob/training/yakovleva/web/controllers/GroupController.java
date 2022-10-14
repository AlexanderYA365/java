package com.getjavajob.training.yakovleva.web.controllers;

import com.getjavajob.training.yakovleva.common.Account;
import com.getjavajob.training.yakovleva.common.Application;
import com.getjavajob.training.yakovleva.common.Group;
import com.getjavajob.training.yakovleva.service.ApplicationService;
import com.getjavajob.training.yakovleva.service.GroupService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@SessionAttributes("account")
public class GroupController {
    private GroupService groupService;
    private ApplicationService applicationService;

    public GroupController(GroupService groupService, ApplicationService applicationService) {
        this.groupService = groupService;
        this.applicationService = applicationService;
    }

    @RequestMapping(value = "/account-find-group", method = RequestMethod.GET)
    public ModelAndView findGroupView() {
        System.out.println("findGroupView");
        ModelAndView modelAndView = new ModelAndView("/group/account-find-group");
        return modelAndView;
    }

    @RequestMapping(value = "/account-find-group", method = RequestMethod.POST)
    public ModelAndView addFindGroupView(HttpSession session, HttpServletRequest request) {
        System.out.println("findGroupView");
        ModelAndView modelAndView = new ModelAndView("/group/account-find-group");
        String groupId = request.getParameter("groupId");
        System.out.println("groupId - " + groupId);
        String groupName = request.getParameter("GroupName");
        try {
            if (groupId == null) {
                try {
                    List<Group> groups = groupService.getGroupName(groupName);
                    System.out.println(groups);
                    request.setAttribute("findGroups", groups);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("exception in addFindGroupView - " + e);
                }
            }
            if (groupId != null) {
                Account account = (Account) session.getAttribute("account");
                Group addGroup = groupService.getGroupID(Integer.parseInt(groupId));
                System.out.println("Account - " + account);
                System.out.println("addGroup - " + addGroup);
                groupService.insertAccountGroup(addGroup, account.getId());
            }
        } catch (Exception e) {
            System.out.println(e);//send redirect
        }
        return modelAndView;
    }

    @RequestMapping(value = "/create-group", method = RequestMethod.POST)
    public ModelAndView saveGroup(HttpSession session, HttpServletRequest request) {
        System.out.println("saveGroup");
        ModelAndView modelAndView = new ModelAndView("/group/create-group");
        Account account = (Account) session.getAttribute("account");
        List<Group> groups = groupService.getAllGroups();
        try {
            if (createNewGroup(groups, request.getParameter("name"))) {
                Group group = new Group();
                group.setAdministratorId(account.getId());
                group.setAccountId(account.getId());
                group.setGroupName(request.getParameter("name"));
                group.setLogo(request.getParameter("logo"));
                System.out.println("AccountCreateGroup doPost - " + group);
                groupService.createAccountGroups(group);
            }
        } catch (Exception e) {
            System.out.println("Error");
        }
        return modelAndView;
    }

    private boolean createNewGroup(List<Group> groups, String groupName) {
        boolean flag = false;
        System.out.println("createGroup, group name - " + groupName);
        for (Group group : groups) {
            if (!group.getGroupName().equals(groupName)) {
                flag = true;
            }
        }
        System.out.println("createGroup flag - " + flag);
        return flag;
    }

    @RequestMapping(value = "/create-group", method = RequestMethod.GET)
    public ModelAndView createGroup() {
        System.out.println("createGroup");
        return new ModelAndView("/group/create-group");
    }

    @RequestMapping(value = "/show-group", method = RequestMethod.GET)
    public ModelAndView viewGroup(HttpSession session, HttpServletRequest request) {
        System.out.println("viewGroup");
        ModelAndView modelAndView = new ModelAndView("/group/show-group");
        try {
            Group group = groupService.getGroupID(Integer.parseInt(request.getParameter("id")));
            Account account = (Account) session.getAttribute("account");
            Application application = applicationService.getGroupAccount(group, account.getId());
            System.out.println("Application - " + application);
            if (application != null) {
                request.setAttribute("application", application);
                modelAndView.addObject("application", application);
                int newUserGroup = application.getStatus();
                modelAndView.addObject("newUserGroup", newUserGroup);
                request.setAttribute("newUserGroup", newUserGroup);
            }
            System.out.println(group);
            request.setAttribute("group", group);
            modelAndView.addObject("group", group);
        } catch (Exception e) {
            System.out.println(e);//send redirect
        }
        return modelAndView;
    }

    @RequestMapping(value = "/account-group", method = RequestMethod.GET)
    public ModelAndView accountGroup(HttpSession session) {
        System.out.println("accountGroup");
        ModelAndView modelAndView = new ModelAndView("/group/account-group");
        Account account = (Account) session.getAttribute("account");
        System.out.println("account - " + account);
        try {
            List<Group> groups = groupService.getAccountGroups(account);
            System.out.println(groups);
            session.setAttribute("groups", groups);
            modelAndView.addObject("groups", groups);
        } catch (Exception e) {
            System.out.println(e);//send redirect
        }
        return modelAndView;
    }

}

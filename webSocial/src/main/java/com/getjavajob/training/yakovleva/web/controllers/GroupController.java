package com.getjavajob.training.yakovleva.web.controllers;

import com.getjavajob.training.yakovleva.common.Account;
import com.getjavajob.training.yakovleva.common.Application;
import com.getjavajob.training.yakovleva.common.Group;
import com.getjavajob.training.yakovleva.service.ApplicationService;
import com.getjavajob.training.yakovleva.service.GroupService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private static final Logger logger = LogManager.getLogger(GroupController.class);
    private GroupService groupService;
    private ApplicationService applicationService;

    public GroupController(GroupService groupService, ApplicationService applicationService) {
        this.groupService = groupService;
        this.applicationService = applicationService;
        logger.info("GroupController");
    }

    @RequestMapping(value = "/account-find-group", method = RequestMethod.GET)
    public ModelAndView findGroupView() {
        logger.info("findGroupView()");
        ModelAndView modelAndView = new ModelAndView("/group/account-find-group");
        return modelAndView;
    }

    @RequestMapping(value = "/account-find-group", method = RequestMethod.POST)
    public ModelAndView addFindGroupView(HttpSession session, HttpServletRequest request) {
        logger.info("addFindGroupView()");
        ModelAndView modelAndView = new ModelAndView("/group/account-find-group");
        String groupId = request.getParameter("groupId");
        logger.info("groupId = {}", groupId);
        String groupName = request.getParameter("GroupName");
        try {
            if (groupId == null) {
                try {
                    List<Group> groups = groupService.getGroupName(groupName);
                    logger.info("groups = {}", groups);
                    request.setAttribute("findGroups", groups);
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.info("Exception = {}", e);
                }
            }
            if (groupId != null) {
                Account account = (Account) session.getAttribute("account");
                Group addGroup = groupService.getGroupID(Integer.parseInt(groupId));
                logger.info("account = {}, addGroup = {}", account, addGroup);
                groupService.insertAccountGroup(addGroup, account.getId());
            }
        } catch (Exception e) {
            logger.error("Exception = {}", e);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/create-group", method = RequestMethod.POST)
    public ModelAndView saveGroup(HttpSession session, HttpServletRequest request) {
        logger.info("saveGroup()");
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
                logger.info("group = {}", group);
                groupService.createAccountGroups(group);
            }
        } catch (Exception e) {
            logger.error("Exception = {}", e);
        }
        return modelAndView;
    }

    private boolean createNewGroup(List<Group> groups, String groupName) {
        boolean flag = false;
        logger.info("createNewGroup(groupName = {})", groupName);
        for (Group group : groups) {
            if (!group.getGroupName().equals(groupName)) {
                flag = true;
            }
        }
        logger.info("createNewGroup(flag = {})", flag);
        return flag;
    }

    @RequestMapping(value = "/create-group", method = RequestMethod.GET)
    public ModelAndView createGroup() {
        logger.info("createGroup()");
        return new ModelAndView("/group/create-group");
    }

    @RequestMapping(value = "/show-group", method = RequestMethod.GET)
    public ModelAndView viewGroup(HttpSession session, HttpServletRequest request) {
        logger.info("viewGroup()");
        ModelAndView modelAndView = new ModelAndView("/group/show-group");
        try {
            Group group = groupService.getGroupID(Integer.parseInt(request.getParameter("id")));
            Account account = (Account) session.getAttribute("account");
            Application application = applicationService.getGroupAccount(group, account.getId());
            logger.info("application = {}", application);
            if (application != null) {
                request.setAttribute("application", application);
                modelAndView.addObject("application", application);
                int newUserGroup = application.getStatus();
                modelAndView.addObject("newUserGroup", newUserGroup);
                request.setAttribute("newUserGroup", newUserGroup);
            }
            logger.info("group = {}", group);
            request.setAttribute("group", group);
            modelAndView.addObject("group", group);
        } catch (Exception e) {
            logger.error("Exception = {}", e);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/account-group", method = RequestMethod.GET)
    public ModelAndView accountGroup(HttpSession session) {
        logger.info("accountGroup()");
        ModelAndView modelAndView = new ModelAndView("/group/account-group");
        Account account = (Account) session.getAttribute("account");
        logger.info("account = {}", account);
        try {
            List<Group> groups = groupService.getAccountGroups(account);
            logger.info("groups = {}", groups);
            session.setAttribute("groups", groups);
            modelAndView.addObject("groups", groups);
        } catch (Exception e) {
            logger.error("Exception = {}", e);
        }
        return modelAndView;
    }

}
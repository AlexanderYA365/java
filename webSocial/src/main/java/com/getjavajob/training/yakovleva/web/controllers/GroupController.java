package com.getjavajob.training.yakovleva.web.controllers;

import Repository.GroupMembersRepository;
import com.getjavajob.training.yakovleva.common.*;
import com.getjavajob.training.yakovleva.common.Enum.ApplicationStatusType;
import com.getjavajob.training.yakovleva.common.Enum.ApplicationType;
import com.getjavajob.training.yakovleva.common.Enum.MessageType;
import com.getjavajob.training.yakovleva.service.ApplicationService;
import com.getjavajob.training.yakovleva.service.GroupService;
import com.getjavajob.training.yakovleva.service.MessageService;
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
@SessionAttributes("account")
public class GroupController {
    private static final Logger logger = LogManager.getLogger(GroupController.class);
    private final GroupService groupService;
    private final ApplicationService applicationService;
    private final MessageService messageService;
    private final GroupMembersRepository groupMembersRepository;

    @Autowired
    public GroupController(GroupService groupService, ApplicationService applicationService,
                           MessageService messageService, GroupMembersRepository groupMembersRepository) {
        this.groupService = groupService;
        this.applicationService = applicationService;
        this.messageService = messageService;
        this.groupMembersRepository = groupMembersRepository;
        logger.info("GroupController");
    }

    @RequestMapping(value = "/account-find-group", method = RequestMethod.GET)
    public ModelAndView findGroupView() {
        logger.info("findGroupView()");
        return new ModelAndView("/group/account-find-group");
    }

    @RequestMapping(value = "/account-find-group", method = RequestMethod.POST)
    public ModelAndView addFindGroupView(@RequestParam("groupId") int groupId,
                                         HttpSession session, HttpServletRequest request) {
        logger.info("addFindGroupView(groupId = {})", groupId);
        ModelAndView modelAndView = new ModelAndView("/group/account-find-group");
        String groupId2 = request.getParameter("groupId");
        logger.info("groupId = {}", groupId);
        String groupName = request.getParameter("GroupName");
        try {
            if (groupId2 == null) {
                try {
                    List<Group> groups = groupService.getByGroupName(groupName);
                    logger.info("groups = {}", groups);
                    request.setAttribute("findGroups", groups);
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.info("Exception = {}", e);
                }
            }
            if (groupId2 != null) {
                Account account = (Account) session.getAttribute("account");
                Group addGroup = groupService.get(Integer.parseInt(groupId2));
                logger.info("account = {}, addGroup = {}", account, addGroup);
                groupService.insertAccountGroup(addGroup, account.getId());
            }
        } catch (Exception e) {
            logger.error("Exception - " + e);
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
                group.setIdGroupCreator(account.getId());
//                group.setAccountId(account.getId());
                group.setGroupName(request.getParameter("name"));
                group.setLogo(request.getParameter("logo"));
                logger.info("group = {}", group);
                boolean isGroupCreate = groupService.createAccountGroups(group);
                if (isGroupCreate) {
                    Group newGroup = groupService.get(group.getGroupName());
                    Application application = new Application(ApplicationType.GROUP, account.getId(),
                            newGroup.getGroupId(), ApplicationStatusType.CONFIRMATION);
                    boolean isApplication = applicationService.create(application);
                    logger.info("create application isApplication = {}", isApplication);
                }
            }
        } catch (Exception e) {
            logger.error("Exception - " + e);
        }
        return modelAndView;
    }

    private boolean createNewGroup(List<Group> groups, String groupName) {
        boolean flag = false;
        logger.info("createNewGroup(groupName = {})", groupName);
        for (Group group : groups) {
            if (!group.getGroupName().equals(groupName)) {
                flag = true;
                break;
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

    @RequestMapping(value = "/group-add-message", method = RequestMethod.POST)
    public ModelAndView addMessage(@ModelAttribute("account") Account account,
                                   @ModelAttribute("group") Group group,
                                   @RequestParam("NewWallMessage") String message) {
        logger.info("addMessage(account = {}, group = {}, message = {})",
                account, group, message);
        Message wallMessage = new Message();
        wallMessage.setMessageType(MessageType.WALL);
        wallMessage.setMessage(message);
        wallMessage.setPublicationDate(new Date());
        wallMessage.setEdited(false);
        wallMessage.setUsernameSender(account.getUsername());
        wallMessage.setUsernameReceiving(group.getGroupName());
        wallMessage.setPicture("");
        wallMessage.setReceiverId(group.getGroupId());
        wallMessage.setSenderId(account.getId());
        logger.info("message - {}", wallMessage);
//        messageService.createMassage(wallMessage);
        ModelAndView modelAndView = new ModelAndView("redirect:/show-group");
        modelAndView.addObject("id", group.getGroupId());
        return modelAndView;
    }

    @RequestMapping(value = "/show-group", method = RequestMethod.GET)
    public ModelAndView viewGroup(@ModelAttribute("account") Account account,
                                  @ModelAttribute("id") int idGroup) {
        logger.info("viewGroup(account - {}, idGroup - {})", account, idGroup);
        ModelAndView modelAndView = new ModelAndView("/group/show-group");
        try {
            Group group = groupService.get(idGroup);
            Application application = applicationService.getGroupAccount(group, account.getId());
            int statusAccount = application.getId() != 0 ? application.getStatus() : 1;
            List<GroupMembers> members = groupMembersRepository.getMembersByGroup(group);
            List<Message> groupMessages = messageService.getMessages(group);
            modelAndView.addObject("group", group);
            modelAndView.addObject("groupMessages", groupMessages);
            modelAndView.addObject("members", members);
            modelAndView.addObject("application", application);
            logger.info("groupMessages - {}", groupMessages);
            logger.info("groups = {}", group);
            logger.info("members - {}", members);
            logger.info("application = {}", application);
            logger.info("statusAccount = {}", statusAccount);
        } catch (Exception e) {
            logger.error("Exception - " + e);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/account-group", method = RequestMethod.GET)
    public ModelAndView accountGroup(@ModelAttribute("account") Account account) {
        logger.info("accountGroup(account = {})", account);
        ModelAndView modelAndView = new ModelAndView("/group/account-group");
        List<GroupMembers> groupMembers = groupMembersRepository.getGroupByMember_Id(account.getId());
        modelAndView.addObject("groupMembers", groupMembers);
        logger.info("groupMembers - {}", groupMembers);
        return modelAndView;
    }

}
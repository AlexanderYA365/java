package com.getjavajob.training.yakovleva.web.controllers;

import com.getjavajob.training.yakovleva.Repository.GroupMembersRepository;
import com.getjavajob.training.yakovleva.common.*;
import com.getjavajob.training.yakovleva.common.Enum.ApplicationStatusType;
import com.getjavajob.training.yakovleva.common.Enum.ApplicationType;
import com.getjavajob.training.yakovleva.common.Enum.GroupRole;
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
@SessionAttributes({"account", "members"})
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

    @RequestMapping(value = "/group-admin-panel", method = RequestMethod.GET)
    public ModelAndView groupAdminPanel(@ModelAttribute("members") GroupMembers members) {
        logger.info("groupAdminPanel(members = {})", members);
        ModelAndView modelAndView = new ModelAndView("/group/group-admin-panel");
        modelAndView.addObject("members", members);
        return modelAndView;
    }

    @RequestMapping(value = "/getGroupMembers", method = RequestMethod.GET)
    @ResponseBody
    public TableResult updateTable(final @RequestParam("draw") int draw,
                                   final @RequestParam("start") int start,
                                   final @RequestParam("length") int length,
                                   @SessionAttribute("members") GroupMembers members) {
        logger.info("updateTable(draw = {}, start = {}, length = {})", draw, start, length, members);
        List<GroupMembers> groupMembers = groupMembersRepository.getMembersByGroup(members.getGroup());
        long max = groupMembers.size();
        TableResult tableResult = new TableResult(draw, max, max, groupMembers);
        return tableResult;
    }

    @RequestMapping(value = "/update-role-member/{id}/{role}",
            produces = "application/json",
            method = RequestMethod.GET)
    public void updateRole(@PathVariable String id, @PathVariable String role,
                           @SessionAttribute("members") GroupMembers members) {
        logger.info("updateRole(id = {}, role = {}, members = {})", id, role, members.getGroup());
        GroupRole groupRole = GroupRole.valueOf(role);
        groupMembersRepository.updateRoleGroupMembers(groupRole, Integer.valueOf(id));
    }

    @RequestMapping(value = "/delete-member/{id}",
            produces = "application/json",
            method = {RequestMethod.DELETE, RequestMethod.GET})
    public void deleteMember(@PathVariable String id,
                             @SessionAttribute("members") GroupMembers members) {
        logger.info("deleteMember(id = {}, members = {})", id, members.getGroup());
        groupMembersRepository.deleteMemberFromGroupMembers(Integer.valueOf(id), members.getGroup().getGroupId());

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
    public ModelAndView createGroup(@ModelAttribute("group") Group group,
                                    @ModelAttribute("account") Account account) {
        logger.info("createGroup(group = {}, account = {})", group, account);
        ModelAndView modelAndView = new ModelAndView("/group/create-group");
        List<Group> groups = groupService.getAllGroups();
        try {
            if (createNewGroup(groups, group.getGroupName())) {
                group.setIdGroupCreator(account.getId());
                boolean isGroupCreate = groupService.createAccountGroups(group);
                Group createdGroup = groupService.get(group.getGroupName());
                logger.info("createdGroup = {}", createdGroup);
                if (isGroupCreate) {
                    Application application = new Application(ApplicationType.GROUP, createdGroup.getGroupId(),
                            account.getId(), ApplicationStatusType.ACCEPTED);
                    boolean isApplication = applicationService.create(application);
                    GroupMembers groupMembers = new GroupMembers();
                    groupMembers.setGroupRole(GroupRole.ADMIN);
                    groupMembers.setGroup(createdGroup);
                    groupMembers.setMember(account);
                    groupMembersRepository.save(groupMembers);
                    logger.info("groupMembers - {}", groupMembers);
                    logger.info("accountId - {}", account.getId());
                    logger.info("result create application = {}", isApplication);
                    modelAndView.addObject("account", account);
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

    @RequestMapping(value = "/group-add-members", method = RequestMethod.POST)
    public ModelAndView addMembers(@ModelAttribute("account") Account account,
                                   @ModelAttribute("members") GroupMembers groupMembers) {
        logger.info("addMembers(account = {}, groupMembers = {})", account, groupMembers);
        try {
            GroupMembers newMember = new GroupMembers();
            Application application = new Application();
            application.setApplicantId(groupMembers.getGroup().getGroupId());
            application.setRecipientId(account.getId());
            application.setApplicationType(ApplicationType.GROUP);
            application.setStatus(ApplicationStatusType.CONFIRMATION);
            applicationService.create(application);
            newMember.setMember(account);
            newMember.setGroup(groupService.get(groupMembers.getGroup().getGroupId()));
            logger.info("groupMembers.getGroup() = {}", groupMembers.getGroup());
            newMember.setGroupRole(GroupRole.SUBSCRIBER);
            logger.info("newMember = {}", newMember);
            groupMembersRepository.save(newMember);
            logger.info("application = {}", application);
            logger.info("newMember = {}", newMember);
        } catch (Exception ex) {
            logger.info("Exception - " + ex);
        }
        return new ModelAndView("main");
    }

    @RequestMapping(value = "/create-group", method = RequestMethod.GET)
    public ModelAndView createGroup() {
        logger.info("createGroup()");
        return new ModelAndView("/group/create-group");
    }

    @RequestMapping(value = "/delete-group", method = RequestMethod.POST)
    public ModelAndView deleteGroup(@ModelAttribute("groupId") int groupId,
                                    @ModelAttribute("account") Account account) {
        logger.info("deleteGroup(groupId = {})", groupId);
        Group group = groupService.get(groupId);
        Application application = applicationService.getGroupAccount(group, account.getId());
        if (applicationService.delete(application)) {
            groupMembersRepository.deleteByGroup(group);
        }
        return new ModelAndView("redirect:/account-group");
    }

    @RequestMapping(value = "/group-add-message", method = RequestMethod.POST)
    public ModelAndView addMessage(@ModelAttribute("account") Account account,
                                   @ModelAttribute("members") GroupMembers groupMembers,
                                   @RequestParam("NewWallMessage") String message) {
        logger.info("addMessage(account = {}, group = {}, message = {})",
                account, groupMembers, message);
        Message wallMessage = new Message();
        wallMessage.setMessageType(MessageType.WALL);
        wallMessage.setMessage(message);
        wallMessage.setPublicationDate(new Date());
        wallMessage.setEdited(false);
        wallMessage.setUsernameSender(account.getUsername());
        wallMessage.setUsernameReceiving(groupMembers.getGroup().getGroupName());
        wallMessage.setPicture("");
        wallMessage.setMessageType(MessageType.GROUP);
        wallMessage.setReceiverId(groupMembers.getGroup().getGroupId());
        wallMessage.setSenderId(account.getId());
        logger.info("message - {}", wallMessage);
        messageService.createMassage(wallMessage);
        ModelAndView modelAndView = new ModelAndView("redirect:/show-group");
        modelAndView.addObject("id", groupMembers.getGroup().getGroupId());
        logger.info("id - {}", groupMembers.getGroup().getGroupId());
        return modelAndView;
    }

    @RequestMapping(value = "/show-group", method = RequestMethod.GET)
    public ModelAndView viewGroup(@SessionAttribute("account") Account account,
                                  @ModelAttribute("groupId") int idGroup) {
        logger.info("viewGroup(accountId - {}, idGroup - {})", account.getId(), idGroup);
        ModelAndView modelAndView = new ModelAndView("/group/show-group");
        try {
            Group group = groupService.get(idGroup);
            Application application = applicationService.getGroupAccount(group, account.getId());
            int groupFlag = application.getId() != 0 ? application.getStatus() : 3;
            List<GroupMembers> members = groupMembersRepository.getMembersByGroup(group);
            List<Message> groupMessages = messageService.getMessages(group);
            modelAndView.addObject("group", group);
            modelAndView.addObject("account", account);
            modelAndView.addObject("groupMessages", groupMessages);
            modelAndView.addObject("members", members);
            modelAndView.addObject("application", application);
            modelAndView.addObject("groupFlag", groupFlag);
            logger.info("groupMessages - {}", groupMessages);
            logger.info("groups = {}", group);
            logger.info("members - {}", members);
            logger.info("application = {}", application);
            logger.info("groupFlag = {}", groupFlag);
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
        modelAndView.addObject("account", account);
        logger.info("groupMembers - {}", groupMembers);
        logger.info("accountId - {}", account.getId());
        return modelAndView;
    }

    class TableResult {
        private int draw;
        private long recordsTotal;
        private long recordsFiltered;
        private List<GroupMembers> data;

        public TableResult(int draw, long recordsTotal, long recordsFiltered, List<GroupMembers> data) {
            this.draw = draw;
            this.recordsTotal = recordsTotal;
            this.recordsFiltered = recordsFiltered;
            this.data = data;
        }

        public int getDraw() {
            return draw;
        }

        public void setDraw(int draw) {
            this.draw = draw;
        }

        public long getRecordsTotal() {
            return recordsTotal;
        }

        public void setRecordsTotal(int recordsTotal) {
            this.recordsTotal = recordsTotal;
        }

        public long getRecordsFiltered() {
            return recordsFiltered;
        }

        public void setRecordsFiltered(int recordsFiltered) {
            this.recordsFiltered = recordsFiltered;
        }

        public List<GroupMembers> getData() {
            return data;
        }

        public void setData(List<GroupMembers> data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return "TableResult{" +
                    "draw=" + draw +
                    ", recordsTotal=" + recordsTotal +
                    ", recordsFiltered=" + recordsFiltered +
                    ", data=" + data +
                    '}';
        }
    }

}
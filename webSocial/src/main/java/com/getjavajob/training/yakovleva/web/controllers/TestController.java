package com.getjavajob.training.yakovleva.web.controllers;

import Repository.GroupMembersRepository;
import com.getjavajob.training.yakovleva.common.Account;
import com.getjavajob.training.yakovleva.common.Enum.GroupRole;
import com.getjavajob.training.yakovleva.common.Group;
import com.getjavajob.training.yakovleva.common.GroupMembers;
import com.getjavajob.training.yakovleva.service.AccountService;
import com.getjavajob.training.yakovleva.service.GroupService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class TestController {
    private static final Logger logger = LogManager.getLogger(SearchController.class);
    private final AccountService accountService;
    private final GroupService groupService;
    private final GroupMembersRepository groupMembersRepository;

    @Autowired
    public TestController(AccountService accountService, GroupService groupService,
                          GroupMembersRepository groupMembersRepository) {
        this.groupMembersRepository = groupMembersRepository;
        this.accountService = accountService;
        this.groupService = groupService;
    }

    @RequestMapping(value = "/aaa", method = RequestMethod.GET)
    public ModelAndView a() {
        logger.info("a()");
//        List<GroupMembers> groupMembers = groupMembersRepository.getMembersByIdGroup(1);
        GroupMembers groupMembers = new GroupMembers();
        Group group = groupService.get(1);
        groupMembers.setGroup(group);
        Account account = accountService.get(1);
        groupMembers.setMember(account);
        groupMembers.setGroupRole(GroupRole.ADMIN);
        groupMembers.setId(1);
//        groupMembersRepository.save(groupMembers);
//        List<GroupMembers> groupMembers1 = groupMembersRepository.getMembersByGroup(group);
//        List<GroupMembers> groupMembers1 = groupMembersRepository.getGroupMembersByMember_Id(account.getId());
//        logger.info("groupMembers1 - {}", groupMembers1);
        List<GroupMembers> groupMembers1 = groupMembersRepository.getGroupByMember_Id(3);
        logger.info("membera - {}", groupMembers1);
        ModelAndView modelAndView = new ModelAndView("all-accounts");
        return modelAndView;
    }

}

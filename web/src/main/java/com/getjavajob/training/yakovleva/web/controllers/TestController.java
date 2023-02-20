package com.getjavajob.training.yakovleva.web.controllers;

import com.getjavajob.training.yakovleva.Repository.GroupMembersRepository;
import com.getjavajob.training.yakovleva.common.Enum.GroupRole;
import com.getjavajob.training.yakovleva.service.AccountService;
import com.getjavajob.training.yakovleva.service.ApplicationService;
import com.getjavajob.training.yakovleva.service.GroupService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestController {
    private static final Logger logger = LogManager.getLogger(SearchController.class);
    private final AccountService accountService;
    private final GroupService groupService;
    private final GroupMembersRepository groupMembersRepository;
    private final ApplicationService applicationService;

    @Autowired
    public TestController(AccountService accountService, GroupService groupService,
                          GroupMembersRepository groupMembersRepository, ApplicationService applicationService) {
        this.groupMembersRepository = groupMembersRepository;
        this.accountService = accountService;
        this.groupService = groupService;
        this.applicationService = applicationService;
    }

    @RequestMapping(value = "/aaa", method = RequestMethod.GET)
    public ModelAndView a() {
        logger.info("a()");
        GroupRole role = GroupRole.valueOf("MEMBER");
        groupMembersRepository.updateRoleGroupMembers(role, 17);
        ModelAndView modelAndView = new ModelAndView("all-accounts");
        return modelAndView;
    }

    @RequestMapping(value = "/test5", method = RequestMethod.POST)
    public ModelAndView au() {
        logger.info("au()");
        ModelAndView modelAndView = new ModelAndView("all-accounts");
        return modelAndView;
    }

}

package com.getjavajob.training.yakovleva.web;

import com.getjavajob.training.yakovleva.service.*;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServlet;

public abstract class ApplicationContextServlet extends HttpServlet {
    protected AccountService accountService;
    protected ApplicationService applicationService;
    protected GroupService groupService;
    protected MessageService messageService;
    protected PhoneService phoneService;
    protected RelationsService relationsService;

    public void init() {
        ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        accountService = context.getBean(AccountService.class);
        applicationService = context.getBean(ApplicationService.class);
        groupService = context.getBean(GroupService.class);
        messageService = context.getBean(MessageService.class);
        phoneService = context.getBean(PhoneService.class);
        relationsService = context.getBean(RelationsService.class);
    }

}
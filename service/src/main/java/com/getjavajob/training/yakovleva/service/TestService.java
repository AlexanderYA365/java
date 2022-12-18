package com.getjavajob.training.yakovleva.service;

import com.getjavajob.training.yakovleva.common.Account;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestService {
    private static final Logger logger = LogManager.getLogger(AccountService.class);
    private final AccountService accountService;
    private final MessageService messageService;
    private final PhoneService phoneService;

    @Autowired
    public TestService(AccountService accountService, MessageService messageService, PhoneService phoneService) {
        this.accountService = accountService;
        this.messageService = messageService;
        this.phoneService = phoneService;
    }

    @RequestMapping(value = "/aaa", method = RequestMethod.GET)
    public ModelAndView view() {
        ModelAndView modelAndView = new ModelAndView("/all-accounts");
        Account editAccount = accountService.get(4);
        Account account = new Account();
        logger.info("editAccount = {}", editAccount);
        try {
            account.setUsername(editAccount.getUsername());
            account.setPassword(editAccount.getPassword());
            account.setPhones(editAccount.getPhones());
            account.setLastName(editAccount.getLastName());
            account.setName(editAccount.getName());
            account.setSurname(editAccount.getSurname());
            account.setRelations(editAccount.getRelations());
            account.setId(editAccount.getId());
        } catch (Exception ex) {
            logger.error("setDataForm exception = " + ex);
        }
        accountService.update(account);
        return modelAndView;
    }
}
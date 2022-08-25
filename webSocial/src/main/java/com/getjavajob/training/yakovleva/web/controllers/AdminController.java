package com.getjavajob.training.yakovleva.web.controllers;

import com.getjavajob.training.yakovleva.common.Account;
import com.getjavajob.training.yakovleva.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@ControllerAdvice
public class AdminController {
    private AccountService accountService;

    @Autowired
    public AdminController(AccountService accountService) {
        this.accountService = accountService;
    }


    @RequestMapping(value = "/admin-panel", method = RequestMethod.GET)
    public ModelAndView admin() {
        System.out.println("admin");
        List<Account> accounts = accountService.getAllAccountsLimit(0, 100);
        System.out.println(accounts);
        ModelAndView modelAndView = new ModelAndView("admin-panel");
        //modelAndView.addObject("accounts", accounts);
        return modelAndView;
    }

    @RequestMapping(value = "/getAccounts", method = RequestMethod.GET)
    @ResponseBody
    public List<Account> updateTable() {
        System.out.println("updateTable");
        List<Account> accounts = accountService.getAllAccountsLimit(0, 100);
        return accounts;
    }

}

package com.getjavajob.training.yakovleva.web.controllers;

import com.getjavajob.training.yakovleva.dao.Account;
import com.getjavajob.training.yakovleva.service.AccountService;
import com.getjavajob.training.yakovleva.service.GroupService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class SearchController {
    private final AccountService accountService;
    private final GroupService groupService;

    @Autowired
    public SearchController(AccountService accountService, GroupService groupService) {
        this.accountService = accountService;
        this.groupService = groupService;
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ResponseBody
    public List<Account> search(final @RequestParam("filter") String filter) {
        System.out.println("search");
        System.out.println("filter - " + filter);
        List<Account> accounts = accountService.getAllAccounts();
        CollectionUtils.filter(accounts,
                account -> account.getName().contains(filter) || account.getSurname().contains(filter));
        System.out.println("accounts - " + accounts);
        return accounts;
    }

}

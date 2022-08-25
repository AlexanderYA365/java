package com.getjavajob.training.yakovleva.web.controllers;

import com.getjavajob.training.yakovleva.common.Account;
import com.getjavajob.training.yakovleva.common.Group;
import com.getjavajob.training.yakovleva.service.AccountService;
import com.getjavajob.training.yakovleva.service.GroupService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SearchController {
    private static final Logger logger = LogManager.getLogger();
    private final AccountService accountService;
    private final GroupService groupService;

    @Autowired
    public SearchController(AccountService accountService, GroupService groupService) {
        logger.info("SearchController");
        this.accountService = accountService;
        this.groupService = groupService;
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ResponseBody
    public List<SearchResult> search(final @RequestParam("filter") String filter) {
        logger.info("search (filter - {})", filter);
        List<Account> accounts = accountService.getAccountsCriteriaLimit(0, 5, filter);
        List<Group> groups = groupService.getCriteriaLimit(0, 5, filter);
        List<SearchResult> searchResults = new ArrayList<>();
        for (int i = 0; i < accounts.size(); i++) {
            searchResults.add(new SearchResult(accounts.get(i).getId(), accounts.get(i).getName(),
                    accounts.get(i).getSurname(), accounts.get(i).getLastName(), "", false));
        }
        for (int i = 0; i < groups.size(); i++) {
            searchResults.add(new SearchResult(groups.get(i).getGroupId(), "", "", "",
                    groups.get(i).getGroupName(), true));
        }
        System.out.println(searchResults);
        return searchResults;
    }

    class SearchResult {
        public int id;
        public String name;
        public String surname;
        public String lastName;
        public String groupName;
        public boolean isGroup;

        public SearchResult(int id, String name, String surname, String lastName, String groupName, boolean isGroup) {
            this.id = id;
            this.name = name;
            this.surname = surname;
            this.lastName = lastName;
            this.groupName = groupName;
            this.isGroup = isGroup;
        }

        @Override
        public String toString() {
            return "SearchResult{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", surname='" + surname + '\'' +
                    ", lastName='" + lastName + '\'' +
                    ", groupName='" + groupName + '\'' +
                    ", isGroup=" + isGroup +
                    '}';
        }

    }

}
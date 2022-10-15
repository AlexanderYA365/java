package com.getjavajob.training.yakovleva.web.controllers;

import com.getjavajob.training.yakovleva.common.Account;
import com.getjavajob.training.yakovleva.service.AccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@ControllerAdvice
public class AdminController {
    private AccountService accountService;
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    public AdminController(AccountService accountService) {
        this.accountService = accountService;
        logger.info("AdminController");
    }


    @RequestMapping(value = "/admin-panel", method = RequestMethod.GET)
    public ModelAndView admin() {
        logger.info("admin");
        ModelAndView modelAndView = new ModelAndView("admin-panel");
        return modelAndView;
    }

    @RequestMapping(value = "/getAccounts", method = RequestMethod.GET)
    @ResponseBody
    public TableResult updateTable(final @RequestParam("draw") int draw,
                                   final @RequestParam("start") int start,
                                   final @RequestParam("length") int length) {
        logger.info("updateTable(draw = {}, start = {}, length = {})", draw, start, length);
        List<Account> accounts = accountService.getAllAccountsLimit(start, length);
        int max = accountService.getSizeRecords();
        TableResult tableResult = new TableResult(draw, max, max, accounts);
        return tableResult;
    }

    class TableResult {
        private int draw;
        private int recordsTotal;
        private int recordsFiltered;
        private List<Account> data;

        public TableResult(int draw, int recordsTotal, int recordsFiltered, List<Account> data) {
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

        public int getRecordsTotal() {
            return recordsTotal;
        }

        public void setRecordsTotal(int recordsTotal) {
            this.recordsTotal = recordsTotal;
        }

        public int getRecordsFiltered() {
            return recordsFiltered;
        }

        public void setRecordsFiltered(int recordsFiltered) {
            this.recordsFiltered = recordsFiltered;
        }

        public List<Account> getData() {
            return data;
        }

        public void setData(List<Account> data) {
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
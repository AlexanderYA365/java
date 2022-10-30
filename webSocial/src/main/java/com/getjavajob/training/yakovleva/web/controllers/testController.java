package com.getjavajob.training.yakovleva.web.controllers;

import Repository.GroupRepository;
import com.getjavajob.training.yakovleva.common.Account;
import com.getjavajob.training.yakovleva.common.Phone;
import com.getjavajob.training.yakovleva.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Controller
@SessionAttributes({"account", "friend"})
public class testController {
    private AccountService accountService;
    private PhoneService phoneService;
    private GroupService groupService;
    private MessageService messageService;
    private ApplicationService applicationService;
    private GroupRepository groupRepository;

    @Autowired
    public testController(MessageService messageService, ApplicationService applicationService,
                          GroupService groupService, AccountService accountService,
                          PhoneService phoneService, GroupRepository groupRepository) {
        this.groupService = groupService;
        this.accountService = accountService;
        this.phoneService = phoneService;
        this.messageService = messageService;
        this.applicationService = applicationService;
        this.groupRepository = groupRepository;
    }

    @RequestMapping(value = "/createBD", method = RequestMethod.GET)
    public ModelAndView sql() {
        long start = System.currentTimeMillis();
        System.out.println("sql, time start - " + new Date());
        ModelAndView modelAndView = new ModelAndView("all-accounts");
        String[] name = {"Александр", "Николай", "Василий", "Алексей", "Никита", "Иван", "Андрей", "Костя", "Витя", "Егор"};
        String[] surname = {"Любимов", "Фатыхов", "Назаренко", "Комаров", "Воронин"};
        String[] lastName = {"Александров", "Николаевич", "Александрович", "Андреевич", "Викторович"};
        String[] username = {"bobs", "Jonson", "Beap", "Bell", "Bold", "alex", "titer", "beep", "jost", "wolt"};
        List<Account> accounts = generateAccounts(name, surname, lastName, username);
        System.out.println("sql, time start insert into DB - " + new Date());
        accountService.createAccounts(accounts);
        System.out.println("end - "
                + (new SimpleDateFormat("mm:ss:SSS")).format(new Date(System.currentTimeMillis() - start)));
        return modelAndView;
    }

    private List<Account> generateAccounts(String[] name, String[] surname, String[] lastName, String[] username) {
        List<Account> accounts = new ArrayList<>();
        for (int i = 0; i < 1_000_000; i++) {
            Account account = new Account();
            account.setName(name[(int) (Math.random() * 10)]);
            account.setSurname(surname[(int) (Math.random() * 5)]);
            account.setUsername("aq" + username[(int) (Math.random() * 10)] + i);
            account.setPassword("1111");
            account.setLastName(lastName[(int) (Math.random() * 5)]);
            account.setDate(new Date());
            account.setRole(0);
            account.setEmail("email");
            account.setAddressJob("email");
            account.setAddressHome("email");
            account.setAboutMe("email");
            account.setIcq(i);
            List<Phone> phones = new ArrayList<>();
            Phone phone = new Phone();
            phone.setId(11);
            phone.setPhoneType(1);
            phone.setPhoneNumber("11111111");
            account.setPhones(phones);
            account.setDate(new Date());
            accounts.add(account);
        }
        return accounts;
    }

    @RequestMapping(value = "/a", method = RequestMethod.GET)
    public ModelAndView acc() {
        System.out.println("acc");
        ModelAndView modelAndView = new ModelAndView("all-accounts");
        try {
            Account account = new Account();
            account.setId(1);
//            System.out.println(messageService.getUniqueMessages(account));
            System.out.println(accountService.get(1));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public ModelAndView test(HttpServletRequest request,
                             HttpSession session,
                             Model model) {
        System.out.println("test");
        ModelAndView modelAndView = new ModelAndView("all-accounts");
        Account account = new Account();
        account.setId(1);
        account.setUsername("aaaaaaa");
        modelAndView.addObject("account", account);
        List<Account> friends = accountService.getFriendsAccount(1);
        session.setAttribute("account", account);
        model.addAttribute("friends", friends);
        model.addAttribute("account", account);
        System.out.println(modelAndView);
//        for (int i = 0; i < 1_000_000; i++) {
//            accountService.create(createAccount(i));
//        }
        return modelAndView;
    }

    @RequestMapping(value = "/test5", method = RequestMethod.POST)
    public ModelAndView see(@RequestParam("id") int id, @SessionAttribute("account") Account account3) {
        System.out.println("test5");
        System.out.println("account3 - " + account3);
        System.out.println("id - " + id);
        ModelAndView modelAndView = new ModelAndView("all-accounts");
        System.out.println(modelAndView);
        return modelAndView;
    }

    @ModelAttribute("account")
    public Account getAccount() {
        return new Account();
    }

    @RequestMapping(value = "/test4", method = RequestMethod.GET)
    public ModelAndView add() {
        System.out.println("test4");
        ModelAndView modelAndView = new ModelAndView("all-accounts");
//        for (int i = 0; i < 1_000_000; i++) {
//            accountService.create(createAccount(i));
//        }
        System.out.println(modelAndView);
        return modelAndView;
    }

    private Account createAccount(int i) {
        Account account = new Account();
        account.setUsername("0" + i);
        account.setPassword("Qwer123");
        account.setLastName("aaaaaaa");
        account.setName("aaaaaaa");
        account.setSurname("aaaaaaa");
        account.setAboutMe("aaaaaaa");
        account.setAddressHome("aaaaaaa");
        account.setAddressJob("aaaaaaa");
        account.setEmail("aaaaaaa");
        account.setIcq(i);
        account.setRole(0);
        return account;
    }

    @RequestMapping(value = "/test3", method = RequestMethod.POST)
    public @ResponseBody
    ModelAndView test2(@ModelAttribute("account") Account account,
                       HttpServletRequest request,
                       @RequestParam("name") String name,
                       @RequestParam("file") MultipartFile file) {
        System.out.println("test3");
        ModelAndView modelAndView = new ModelAndView("all-accounts");
        account.setId(11);
        account.setUsername("1111");
        account.setPassword("Qwer123");
        account.setLastName("aaaaaaa");
        account.setName("aaaaaaa");
        account.setSurname("aaaaaaa");
        account.setAboutMe("aaaaaaa");
        account.setAddressHome("aaaaaaa");
        account.setAddressJob("aaaaaaa");
        account.setEmail("aaaaaaa");
        account.setIcq(1);
        account.setRole(1);
        List<Phone> phones = new ArrayList<>();
        Phone phone = new Phone();
        phone.setId(11);
        phone.setPhoneType(1);
        phone.setPhoneNumber("11111111");
        account.setPhones(phones);
        account.setDate(new Date());
        System.out.println("name - " + name);
        System.out.println("file - " + file);
        System.out.println("file.getSize() - " + file.getSize());
        System.out.println("file.getName() - " + file.getName());
        modelAndView.addObject("account", account);
        try {
            System.out.println("============tyt=======");
            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    System.out.println("upload yes");
                    System.out.println(bytes.length);
                    System.out.println(name);
                    account.setPhoto(bytes);
                    account.setPhotoFileName(name);
                } catch (Exception e) {
                    System.out.println("Вам не удалось загрузить " + name + " => " + e.getMessage());
                }
            }
            try {
                accountService.update(account);
            } catch (Exception ex) {
                System.out.println("ex - " + ex);
            }
            System.out.println(account);
        } catch (Exception ex) {
            System.out.println("Error whith load photo");
            System.out.println(ex);
        }

        String encodedPhoto = "";
        byte[] photo = account.getPhoto();
        if (photo != null) {

            byte[] encodedPhotoBytes = Base64.getEncoder().encode(account.getPhoto());
            try {
                encodedPhoto = new String(encodedPhotoBytes, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        modelAndView.addObject("encodedPhoto", encodedPhoto);
        modelAndView.addObject("account", account);
        //System.out.println(modelAndView);
        return modelAndView;
    }

    @RequestMapping(value = "/all-accounts", method = RequestMethod.GET)
    public ModelAndView test2(@ModelAttribute("account") Account account) {
        System.out.println("test2");
        ModelAndView modelAndView = new ModelAndView("all-accounts");
        System.out.println("account - " + account);
        System.out.println("modelAndView" + modelAndView);
        return modelAndView;
    }

}

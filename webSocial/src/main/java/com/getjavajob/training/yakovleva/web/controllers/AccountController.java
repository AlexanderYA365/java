package com.getjavajob.training.yakovleva.web.controllers;

import com.getjavajob.training.yakovleva.common.*;
import com.getjavajob.training.yakovleva.service.AccountService;
import com.getjavajob.training.yakovleva.service.MessageService;
import com.getjavajob.training.yakovleva.service.PhoneService;
import com.getjavajob.training.yakovleva.web.controllers.utils.SocialNetworkUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@SessionAttributes("account")
public class AccountController {
    private AccountService accountService;
    private MessageService messageService;
    private PhoneService phoneService;

    @Autowired
    public AccountController(AccountService accountService,
                             MessageService messageService,
                             PhoneService phoneService) {
        this.accountService = accountService;
        this.messageService = messageService;
        this.phoneService = phoneService;
    }

    public AccountController() {

    }

    @RequestMapping(value = "/edit-account-settings", method = RequestMethod.POST)
    public @ResponseBody
    ModelAndView saveSettings(HttpSession session, HttpServletRequest request,
                              @RequestParam("file-name") String name,
                              @RequestParam("file") MultipartFile file) {
        ModelAndView modelAndView = new ModelAndView("/account/my-account");
        try {
            System.out.println("saveSettings");
            System.out.println("name - " + name);
            System.out.println("file - " + file);
            Account editAccount = (Account) session.getAttribute("account");
            Account account = setDataForm(request, editAccount, name, file);
            try {
                accountService.update(account);
                modelAndView.addObject("account", account);
            } catch (Exception ex) {
                System.out.println("editSettings");
            }
            System.out.println("account - " + account);
        } catch (Exception ex) {
            System.out.println("ex - " + ex);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/account-logout", method = RequestMethod.GET)
    public ModelAndView logout(HttpSession session, HttpServletResponse response) {
        System.out.println("registration");
        System.out.println("AccountLogout doGet");
        session.invalidate();
        Cookie cookieUsername = new Cookie("username", null);
        cookieUsername.setMaxAge(0);
        cookieUsername.setPath("/");
        Cookie cookiePassword = new Cookie("password", null);
        cookiePassword.setMaxAge(0);
        cookiePassword.setPath("/");
        Cookie cookieId = new Cookie("id", null);
        cookieId.setMaxAge(0);
        cookieId.setPath("/");
        response.addCookie(cookieUsername);
        response.addCookie(cookiePassword);
        response.addCookie(cookieId);
        return new ModelAndView("redirect:index");
    }

    @RequestMapping(value = "/registration-account", method = RequestMethod.GET)
    public ModelAndView registration() {
        System.out.println("registration");
        return new ModelAndView("account/registration-account");
    }

    @RequestMapping(value = "/registration-account", method = RequestMethod.POST)
    public @ResponseBody
    ModelAndView createNewAccount(HttpSession session, HttpServletRequest request,
                                  @RequestParam("name") String name,
                                  @RequestParam("file") MultipartFile file) {
        System.out.println("createNewAccount");
        System.out.println("name - " + name);
        System.out.println("file - " + file);
        ModelAndView modelAndView = new ModelAndView("redirect:main");
        Account account = new Account();
        account.setUsername(request.getParameter("username"));
        account.setPassword(request.getParameter("password"));
        account.setId(0);
        Account registeredAccount = setDataForm(request, account, name, file);
        if (accountService.create(registeredAccount)) {
            Account accountInBase = accountService.get(registeredAccount.getId());
            session.setAttribute("account", accountInBase);
        } else {
            System.out.println("registration exception");
            modelAndView.setViewName("/account/registration-account");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/edit-account-settings", method = RequestMethod.GET)
    public ModelAndView editSettings(@ModelAttribute("account") Account account) {
        System.out.println("editSettings");
        ModelAndView modelAndView = new ModelAndView("/account/edit-account-settings");
        return modelAndView;
    }

    @RequestMapping(value = "/my-account", method = RequestMethod.GET)
    public ModelAndView settings(@ModelAttribute("account") Account account) {
        System.out.println("settings");
        ModelAndView modelAndView = new ModelAndView("/account/my-account");
        return modelAndView;
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    //TODO RequestParam - заменить на ModelAttribute
    public ModelAndView login(@RequestParam(value = "username", required = false) String username,
                              @RequestParam(value = "password", required = false) String password,
                              @RequestParam(value = "checkbox", required = false) String checkbox,
                              HttpSession session) {
        System.out.println("login, username - " + username + " , password - " + password);
        return userValidation(checkbox, username, password, session);
    }

    @RequestMapping(value = "/main", method = RequestMethod.POST)
    public ModelAndView accountPageSandWallMessage(@RequestParam(value = "NewWallMessage",
            required = false) String newMessage, @RequestParam(value = "replyAccount",
            required = false) String replyAccount, @RequestParam(value = "deleteText",
            required = false) String deleteText, @ModelAttribute("account") Account account) {
        System.out.println("accountPageSandWallMessage");
        ModelAndView modelAndView = new ModelAndView("redirect:main");
        createMessage(account, newMessage, replyAccount, deleteText, messageService);
        return modelAndView;
    }

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public ModelAndView accountPage(@ModelAttribute("account") Account account) {
        System.out.println("public ModelAndView accountPage(){");
        ModelAndView modelAndView = new ModelAndView("main");
        System.out.println("account - " + account);
        SocialNetworkUtils socialNetworkUtils = new SocialNetworkUtils();
        String encodedPhoto = socialNetworkUtils.loadPhoto(account);
        modelAndView.addObject("encodedPhoto", encodedPhoto);
        List<Message> messages = messageService.getWallMassageAccount(account);
        modelAndView.addObject("messages", messages);
        return modelAndView;
    }

    public static void createMessage(Account account,
                                     String newMessage,
                                     String replyAccount,
                                     String deleteText,
                                     MessageService messageService) {
        try {
            if (newMessage != null) {
                System.out.println("create new message");
                Message message = new Message();
                message.setReceiverId(account.getId());
                message.setSenderId(account.getId());
                message.setMessage(newMessage);
                message.setMessageType(0);
                System.out.println("message - " + message);
                messageService.createMassage(message);
            } else if (replyAccount != null) {
                System.out.println("select replyAccount");
                System.out.println(Integer.parseInt(replyAccount));
            } else if (deleteText != null) {
                System.out.println("select deleteAccount");
                int messageId = Integer.parseInt(deleteText);
                System.out.println("messageId - " + messageId);
                messageService.delete(messageId);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private ModelAndView userValidation(String cookies,
                                        String username,
                                        String password,
                                        HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        if (username == null || password == null) {
            System.out.println("redirect to index");
            modelAndView.setViewName("redirect:index");
        } else {
            Account account = accountService.getAccount(username, password);
            System.out.println("registeredAccount - " + account);
            if (account.getId() != 0) {
                modelAndView.addObject("account", account);
                session.setAttribute("account", account);
                modelAndView.setViewName("redirect:main");
            } else {//переделать, направлять
                System.out.println("userValidation -> else");
                int errorLogin = 1;
                modelAndView.addObject("errorLogin", errorLogin);
                modelAndView.setViewName("/");
            }
        }
        System.out.println(modelAndView);
        return modelAndView;
    }

    private Account setDataForm(HttpServletRequest request, Account editAccount, String name, MultipartFile file) {
        System.out.println("setDataForm");
        Account account = new Account();
        try {
            account.setUsername(editAccount.getUsername());
            account.setPassword(editAccount.getPassword());
            account.setId(editAccount.getId());
            account.setName(request.getParameter("name"));
            account.setSurname(request.getParameter("surname"));
            account.setLastName(request.getParameter("lastName"));
            account.setDate(getDateFromForm(request));
            account.setIcq(Integer.parseInt(request.getParameter("icq")));
            account.setAddressHome(request.getParameter("addressHome"));
            account.setAddressJob(request.getParameter("addressJob"));
            account.setEmail(request.getParameter("email"));
            account.setAboutMe(request.getParameter("aboutMe"));
            account.setRole(getRoleFromForm(request));
            account.setPhones(getPhonesFromForm(request, editAccount));
        } catch (Exception ex) {
            System.out.println("setDataForm exception - " + ex);
        }
        try {
            if (!file.isEmpty()) {
                byte[] bytes = file.getBytes();
                account.setPhoto(bytes);
                account.setPhotoFileName(name);
            }
        } catch (Exception ex) {
            System.out.println("failed load photo");
            System.out.println(ex);
        }
        return account;
    }

    private Date getDateFromForm(HttpServletRequest request) {
        System.out.println("getDateFromForm");
        Date date = new Date();
        try {
            String dateFromForm = request.getParameter("date");
            System.out.println("dateFromForm - " + dateFromForm);
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            date = format.parse(dateFromForm);
            System.out.println("date - " + date);
        } catch (ParseException e) {
            System.out.println("getDateFromForm exception - " + e);
        }
        return date;
    }

    private int getRoleFromForm(HttpServletRequest request) {
        System.out.println("getRoleFromForm");
        String type = request.getParameter("typeRole");
        return Role.valueOf(type).getStatus();
    }

    private List<Phone> getPhonesFromForm(HttpServletRequest request, Account account) {
        System.out.println("setPhone");
        List<Phone> phones = new ArrayList<>();
        if (account.getPhones().size() != 0) {
            String[] requestPhone = request.getParameterValues("phone");
            String[] requestPhoneType = request.getParameterValues("typePhone");
            String[] requestPhoneId = request.getParameterValues("phoneId");
            for (int i = 0; i < requestPhone.length; i++) {
                Phone phone = new Phone();
                phone.setPhoneNumber(requestPhone[i]);
                phone.setPhoneType(PhoneType.valueOf(requestPhoneType[i]).getStatus());
                phone.setAccountId(account.getId());
                phone.setId(Integer.parseInt(requestPhoneId[i]));
                System.out.println("update phone - " + phone);
                phones.add(phone);
            }
            if (account.getPhones().size() != phones.size()) {
                removePhone(phones, account);
            }
        }
        try {
            String newPhone = request.getParameter("newPhone");
            String typeNewPhone = request.getParameter("typeNewPhone");
            System.out.println("newPhone - " + newPhone);
            System.out.println("typeNewPhone - " + typeNewPhone);
            if (newPhone != null) {
                System.out.println("create new phone from - accountEditSettings");
                Phone phone = new Phone();
                phone.setPhoneNumber(newPhone);
                phone.setPhoneType(PhoneType.valueOf(typeNewPhone).getStatus());
                phone.setAccountId(account.getId());
                phoneService.create(phone);
                phones.add(phone);
            }
        } catch (Exception ex) {
            System.out.println("no new phone");
        }
        System.out.println("phones - " + phones);
        return phones;
    }

    private void removePhone(List<Phone> phones, Account account) {
        List<Phone> phonesDelete = new ArrayList<>();
        phonesDelete.addAll(account.getPhones());
        phonesDelete.removeAll(phones);
        System.out.println("phonesDelete - " + phonesDelete);
        for (Phone phone : phonesDelete) {
            phoneService.delete(phone);
        }
    }

}
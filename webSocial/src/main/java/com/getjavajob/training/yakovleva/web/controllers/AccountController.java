package com.getjavajob.training.yakovleva.web.controllers;

import com.getjavajob.training.yakovleva.common.*;
import com.getjavajob.training.yakovleva.service.AccountService;
import com.getjavajob.training.yakovleva.service.MessageService;
import com.getjavajob.training.yakovleva.service.PhoneService;
import com.getjavajob.training.yakovleva.web.controllers.utils.SocialNetworkUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
@ControllerAdvice
@SessionAttributes("account")
public class AccountController {
    private static final Logger logger = LogManager.getLogger();
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
        logger.info("AccountController");
    }

    public static void createMessage(Account account,
                                     String newMessage,
                                     String replyAccount,
                                     String deleteText,
                                     MessageService messageService) {
        logger.info("AccountController.createMessage()");
        logger.debug("AccountController.createMessage(account = {}, newMessage ={}, replyAccount ={}," +
                "deleteText = {}, messageService = {})", account, newMessage, replyAccount, deleteText, messageService);
        try {
            if (newMessage != null) {
                logger.info("create new message");
                Message message = new Message();
                message.setReceiverId(account.getId());
                message.setSenderId(account.getId());
                message.setMessage(newMessage);
                message.setMessageType(0);
                logger.debug("message = {}", message);
                messageService.createMassage(message);
            } else if (replyAccount != null) {
                logger.info("select replyAccount");
                logger.info(Integer.parseInt(replyAccount));
            } else if (deleteText != null) {
                logger.info("select deleteAccount");
                int messageId = Integer.parseInt(deleteText);
                logger.debug("messageId = {} ", messageId);
                messageService.delete(messageId);
            }
        } catch (Exception e) {
            logger.error(e);
        }
    }

    @RequestMapping(value = "/edit-account-settings", method = RequestMethod.POST)
    public @ResponseBody
    ModelAndView saveSettings(HttpSession session, HttpServletRequest request,
                              @RequestParam("file-name") String name,
                              @RequestParam("file") MultipartFile file) {
        logger.info("AccountController.saveSettings()");
        ModelAndView modelAndView = new ModelAndView("/account/my-account");
        try {
            logger.debug("name = {}", name);
            logger.debug("file = {}", file);
            Account editAccount = (Account) session.getAttribute("account");
            Account account = setDataForm(request, editAccount, name, file);
            try {
                accountService.update(account);
                modelAndView.addObject("account", account);
            } catch (Exception ex) {
                logger.error(ex);
            }
            logger.debug("account = {}", account);
        } catch (Exception ex) {
            logger.error(ex);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/account-logout", method = RequestMethod.GET)
    public ModelAndView logout(HttpSession session, HttpServletResponse response) {
        logger.info("logout");
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
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "/registration-account", method = RequestMethod.GET)
    public ModelAndView registration() {
        logger.info("registration");
        return new ModelAndView("account/registration-account");
    }

    @RequestMapping(value = "/registration-account", method = RequestMethod.POST)
    public @ResponseBody
    ModelAndView createNewAccount(HttpSession session, HttpServletRequest request,
                                  @RequestParam("name") String name,
                                  @RequestParam("file") MultipartFile file) {
        logger.info("createNewAccount");
        logger.debug("name = {}", name);
        logger.debug("file = {}", file);
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
            logger.error("registration exception");
            modelAndView.setViewName("/account/registration-account");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/edit-account-settings", method = RequestMethod.GET)
    public ModelAndView editSettings(@ModelAttribute("account") Account account) {
        logger.info("editSettings");
        return new ModelAndView("/account/edit-account-settings");
    }

    @RequestMapping(value = "/my-account", method = RequestMethod.GET)
    public ModelAndView settings(@ModelAttribute("account") Account account) {
        logger.info("settings");
        return new ModelAndView("/account/my-account");
    }

    @ModelAttribute("account")
    public Account getAccount() {
        return new Account();
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    //TODO RequestParam - заменить на ModelAttribute
    public ModelAndView login(@ModelAttribute("account") final Account account,
//                              final BindingResult result, final ModelMap model,
                              @RequestParam(value = "checkbox", required = false) String checkbox) {
        logger.info("login, username = {}, password = {} ", account.getUsername(), account.getPassword());
        return userValidation(checkbox, account);
    }

    @RequestMapping(value = "/main", method = RequestMethod.POST)
    public ModelAndView accountPageSandWallMessage(@RequestParam(value = "NewWallMessage",
            required = false) String newMessage, @RequestParam(value = "replyAccount",
            required = false) String replyAccount, @RequestParam(value = "deleteText",
            required = false) String deleteText, @ModelAttribute("account") Account account) {
        logger.info("accountPageSandWallMessage");
        ModelAndView modelAndView = new ModelAndView("redirect:main");
        createMessage(account, newMessage, replyAccount, deleteText, messageService);
        return modelAndView;
    }

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public ModelAndView accountPage(@ModelAttribute("account") Account account) {
        logger.info("public ModelAndView accountPage()");
        ModelAndView modelAndView = new ModelAndView("main");
        logger.debug("account = {}", account);
        SocialNetworkUtils socialNetworkUtils = new SocialNetworkUtils();
        String encodedPhoto = socialNetworkUtils.loadPhoto(account);
        modelAndView.addObject("encodedPhoto", encodedPhoto);
        List<Message> messages = messageService.getWallMassageAccount(account);
        modelAndView.addObject("messages", messages);
        return modelAndView;
    }

    private ModelAndView userValidation(String cookies,
                                        Account account) {
        logger.info("ModelAndView userValidation(cookies = {}," +
                        "account.username = {}, account.password = {})",
                cookies, account.getUsername(), account.getPassword());
        ModelAndView modelAndView = new ModelAndView();
        if (account.getUsername() == null || account.getPassword() == null) {
            logger.info("userValidation, redirect to index");
            modelAndView.setViewName("redirect:index");
        } else {
            Account registeredAccount = accountService.getAccount(account.getUsername(), account.getPassword());
            System.out.println("registeredAccount - " + registeredAccount);
            if (registeredAccount.getId() != 0) {
                modelAndView.addObject("account", registeredAccount);
                modelAndView.setViewName("redirect:main");
            } else {
                logger.info("userValidation -> else");
                int errorLogin = 1;
                modelAndView.addObject("errorLogin", errorLogin);
                modelAndView.setViewName("redirect:index");
            }
        }
        return modelAndView;
    }

    private Account setDataForm(HttpServletRequest request, Account editAccount, String name, MultipartFile file) {
        logger.info("setDataForm");
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
            logger.error("setDataForm exception = {}", ex);
        }
        try {
            if (!file.isEmpty()) {
                byte[] bytes = file.getBytes();
                account.setPhoto(bytes);
                account.setPhotoFileName(name);
            }
        } catch (Exception ex) {
            logger.error("failed load photo");
            logger.error(ex);
        }
        return account;
    }

    private Date getDateFromForm(HttpServletRequest request) {
        logger.info("getDateFromForm");
        Date date = new Date();
        try {
            String dateFromForm = request.getParameter("date");
            logger.debug("dateFromForm = {%d}", dateFromForm);
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            date = format.parse(dateFromForm);
            logger.debug("date = {%d}", date);
        } catch (ParseException e) {
            logger.error("getDateFromForm exception = {}", e);
        }
        return date;
    }

    private int getRoleFromForm(HttpServletRequest request) {
        logger.info("getRoleFromForm");
        String type = request.getParameter("typeRole");
        return Role.valueOf(type).getStatus();
    }

    private List<Phone> getPhonesFromForm(HttpServletRequest request, Account account) {
        logger.info("setPhone");
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
                logger.debug("update phone - " + phone);
                phones.add(phone);
            }
            if (account.getPhones().size() != phones.size()) {
                removePhone(phones, account);
            }
        }
        try {
            String newPhone = request.getParameter("newPhone");
            String typeNewPhone = request.getParameter("typeNewPhone");
            logger.debug("newPhone = {}", newPhone);
            logger.debug("typeNewPhone = {}", typeNewPhone);
            if (newPhone != null) {
                logger.info("create new phone from - accountEditSettings");
                Phone phone = new Phone();
                phone.setPhoneNumber(newPhone);
                phone.setPhoneType(PhoneType.valueOf(typeNewPhone).getStatus());
                phone.setAccountId(account.getId());
                phoneService.create(phone);
                phones.add(phone);
            }
        } catch (Exception ex) {
            logger.error("no new phone = {}", ex);
        }
        logger.info("phones = {}", phones);
        return phones;
    }

    private void removePhone(List<Phone> phones, Account account) {
        logger.info("removePhone");
        List<Phone> phonesDelete = new ArrayList<>();
        phonesDelete.addAll(account.getPhones());
        phonesDelete.removeAll(phones);
        logger.debug("phonesDelete = {}", phonesDelete);
        for (Phone phone : phonesDelete) {
            phoneService.delete(phone);
        }
    }

}
package com.getjavajob.training.yakovleva.web.controllers;

import com.getjavajob.training.yakovleva.common.Account;
import com.getjavajob.training.yakovleva.common.Enum.PhoneType;
import com.getjavajob.training.yakovleva.common.Enum.Role;
import com.getjavajob.training.yakovleva.common.Message;
import com.getjavajob.training.yakovleva.common.Phone;
import com.getjavajob.training.yakovleva.service.AccountService;
import com.getjavajob.training.yakovleva.service.MessageService;
import com.getjavajob.training.yakovleva.service.PhoneService;
import com.getjavajob.training.yakovleva.web.controllers.utils.SocialNetworkUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Controller
@ControllerAdvice
@SessionAttributes("account")
public class AccountController {
    private static final Logger logger = LogManager.getLogger(AccountController.class);
    private static final int BUFFER_SIZE = 4096;
    private final AccountService accountService;
    private final MessageService messageService;
    private final PhoneService phoneService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AccountController(AccountService accountService,
                             MessageService messageService,
                             PhoneService phoneService,
                             PasswordEncoder passwordEncoder) {
        this.accountService = accountService;
        this.messageService = messageService;
        this.phoneService = phoneService;
        this.passwordEncoder = passwordEncoder;
        logger.info("AccountController");
    }

    @RequestMapping(value = "/edit-account-settings", method = RequestMethod.POST)
    public @ResponseBody
    ModelAndView saveSettings(HttpSession session, HttpServletRequest request,
                              @RequestParam("file-name") String fileName,
                              @RequestParam("file") MultipartFile file) {
        logger.info("AccountController.saveSettings(file name - {}, file - {})", fileName, file);
        ModelAndView modelAndView = new ModelAndView("/account/my-account");
        try {
            Account editAccount = (Account) session.getAttribute("account");
            Account account = setDataForm(request, editAccount, fileName, file);
            try {
                accountService.update(account);
                modelAndView.addObject("account", account);
            } catch (Exception ex) {
                logger.error(ex);
            }
            logger.info("account = {}", account);
        } catch (Exception ex) {
            logger.error(ex);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/registration-account", method = RequestMethod.GET)
    public ModelAndView registration() {
        logger.info("registration()");
        return new ModelAndView("account/registration-account");
    }

    @RequestMapping(value = "/registration-account", method = RequestMethod.POST)
    public @ResponseBody
    ModelAndView
    createNewAccount(@ModelAttribute("account") Account account, @RequestParam("file") MultipartFile file,
                     @ModelAttribute("phone") String phone, @ModelAttribute("typePhone") String typePhone) {
        logger.info("createNewAccount(account - {}, file - {})", account, file);
        try {
            if (!file.isEmpty()) {
                byte[] bytes = file.getBytes();
                account.setPhoto(bytes);
                account.setPhotoFileName(file.getName());
            }
        } catch (Exception ex) {
            logger.error("failed load photo");
            logger.error(ex);
        }
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.setRole(Role.ROLE_USER);
        SocialNetworkUtils utils = new SocialNetworkUtils();
        List<Phone> phones = utils.convertPhones(phone, typePhone, account);
        logger.info("account - {}", account);
        ModelAndView modelAndView = new ModelAndView("redirect:/");
        try {
            if (accountService.create(account, phones)) {
                Account accountFromBase = accountService.getByUsername(account.getUsername());
                logger.info("accountFromBase - {}", accountFromBase);
                modelAndView.addObject("account", accountFromBase);
            } else {
                logger.error("registration exception");
                modelAndView.setViewName("/account/registration-account");
            }
        } catch (Exception ex) {
            logger.info("ex - {}", ex);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/edit-account-settings", method = RequestMethod.GET)
    public ModelAndView editSettings(@ModelAttribute("account") Account account) {
        logger.info("editSettings(account - {})", account);
        return new ModelAndView("/account/edit-account-settings");
    }

    @RequestMapping(value = "/my-account", method = RequestMethod.GET)
    public ModelAndView settings(@ModelAttribute("account") Account account) {
        logger.info("settings(account - {})", account);
        return new ModelAndView("/account/my-account");
    }

    @RequestMapping(value = "/update-account-settings", method = RequestMethod.POST)
    public ModelAndView update(@RequestParam("uploadXml") MultipartFile file) {
        logger.info("update");
        Account account = new Account();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Account.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            File fileToUpdate = new File(Objects.requireNonNull(file.getOriginalFilename()));
            file.transferTo(fileToUpdate);
            account = (Account) unmarshaller.unmarshal(fileToUpdate);
            logger.info("account = {}", account);
            accountService.update(account);
        } catch (JAXBException e) {
            logger.error("Error with JAXB instance. Exception: " + e);
        } catch (IOException e) {
            logger.error("Error with transfer to file. Exception: " + e);
        }
        ModelAndView modelAndView = new ModelAndView("/account/my-account");
        modelAndView.addObject("account", account);
        return modelAndView;
    }

    @RequestMapping(value = "/save-account-settings", method = RequestMethod.GET)
    public ModelAndView save(@ModelAttribute("account") Account account, HttpServletResponse response) {
        logger.info("save = {}", account);
        File file = new File("account.xml");
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Account.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "utf-8");
            marshaller.marshal(account, file);
        } catch (JAXBException e) {
            logger.error("Error with JAXB instance. Exception: " + e);
        }
        try (FileInputStream inputStream = new FileInputStream(file)) {
            response.setContentType("application/xml");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
            response.setContentType("application/xml");
            response.setContentLength((int) file.length());
            byte[] buffer = new byte[BUFFER_SIZE];
            OutputStream outStream = response.getOutputStream();
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
            inputStream.close();
            outStream.close();
        } catch (FileNotFoundException e) {
            logger.error("FileNotFoundException: " + e);
        } catch (IOException e) {
            logger.error("IOException: " + e);
        }
        return new ModelAndView("/account/my-account");
    }

    @ModelAttribute("account")
    public Account account() {
        return new Account();
    }

    @RequestMapping(value = "/sendWallMessage", method = RequestMethod.POST)
    public ModelAndView accountPageSendWallMessage(@RequestParam(value = "NewWallMessage",
            required = false) String newMessage, @RequestParam(value = "replyAccount",
            required = false) String replyAccount, @RequestParam(value = "deleteText",
            required = false) String deleteText, @ModelAttribute("account") Account account) {
        logger.info("sendWallMessage:");
        logger.info("newMessage - {}", newMessage);
        logger.info("replyAccount - {}", replyAccount);
        logger.info("deleteText - {}", deleteText);
        logger.info("account - {}", account);
        ModelAndView modelAndView = new ModelAndView("redirect:main");
        createMessage(account, newMessage, replyAccount, deleteText);
        return modelAndView;
    }

    public void createMessage(Account account,
                              String newMessage,
                              String replyAccount,
                              String deleteText) {
        logger.info("AccountController.createMessage(account = {}, newMessage ={}, replyAccount ={}," +
                "deleteText = {})", account, newMessage, replyAccount, deleteText);
        try {
            if (newMessage != null) {
                logger.info("create new message");
                Message message = new Message();
                message.setReceiverId(account.getId());
                message.setSenderId(account.getId());
                message.setMessage(newMessage);
                message.setPublicationDate(new Date());
                message.setMessageType(0);
                logger.info("message = {}", message);
                messageService.createMassage(message);
            } else if (replyAccount != null) {
                logger.info("select replyAccount");
                logger.info(Integer.parseInt(replyAccount));
            } else if (deleteText != null) {
                logger.info("select deleteAccount");
                int messageId = Integer.parseInt(deleteText);
                logger.info("messageId = {} ", messageId);
                messageService.delete(messageId);
            }
        } catch (Exception e) {
            logger.error(e);
        }
    }

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public ModelAndView main(Principal principal) {
        logger.info("public main(principal = {})", principal);
        Account registeredAccount = accountService.getByUsername(principal.getName());
        ModelAndView modelAndView = new ModelAndView("main");
        logger.info("account = {}", registeredAccount);
        SocialNetworkUtils socialNetworkUtils = new SocialNetworkUtils();
        String encodedPhoto = socialNetworkUtils.loadPhoto(registeredAccount);
        modelAndView.addObject("encodedPhoto", encodedPhoto);
        List<Message> messages = messageService.getWallMassageAccount(registeredAccount);
        modelAndView.addObject("messages", messages);
        modelAndView.addObject("account", registeredAccount);
        return modelAndView;
    }

    @RequestMapping(value = "/main", method = RequestMethod.POST)
    public ModelAndView accountPage(@ModelAttribute("account") Account account) {
        logger.info("public accountPage(account = {})", account);
        return new ModelAndView("main");
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
            account.setRelations(editAccount.getRelations());
            account.setPhones(getPhonesFromForm(request, editAccount));
        } catch (Exception ex) {
            logger.error("setDataForm exception = " + ex);
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
            if (dateFromForm != null) {
                logger.info("dateFromForm = {}", dateFromForm);
                SimpleDateFormat format = new SimpleDateFormat("yyyyy-MM-dd");
                date = format.parse(dateFromForm);
                logger.info("date = {}", date);
            }
        } catch (ParseException e) {
            logger.error("getDateFromForm exception = " + e);
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
                logger.info("update phone - {}", phone);
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
            logger.error("no new phone = " + ex);
        }
        logger.info("phones = {}", phones);
        return phones;
    }

    private void removePhone(List<Phone> phones, Account account) {
        logger.info("removePhone");
        List<Phone> phonesDelete = new ArrayList<>(account.getPhones());
        phonesDelete.removeAll(phones);
        logger.debug("phonesDelete = {}", phonesDelete);
        for (Phone phone : phonesDelete) {
            phoneService.delete(phone);
        }
    }

}
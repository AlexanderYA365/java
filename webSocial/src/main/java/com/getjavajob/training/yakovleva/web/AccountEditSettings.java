package com.getjavajob.training.yakovleva.web;

import com.getjavajob.training.yakovleva.common.Account;
import com.getjavajob.training.yakovleva.common.Phone;
import com.getjavajob.training.yakovleva.common.PhoneType;
import com.getjavajob.training.yakovleva.common.Role;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//@WebServlet("/edit-account-settings")
public class AccountEditSettings extends ApplicationContextServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        System.out.println("AccountEditSettings doGet");
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        Account accountFromDB = accountService.get(account.getId());
        request.setAttribute("account", accountFromDB);
        request.getRequestDispatcher("jsp/account/edit-account-settings.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("AccountEditSettings doPost");
        HttpSession session = request.getSession();
        Account editAccount = (Account) session.getAttribute("account");
        System.out.println("editAccount - " + editAccount);
        Account account = setDataForm(request, editAccount);
        try {
            accountService.update(account);
        } catch (Exception e) {
            System.out.println(e);//send redirect
        }
        session.setAttribute("account", account);
        request.getRequestDispatcher("jsp/account/my-account.jsp").forward(request, response);
    }

    private Account setDataForm(HttpServletRequest request, Account editAccount) {
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
            System.out.println(account);
        } catch (Exception ex) {
            System.out.println("setDataForm exception - " + ex);
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
        System.out.println( "phonesDelete - " + phonesDelete);
        for (Phone phone : phonesDelete){
            phoneService.delete(phone);
        }
    }

}
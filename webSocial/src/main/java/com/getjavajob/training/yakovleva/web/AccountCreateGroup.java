package com.getjavajob.training.yakovleva.web;

import com.getjavajob.training.yakovleva.dao.Account;
import com.getjavajob.training.yakovleva.dao.Group;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/CreateGroup")
public class AccountCreateGroup extends ApplicationContextServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        System.out.println("AccountCreateGroup doGet");
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/CreateGroup.jsp");
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("AccountCreateGroup doPost");
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        List<Group> groups = groupService.readGroups();
        try {
            if (createGroup(groups, request.getParameter("name"))) {
                Group group = new Group();
                group.setIdAdministrator(account.getId());
                group.setIdAccount(account.getId());
                group.setGroupName(request.getParameter("name"));
                group.setLogo(request.getParameter("logo"));
                System.out.println("AccountCreateGroup doPost - " + group);
                groupService.createAccountGroups(group);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/AccountGroup.jsp");
                requestDispatcher.forward(request, response);
            }
        } catch (Exception e) {
            System.out.println("Error");//TODO redirect
        }
    }

    private boolean createGroup(List<Group> groups, String groupName) {
        boolean flag = false;
        System.out.println("createGroup, group name - " + groupName);
        for (Group group : groups) {
            if (!group.getGroupName().equals(groupName)) {
                flag = true;
            }
        }
        System.out.println("createGroup flag - " + flag);
        return flag;
    }

}
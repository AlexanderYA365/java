package com.getjavajob.training.yakovleva.web;

import com.getjavajob.training.yakovleva.dao.Account;
import com.getjavajob.training.yakovleva.dao.Group;
import com.getjavajob.training.yakovleva.service.GroupService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/AccountFindGroup")
public class AccountFindGroup extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("AccountFindGroup doGet");
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/AccountFindGroup.jsp");
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String groupId = request.getParameter("groupId");
        System.out.println("groupId - " + groupId);
        System.out.println("AccountFindGroup doPost");
        String groupName = request.getParameter("GroupName");
        GroupService service = new GroupService();
        try {
            if (groupId == null) {
                try {
                    List<Group> groups = service.getGroupName(groupName);
                    System.out.println(groups);
                    request.setAttribute("findGroups", groups);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (groupId != null) {
                HttpSession session = request.getSession();
                Account account = (Account) session.getAttribute("account");
                Group addGroup = service.readGroupID(Integer.parseInt(groupId));//TODO
                System.out.println("com.getjavajob.training.yakovleva.dao.Account - " + account);
                System.out.println("addGroup - " + addGroup);
                service.insertAccountGroup(addGroup, account.getId());
            }
        } catch (Exception e) {
            System.out.println(e);//send redirect
        }
        doGet(request, response);
    }

}

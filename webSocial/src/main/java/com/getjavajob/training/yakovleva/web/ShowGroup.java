package com.getjavajob.training.yakovleva.web;

import com.getjavajob.training.yakovleva.dao.Account;
import com.getjavajob.training.yakovleva.dao.Application;
import com.getjavajob.training.yakovleva.dao.Group;
import com.getjavajob.training.yakovleva.service.ApplicationService;
import com.getjavajob.training.yakovleva.service.GroupService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/ShowGroup")
public class ShowGroup extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("ShowGroup doGet");
        GroupService service = new GroupService();
        ApplicationService applicationService = new ApplicationService();
        try {
            Group group = service.readGroupID(Integer.parseInt(req.getParameter("id")));
            HttpSession session = req.getSession();
            Account account = (Account) session.getAttribute("account");
            Application application = applicationService.readGroupAccount(group, account.getId());
            System.out.println("com.getjavajob.training.yakovleva.dao.Application - " + application);
            if (application != null) {
                req.setAttribute("application", application);
                int newUserGroup = application.getStatus();
                req.setAttribute("newUserGroup", newUserGroup);
            }
            System.out.println(group);
            req.setAttribute("group", group);
        } catch (Exception e) {
            System.out.println(e);//send redirect
        }
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("jsp/showGroup.jsp");
        requestDispatcher.forward(req, response);
    }

}
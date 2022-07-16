package com.getjavajob.training.yakovleva.web.utils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(filterName = "AuthorizationFilter")
public class AuthorizationFilter implements Filter {

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain)
            throws ServletException, IOException {
//        System.out.println("AuthorizationFilter.doFilter");
//        HttpServletRequest request = (HttpServletRequest) req;
//        HttpServletResponse response = (HttpServletResponse) resp;
//        HttpSession session = request.getSession(false);
//        Cookie[] cookies = request.getCookies();
//        Map<String, Cookie> cookieMap = new HashMap<>();
//        if (cookies != null) {
//            for (Cookie item : cookies) {
//                cookieMap.put(item.getName(), item);
//            }
//        }
//        String uri = request.getRequestURI();
//        boolean isLogged = session == null || session.getAttribute("username") == null;
//        boolean isCookieExists = cookieMap.get("username") != null;
//        System.out.println("isLogged - " + isLogged);
//        System.out.println("isCookieExists - " + isCookieExists);
//        if (uri.endsWith("/main.jsp") || uri.endsWith("login") || uri.endsWith("RegistrationAccount")) {
//            System.out.println("1");
//            filterChain.doFilter(request, response);
//        } else if (isLogged && isCookieExists) {
//            System.out.println("2");
//            RequestDispatcher dispatcher = request.getRequestDispatcher("/login?cookie=true");
//            dispatcher.forward(request, response);
//        } else if (isLogged || uri.endsWith("/index.jsp")) {
//            System.out.println("3");
//            RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
//            dispatcher.forward(request, response);
//        } else {
//            System.out.println("4");
//            filterChain.doFilter(request, response);
//        }
        filterChain.doFilter(req, resp);

    }

    public void init(FilterConfig config) throws ServletException {
    }

}

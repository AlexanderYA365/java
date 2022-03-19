package utils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebFilter(filterName = "AuthorizationFilter")
public class AuthorizationFilter implements Filter {

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain)
            throws ServletException, IOException {
        System.out.println("AuthorizationFilter.doFilter");
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession(false);
        Cookie[] cookies = request.getCookies();
        Map<String, Cookie> cookieMap = new HashMap<>();
        if (cookies != null) {
            for (Cookie item : cookies) {
                cookieMap.put(item.getName(), item);
            }
        }
        boolean isLogged = session != null && session.getAttribute("username") != null;
        boolean isCookieExists = cookieMap.get("username") != null;
        System.out.println("0");
        System.out.println("session - " + session);
        System.out.println("isLogged - " + isLogged + " isCookieExists - " + isCookieExists);
        if (request.getServletPath().equals("/index.jsp") || request.getServletPath().equals("/RegistrationAccount.jsp")) {
            filterChain.doFilter(request, response);
        } else if (!isLogged && !isCookieExists) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp?cookie=true");
            dispatcher.forward(request, response);
        } else {
            filterChain.doFilter(request, response);
        }
    }

    public void init(FilterConfig config) throws ServletException {
    }

}

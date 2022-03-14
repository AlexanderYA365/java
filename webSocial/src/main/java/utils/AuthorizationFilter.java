package utils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebFilter(filterName = "AuthorizationFilter")
public class AuthorizationFilter implements Filter {
    private FilterConfig filterConfig;
    private static List<String> pages;

    public AuthorizationFilter()
    {
        if (pages == null) {
            pages = new ArrayList<String>();
        }
    }

    public void destroy() {
        filterConfig = null;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (filterConfig.getInitParameter("active").equalsIgnoreCase("true")) {
            HttpServletRequest req = (HttpServletRequest)request;
            String[] list = req.getRequestURI().split("/");
            String page = null;
            if (list[list.length - 1].indexOf(".jsp") > 0) {
                page = list[list.length - 1];
            }
            if (page != null) {
                if (pages.contains("index.jsp") || pages.contains("RegistrationAccount.jsp")) {
                    filterChain.doFilter(request, response);
                } else {
                    ServletContext ctx = filterConfig.getServletContext();
                    RequestDispatcher dispatcher = ctx.getRequestDispatcher("/index.jsp");
                    dispatcher.forward(request, response);
                }
                if (!pages.contains(page)) {
                    pages.add(page);
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    public void init(FilterConfig config) throws ServletException {
        filterConfig = config;
    }

}

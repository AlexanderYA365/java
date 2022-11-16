package com.getjavajob.training.yakovleva.web.security;

import org.springframework.stereotype.Component;

@Component
public class JAuthenticationSuccessHandler
//        implements
//        AuthenticationSuccessHandler
{
//    private AccountService accountService;

//    @Autowired
//    public JAuthenticationSuccessHandler(AccountService accountService) {
//        this.accountService = accountService;
//    }

//    public JAuthenticationSuccessHandler() {
//    }

//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {

//    }

//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//        HttpSession session = request.getSession(false);
////        User user = (User) authentication.getPrincipal();
////        Account currentAccount = new Account();
////        String email = user.getUsername();
////        try {
////            System.out.println("SocNetAuthenticationSuccessHandler accountService " + accountService);
////            currentAccount = accountService.getByUsername(email);
////            System.out.println("currentAccount " + currentAccount);
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////        int id = currentAccount.getId();
////        session.setAttribute("email", email);
////        session.setAttribute("id", id);
////        session.setAttribute("userName", currentAccount.getName() + " " + currentAccount.getLastName());
////        session.setAttribute("role", currentAccount.getRole());
////        response.setStatus(HttpServletResponse.SC_OK);
////        response.sendRedirect("/main?id=" + id);
//    }

}
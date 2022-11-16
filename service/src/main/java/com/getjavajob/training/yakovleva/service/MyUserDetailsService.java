package com.getjavajob.training.yakovleva.service;

import com.getjavajob.training.yakovleva.common.Account;
import com.getjavajob.training.yakovleva.common.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
    private static final Logger logger = LogManager.getLogger(MyUserDetailsService.class);
    private AccountService accountService;
    private BCryptPasswordEncoder encoder;

    @Autowired
    public MyUserDetailsService(AccountService accountService, BCryptPasswordEncoder encoder) {
        this.accountService = accountService;
        this.encoder = encoder;
        logger.info("UserDetailService");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("loadUserByUsername() - " + username);
        Account account;
        try {
            account = accountService.getByUsername(username);
        } catch (Exception e) {
            throw new UsernameNotFoundException("Username " + username + " not found.");
        }
        return User.builder()
                .username(account.getUsername())
                .password(encoder.encode(account.getPassword()))
                .roles(Role.values()[account.getRole()].toString())
                .build();
    }

}
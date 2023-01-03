package com.getjavajob.training.yakovleva.web.security;

import com.getjavajob.training.yakovleva.service.DetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig {
    private final DetailsService detailsService;

    @Autowired
    public SecurityConfig(DetailsService detailsService) {
        this.detailsService = detailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/account-message/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/account-write-message").hasAnyRole("USER", "ADMIN")
                .antMatchers("/account-friends").hasAnyRole("USER", "ADMIN")
                .antMatchers("/account-find-group").hasAnyRole("USER", "ADMIN")
                .antMatchers("/edit-account-settings").hasAnyRole("USER", "ADMIN")
                .antMatchers("/my-account").hasAnyRole("USER", "ADMIN")
                .antMatchers("/add-friend-account").hasAnyRole("USER", "ADMIN")
                .antMatchers("/show-friend").hasAnyRole("USER", "ADMIN")
                .antMatchers("/create-group").hasAnyRole("USER", "ADMIN")
                .antMatchers("/show-group").hasAnyRole("USER", "ADMIN")
                .antMatchers("/account-group").hasAnyRole("USER", "ADMIN")
                .antMatchers("/**").permitAll()
                .antMatchers("/aaa").permitAll()
                .antMatchers("/registration-account").permitAll()
                .and()
                .formLogin()
                .loginPage("/index.jsp").permitAll()
                .failureUrl("/index.jsp?error=true")
                .successForwardUrl("/main").permitAll()
                .and()
                .logout()
                .logoutUrl("/account-logout")
                .logoutSuccessUrl("/").permitAll()
                .deleteCookies("JSESSIONID")
                .and()
                .rememberMe().key("remember-me")
                .tokenValiditySeconds(86400)
                .and()
                .csrf().disable()
                .sessionManagement().
                sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                .maximumSessions(5);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(detailsService);
        return daoAuthenticationProvider;
    }

}
package com.getjavajob.training.yakovleva.web.security;

import com.getjavajob.training.yakovleva.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final MyUserDetailsService myUserDetailsService;

    @Autowired
    public SecurityConfig(MyUserDetailsService myUserDetailsService) {
        this.myUserDetailsService = myUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        System.out.println("securityFilterChain");
        http
                .authorizeHttpRequests((requests) -> requests
                        .antMatchers("/").permitAll()
                        .antMatchers("/main").hasAnyRole("USER", "ADMIN")
                        .antMatchers("/admin/*").hasRole("ADMIN")
                        .antMatchers("/account-message").hasAnyRole("USER", "ADMIN")
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
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/index.jsp")
                        .failureUrl("/index.jsp?error=true")
                        .successForwardUrl("/main")
                        .permitAll()
                )
                .userDetailsService(myUserDetailsService)
                .logout((logout) -> logout
                        .logoutUrl("/account-logout")
                        .permitAll())
                .httpBasic().and()
                .csrf().disable();
        return http.build();
    }

}
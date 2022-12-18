package com.getjavajob.training.yakovleva.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.servlet.ServletContext;

@Configuration
@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.getjavajob.training.yakovleva.dao")
public class WebappApplication extends SpringBootServletInitializer {

    @Override
    public void onStartup(ServletContext servletContext) {
        servletContext.setInitParameter("com.sun.faces.expressionFactory", "org.apache.el.ExpressionFactoryImpl");
    }

    public static void main(String[] args) {
        SpringApplication.run(WebappApplication.class, args);
    }

}
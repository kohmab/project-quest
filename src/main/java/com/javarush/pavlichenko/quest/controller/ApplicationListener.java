package com.javarush.pavlichenko.quest.controller;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ApplicationListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Started");
        System.out.println(sce.getServletContext().getServerInfo());

    }
}

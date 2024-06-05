package com.javarush.pavlichenko.quest.controller;

import com.javarush.pavlichenko.quest.repository.QuestRepository;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;

@WebListener
public class ApplicationListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            QuestRepository.init();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

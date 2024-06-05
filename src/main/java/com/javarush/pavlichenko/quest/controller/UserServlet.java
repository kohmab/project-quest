package com.javarush.pavlichenko.quest.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Objects.isNull;

@WebServlet(name="user",value="/user")
public class UserServlet extends HttpServlet {

    private final static String ANONIMOUS_USER_NAME = "Аноним";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("userName");

        if (isNull(userName) || userName.trim().isEmpty())
            userName = ANONIMOUS_USER_NAME;

        req.getSession().setAttribute("userName",userName);

        try {
            resp.sendRedirect("quest");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}

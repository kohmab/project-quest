package com.javarush.pavlichenko.quest.controller;

import com.javarush.pavlichenko.quest.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static java.util.Objects.isNull;

@WebServlet(name = "newUser", value = "/newUser")
public class NewUserServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        if (isNull(session.getAttribute("user"))) {
            String userName = req.getParameter("userName");

            if (isNull(userName) || userName.trim().isEmpty())
                userName = getServletContext().getInitParameter("anonymousUserName") ;

            req.getSession().setAttribute("user", new User(userName));
        }

        req.getRequestDispatcher("quest").forward(req, resp);

    }

}

package com.javarush.pavlichenko.quest.controller;

import com.javarush.pavlichenko.quest.repository.QuestRepository;
import com.javarush.pavlichenko.quest.service.QuestService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@WebServlet(name = "quest", value = "/quest")
public class QuestServlet extends HttpServlet {

    private final QuestService questService = new QuestService(QuestRepository.getInstance());


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        String currentEdgeKey = (String) session.getAttribute("currentEdgeKey");
        if (isNull(currentEdgeKey)) {
            currentEdgeKey = questService.getStartEdgeKey();
        }

        String receivedEdgeKey = req.getParameter("edgeKey");

        Set<String> possibleKeys = questService.getNextKeysAndActions(currentEdgeKey).keySet();
        if (nonNull(receivedEdgeKey) && possibleKeys.contains(receivedEdgeKey)){
            currentEdgeKey = receivedEdgeKey;
        }

        session.setAttribute("currentEdgeKey",currentEdgeKey);
        session.setAttribute("isWin", questService.checkWin(currentEdgeKey));
        session.setAttribute("isLoose", questService.checkLoose(currentEdgeKey));

        req.setAttribute("question", questService.getQuestion(currentEdgeKey));
        req.setAttribute("nextKeysAndActions", questService.getNextKeysAndActions(currentEdgeKey));

        req.getRequestDispatcher("game.jsp").forward(req, resp);



        req.getRequestDispatcher("quest").forward(req,resp);
    }
}

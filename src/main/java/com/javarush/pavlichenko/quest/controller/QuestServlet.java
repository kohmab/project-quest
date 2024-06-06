package com.javarush.pavlichenko.quest.controller;

import com.javarush.pavlichenko.quest.entity.User;
import com.javarush.pavlichenko.quest.entity.enums.UserState;
import com.javarush.pavlichenko.quest.repository.QuestRepository;
import com.javarush.pavlichenko.quest.service.QuestService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Set;

import static java.util.Objects.isNull;

@WebServlet(name = "questServlet", value = "/quest")
public class QuestServlet extends HttpServlet {

    private final QuestService questService = new QuestService(QuestRepository.getInstance());


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        if (user.getState() == UserState.DEFEAT || user.getState() == UserState.WIN) {
            String startEdgeKey = questService.getStartEdgeKey();
            user.beginQuest(startEdgeKey);
        }

        String currentEdgeKey = user.getEdgeKey();
        String receivedEdgeKey = req.getParameter("edgeKey");

        Set<String> possibleKeys = questService.getNextKeysAndActions(currentEdgeKey).keySet();
        if (possibleKeys.contains(receivedEdgeKey)) {
            currentEdgeKey = receivedEdgeKey;
        }

        req.setAttribute("question", questService.getQuestion(currentEdgeKey));
        req.setAttribute("nextKeysAndActions", questService.getNextKeysAndActions(currentEdgeKey));


        if (questService.checkWin(currentEdgeKey)) {
            user.win();
        } else if (questService.checkDefeat(currentEdgeKey)) {
            user.defeat();
        } else {
            user.setEdgeKey(currentEdgeKey);
        }

        req.getRequestDispatcher("game.jsp").forward(req, resp);
    }
}

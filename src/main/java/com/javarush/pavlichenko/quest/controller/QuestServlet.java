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

@WebServlet(name = "questServlet", value = "/quest")
public class QuestServlet extends HttpServlet {

    private final QuestService questService = new QuestService(QuestRepository.getInstance());


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        if (user.getState() == UserState.DEFEAT || user.getState() == UserState.WIN) {
            String startNodeKey = questService.getStartNodeKey();
            user.beginQuest(startNodeKey);
        }

        String currentNodeKey = user.getNodeKey();
        String receivedNodeKey = req.getParameter("nodeKey");

        Set<String> possibleKeys = questService.getNextKeysAndActions(currentNodeKey).keySet();
        if (possibleKeys.contains(receivedNodeKey)) {
            currentNodeKey = receivedNodeKey;
        }

        req.setAttribute("consequence", questService.getQuestion(currentNodeKey));
        req.setAttribute("nextKeysAndActions", questService.getNextKeysAndActions(currentNodeKey));


        if (questService.checkWin(currentNodeKey)) {
            user.win();
        } else if (questService.checkDefeat(currentNodeKey)) {
            user.defeat();
        } else {
            user.setNodeKey(currentNodeKey);
        }

        req.getRequestDispatcher("game.jsp").forward(req, resp);
    }
}

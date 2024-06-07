package com.javarush.pavlichenko.quest.controller;

import com.javarush.pavlichenko.quest.entity.User;
import com.javarush.pavlichenko.quest.entity.enums.UserState;
import com.javarush.pavlichenko.quest.service.QuestService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.util.ReflectionUtils;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.lang.reflect.Field;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class QuestServletTest {

    private final QuestServlet questServlet = new QuestServlet();
    private final QuestService questService = Mockito.mock(QuestService.class);

    private final HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
    private final HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
    private final HttpSession session = Mockito.mock(HttpSession.class);
    private final User user = Mockito.mock(User.class);
    private final RequestDispatcher requestDispatcher = Mockito.mock(RequestDispatcher.class);
    {
        Mockito.doReturn(session).when(request).getSession();
        Mockito.doReturn(requestDispatcher).when(request).getRequestDispatcher("game.jsp");
        Mockito.doReturn(user).when(session).getAttribute("user");
        Mockito.doReturn("someKey").when(user).getNodeKey();

        Field field = ReflectionUtils
                .findFields(QuestServlet.class, f -> f.getName().equals("questService"),
                        ReflectionUtils.HierarchyTraversalMode.TOP_DOWN)
                .get(0);

        field.setAccessible(true);
        try {
            field.set(questServlet, questService);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        Mockito.doReturn("someNode").when(questService).getStartNodeKey();
    }

    @Test
    void beginOnWin() throws ServletException, IOException {
        Mockito.doReturn(UserState.WIN).when(user).getState();
        questServlet.doPost(request, response);
        Mockito.verify(user, times(1)).beginQuest(anyString());

    }

    @Test
    void beginOnDefeat() throws ServletException, IOException {
        Mockito.doReturn(UserState.DEFEAT).when(user).getState();
        questServlet.doPost(request, response);
        Mockito.verify(user, times(1)).beginQuest(anyString());
    }

    @Test
    void winNodeActionsWithUser() throws ServletException, IOException {
        Mockito.doReturn(true).when(questService).checkWin(anyString());
        questServlet.doPost(request,response);
        Mockito.verify(user,times(1)).win();
        Mockito.verify(user,never()).setNodeKey(anyString());
        Mockito.verify(user,never()).defeat();
    }

    @Test
    void defeatNodeActionsWithUser() throws ServletException, IOException {
        Mockito.doReturn(true).when(questService).checkDefeat(anyString());
        questServlet.doPost(request,response);
        Mockito.verify(user,times(1)).defeat();
        Mockito.verify(user,never()).setNodeKey(anyString());
        Mockito.verify(user,never()).win();
    }

    @Test
    void intermediateNodeActionsWithUser() throws ServletException, IOException {
        Mockito.doReturn(false).when(questService).checkWin(anyString());
        Mockito.doReturn(false).when(questService).checkDefeat(anyString());
        questServlet.doPost(request,response);
        Mockito.verify(user).setNodeKey(anyString());
        Mockito.verify(user,never()).win();
        Mockito.verify(user,never()).defeat();
    }


    @Test
    void getUserFromSession() throws ServletException, IOException {
        questServlet.doPost(request,response);
        Mockito.verify(session).getAttribute("user");

    }

    @Test
    void redirectToView() throws ServletException, IOException {
        questServlet.doPost(request,response);
        Mockito.verify(request).getRequestDispatcher("game.jsp");
        Mockito.verify(requestDispatcher).forward(request,response);
    }

}
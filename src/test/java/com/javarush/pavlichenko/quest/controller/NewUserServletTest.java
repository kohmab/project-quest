package com.javarush.pavlichenko.quest.controller;

import com.javarush.pavlichenko.quest.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.platform.commons.util.ReflectionUtils;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class NewUserServletTest {

    private final NewUserServlet newUserServlet = new NewUserServlet();

    private final HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
    private final HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
    private final ServletConfig servletConfig = Mockito.mock(ServletConfig.class);
    private final ServletContext servletContext = Mockito.mock(ServletContext.class);
    private final HttpSession session = Mockito.mock(HttpSession.class);
    private final User user = Mockito.mock(User.class);
    private final RequestDispatcher requestDispatcher = Mockito.mock(RequestDispatcher.class);

    {
        Mockito.doReturn(session).when(request).getSession();
        Mockito.doReturn(requestDispatcher).when(request).getRequestDispatcher("quest");
        Mockito.doReturn(servletContext).when(servletConfig).getServletContext();
        Mockito.doReturn("anonymousUserName")
                .when(servletContext).getInitParameter("anonymousUserName");


        Field field = ReflectionUtils
                .findFields(GenericServlet.class, f -> f.getName().equals("config"),
                        ReflectionUtils.HierarchyTraversalMode.TOP_DOWN)
                .get(0);
        field.setAccessible(true);
        try {
            field.set(newUserServlet, servletConfig);
        } catch (Exception ignore) {
        }
    }

    @Test
    void tryGetUserFromSession() throws ServletException, IOException {
        newUserServlet.doPost(request, response);
        Mockito.verify(session).getAttribute("user");
    }

    @Test
    void createIfNoUser() throws ServletException, IOException {
        Mockito.doReturn(null).when(session).getAttribute("user");
        try (MockedConstruction<User> userMockedConstruction = Mockito.mockConstruction(User.class)) {
            newUserServlet.doPost(request, response);
            assertEquals(1, userMockedConstruction.constructed().size());
        }
    }

    @Test
    void doNotCreateIfPresent() throws ServletException, IOException {
        Mockito.doReturn(user).when(session).getAttribute("user");
        try (MockedConstruction<User> userMockedConstruction = Mockito.mockConstruction(User.class)) {
            newUserServlet.doPost(request, response);
            assertEquals(0, userMockedConstruction.constructed().size());
        }
    }

    @Test
    void anonymousUserCreationWhenNullNameInRequest() throws ServletException, IOException {
        Mockito.doReturn(null).when(request).getParameter("userName");

        try (MockedConstruction<User> userMockedConstruction =
                     Mockito.mockConstruction(
                             User.class,
                             (mock, context) -> assertEquals("anonymousUserName", context.arguments().get(0)))) {
            newUserServlet.doPost(request, response);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\n", "\t", "\t\n", "\t\t\t   \t\t\n"})
    void anonymousUserCreationWhenBlankNameInRequest(String val) throws ServletException, IOException {
        Mockito.doReturn(val).when(request).getParameter("userName");
        try (MockedConstruction<User> userMockedConstruction =
                     Mockito.mockConstruction(
                             User.class,
                             (mock, context) -> assertEquals("anonymousUserName", context.arguments().get(0)))) {
            newUserServlet.doPost(request, response);
        }
    }

}
package com.javarush.pavlichenko.quest.entity;

import com.javarush.pavlichenko.quest.entity.enums.UserState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    User user;

    @BeforeEach
    void newUser() {
        this.user = new User("testUserName");
    }

    @Test
    void gamesCount() {
        int gamesCount = user.getGamesPlayed();
        user.win();
        int newGamesCount = user.getGamesPlayed();
        assertEquals(gamesCount + 1, newGamesCount);

        gamesCount = newGamesCount;
        user.defeat();
        newGamesCount = user.getGamesPlayed();
        assertEquals(gamesCount + 1, newGamesCount);

        user.beginQuest("someKey");
        newGamesCount = user.getGamesPlayed();
        assertEquals(gamesCount + 1, newGamesCount);
    }

    @Test
    void winCount() {
        int winCount = user.getGamesPlayed();
        user.win();
        int newWinCount = user.getGamesPlayed();
        assertEquals(winCount + 1, newWinCount);

        winCount = newWinCount;
        user.defeat();
        newWinCount = user.getGamesPlayed();
        assertEquals(winCount, newWinCount);

        user.beginQuest("someKey");
        newWinCount = user.getGamesPlayed();
        assertEquals(winCount, newWinCount);
    }


    @Test
    void state() {
        user.beginQuest("someKey");
        assertEquals(UserState.PLAY, user.getState());
        user.win();
        assertEquals(UserState.WIN, user.getState());
        user.defeat();
        assertEquals(UserState.DEFEAT, user.getState());
    }

    @Test
    void startEdge() {
        user.beginQuest("someEdge");
        assertEquals("someEdge", user.getNodeKey());
    }


}
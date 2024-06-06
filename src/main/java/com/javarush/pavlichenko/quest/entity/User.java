package com.javarush.pavlichenko.quest.entity;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class User {

    private final String name;
    private int gamesPlayed = 0;
    private int gamesWon = 0;

    public void win() {
        gamesPlayed++;
        gamesWon++;
    }

    public void loose() {
        gamesPlayed++;
    }


}

package com.javarush.pavlichenko.quest.entity;


import com.javarush.pavlichenko.quest.entity.enums.UserState;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
public class User {

    private final String name;

    @Setter
    private String nodeKey;

    private UserState state = UserState.WIN; // we are all born winners
    private int gamesPlayed = 0;
    private int gamesWon = 0;


    public void win() {
        gamesPlayed++;
        gamesWon++;
        state = UserState.WIN;
    }

    public void defeat() {
        gamesPlayed++;
        state = UserState.DEFEAT;
    }

    public void beginQuest(String startNodeKey){
        nodeKey = startNodeKey;
        state = UserState.PLAY;
    }


}

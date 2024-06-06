package com.javarush.pavlichenko.quest.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserState {
    PLAY("play"), WIN("win"), DEFEAT("defeat");

    private final String name;
}

package com.javarush.pavlichenko.quest.entity;

import com.javarush.pavlichenko.quest.entity.enums.EdgeType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
public class QuestTreeEdge {
    private String key;
    private String action;
    private String question;
    private EdgeType type;
    private final List<String> nextEdgeKeys = new ArrayList<>();
}

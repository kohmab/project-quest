package com.javarush.pavlichenko.quest.entity;

import com.javarush.pavlichenko.quest.entity.enums.EdgeType;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class QuestTreeEdge {
    private final int id;
    private final String action;
    private final String question;
    private final EdgeType type;
    private final List<Integer> nextEdgeIds = new ArrayList<>();
}

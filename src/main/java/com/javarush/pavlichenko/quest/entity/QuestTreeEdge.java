package com.javarush.pavlichenko.quest.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class QuestTreeEdge {
    private final int id;
    private final String answer;
    private final String question;
    private final List<QuestTreeEdge> nextEdges = new ArrayList<>();
}

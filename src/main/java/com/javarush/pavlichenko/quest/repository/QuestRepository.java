package com.javarush.pavlichenko.quest.repository;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.pavlichenko.quest.entity.QuestTreeEdge;
import com.javarush.pavlichenko.quest.entity.enums.EdgeType;
import lombok.Getter;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Objects.nonNull;

public class QuestRepository {
    private static final String QUEST_FILE = "quest_tree_edges.json";

    @Getter
    private static QuestRepository instance;

    private Map<Integer, QuestTreeEdge> questEdges;

    private QuestRepository() {
    }

    public static void init() throws IOException {
        if (nonNull(instance))
            return;

        ObjectMapper mapper = new ObjectMapper(new JsonFactory());
        List<QuestTreeEdge> edjesList;
        try (InputStream ioStream = QuestRepository.class.getClassLoader().getResourceAsStream(QUEST_FILE)) {
            edjesList = mapper.readValue(ioStream, new TypeReference<List<QuestTreeEdge>>() {
            });
        }

        instance = new QuestRepository();
        instance.questEdges = new HashMap<>();
        for (QuestTreeEdge e : edjesList) {
            instance.questEdges.put(e.getId(), e);
        }
    }

    public QuestTreeEdge getEdgeById(int id){
        return instance.questEdges.get(id);
    }

    public Set<Integer> getAllIds(){
        return instance.questEdges.keySet();
    }

}

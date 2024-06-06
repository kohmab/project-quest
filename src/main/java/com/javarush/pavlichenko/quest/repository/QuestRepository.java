package com.javarush.pavlichenko.quest.repository;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.pavlichenko.quest.entity.QuestTreeEdge;
import lombok.Getter;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Objects.nonNull;

public class QuestRepository {
    private static final String QUEST_FILE = "json/quest_tree_edges.json";

    @Getter
    private static QuestRepository instance;

    private Map<String, QuestTreeEdge> questEdges;

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
            instance.questEdges.put(e.getKey(), e);
        }
    }

    public QuestTreeEdge getEdgeByKey(String key){
        return instance.questEdges.get(key);
    }

    public Set<String> getAllKeys(){
        return instance.questEdges.keySet();
    }

}

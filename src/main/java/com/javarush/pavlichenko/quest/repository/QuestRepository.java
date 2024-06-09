package com.javarush.pavlichenko.quest.repository;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.pavlichenko.quest.entity.QuestNode;
import lombok.Getter;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Objects.nonNull;

public class QuestRepository {
    private static final String QUEST_FILE = "json/quest-tree.json"; //TODO move

    @Getter
    private static QuestRepository instance;

    private Map<String, QuestNode> questNodes;

    private QuestRepository() {
    }

    // TODO make thread safe
    public static void init() throws IOException {
        if (nonNull(instance))
            return;

        ObjectMapper mapper = new ObjectMapper(new JsonFactory());
        instance = new QuestRepository();

        try (InputStream ioStream = QuestRepository.class.getClassLoader().getResourceAsStream(QUEST_FILE)) {
            instance.questNodes = mapper.readValue(ioStream, new TypeReference<Map<String,QuestNode>>() {
            });
        }

    }

    public QuestNode getNodeByKey(String key){
        return instance.questNodes.get(key);
    }

    public Set<String> getAllKeys(){
        return instance.questNodes.keySet();
    }

}

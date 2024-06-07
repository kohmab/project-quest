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

    public static void init() throws IOException {
        if (nonNull(instance))
            return;

        ObjectMapper mapper = new ObjectMapper(new JsonFactory());
        List<QuestNode> NodesList;
        try (InputStream ioStream = QuestRepository.class.getClassLoader().getResourceAsStream(QUEST_FILE)) {
            NodesList = mapper.readValue(ioStream, new TypeReference<List<QuestNode>>() {
            });
        }

        instance = new QuestRepository();
        instance.questNodes = new HashMap<>();
        for (QuestNode node : NodesList) {
            instance.questNodes.put(node.getKey(), node);
        }
    }

    public QuestNode getNodeByKey(String key){
        return instance.questNodes.get(key);
    }

    public Set<String> getAllKeys(){
        return instance.questNodes.keySet();
    }

}

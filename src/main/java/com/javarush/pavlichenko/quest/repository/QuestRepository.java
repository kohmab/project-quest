package com.javarush.pavlichenko.quest.repository;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.pavlichenko.quest.entity.QuestNode;

import java.io.*;
import java.util.Map;
import java.util.Set;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class QuestRepository {
    private static final String QUEST_FILE = "json/quest-tree.json"; //TODO move

    private static volatile QuestRepository instance;

    private Map<String, QuestNode> questNodes;

    private QuestRepository() {
    }

    public static QuestRepository getInstance() {
        try {
            init();
        } catch (IOException exception){
            throw new RuntimeException(exception);
        }

        return instance;
    }

    public static void init() throws IOException {
        if (nonNull(instance))
            return;

        ObjectMapper mapper = new ObjectMapper(new JsonFactory());

        synchronized (QuestRepository.class){
            if (isNull(instance)){
                QuestRepository localInstance = new QuestRepository();
                try (InputStream ioStream = QuestRepository.class.getClassLoader().getResourceAsStream(QUEST_FILE)) {
                    localInstance.questNodes = mapper.readValue(ioStream, new TypeReference<Map<String,QuestNode>>() {
                    });
                }
                instance = localInstance;
            }
        }

    }

    public QuestNode getNodeByKey(String key){
        return instance.questNodes.get(key);
    }

    public Set<String> getAllKeys(){
        return instance.questNodes.keySet();
    }

}

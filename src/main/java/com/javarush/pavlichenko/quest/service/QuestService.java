package com.javarush.pavlichenko.quest.service;

import com.javarush.pavlichenko.quest.entity.QuestNode;
import com.javarush.pavlichenko.quest.entity.enums.QuestNodeType;
import com.javarush.pavlichenko.quest.repository.QuestRepository;
import org.cache2k.Cache;
import org.cache2k.Cache2kBuilder;

import java.util.*;


import static java.util.Objects.isNull;

public class QuestService {
    private final QuestRepository repository;
    private final Cache<String,QuestNode> cache;

    private String startNodeKey;

    public QuestService(QuestRepository repository) {
        this.repository = repository;
        cache = new Cache2kBuilder<String,QuestNode>(){}
                .name("questNodesCache")
                .eternal(true)
                .entryCapacity(100L)
                .build();
    }

    public String getStartNodeKey(){
        if (isNull(startNodeKey)){
            determineStartNodeKey();
        }
        return startNodeKey;
    }

    public String getConsequence(String key){
        return getNodeByKey(key).getConsequence();
    }

    public QuestNodeType getType(String key){
        return getNodeByKey(key).getType();
    }

    public boolean checkWin(String key) { return getType(key) == QuestNodeType.WIN;}

    public boolean checkDefeat(String key) { return getType(key) == QuestNodeType.DEFEAT;}

    /**
     * @param currentKey key of current node
     * @return map containing pairs of node keys and corresponding actions
     *         which follows the edge with given key
     */
    public Map<String,String> getNextKeysAndActions(String currentKey){
        QuestNode currentNode = getNodeByKey(currentKey);
        Map<String,String> result = new HashMap<>();
        for (String nextNodeKey: currentNode.getNextNodeKeys()){
            QuestNode next = getNodeByKey(nextNodeKey);
            String nextNodeAction = next.getAction();
            result.put(nextNodeKey,nextNodeAction);
        }
        return result;
    }

    private QuestNode getNodeByKey(String key){
        if (!cache.containsKey(key)){
            cache.put(key,repository.getNodeByKey(key));
        }
            return cache.get(key);
    }

    private void determineStartNodeKey(){
        for (String k: repository.getAllKeys()){
            if (repository.getNodeByKey(k).getType() == QuestNodeType.START){
                startNodeKey = k;
                return;
            }
        }
    }


}

package com.javarush.pavlichenko.quest.service;

import com.javarush.pavlichenko.quest.entity.QuestNode;
import com.javarush.pavlichenko.quest.entity.enums.QuestNodeType;
import com.javarush.pavlichenko.quest.repository.QuestRepository;
import lombok.RequiredArgsConstructor;

import java.util.*;


import static java.util.Objects.isNull;

@RequiredArgsConstructor
public class QuestService {
    private final QuestRepository repository;
    private String startNodeKey;

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
            String key = next.getKey();
            String action = next.getAction();
            result.put(key,action);
        }
        return result;
    }

    private QuestNode getNodeByKey(String key){
        return repository.getNodeByKey(key);
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

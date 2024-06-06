package com.javarush.pavlichenko.quest.service;

import com.javarush.pavlichenko.quest.entity.QuestTreeEdge;
import com.javarush.pavlichenko.quest.entity.enums.EdgeType;
import com.javarush.pavlichenko.quest.repository.QuestRepository;
import lombok.RequiredArgsConstructor;

import java.util.*;


import static java.util.Objects.isNull;

@RequiredArgsConstructor
public class QuestService {
    private final QuestRepository repository;
    private static String startEdgeKey;

    public String getStartEdgeKey(){
        if (isNull(startEdgeKey)){
            determineStartEdgeKey();
        }
        return startEdgeKey;
    }

    public String getQuestion(String key){
        return getEdgeByKey(key).getQuestion();
    }

    public EdgeType getType(String key){
        return getEdgeByKey(key).getType();
    }

    public boolean checkWin(String key) { return getType(key) == EdgeType.WIN;}

    public boolean checkDefeat(String key) { return getType(key) == EdgeType.DEFEAT;}

    /**
     * @param currentKey key of current edge
     * @return map containing pairs of edge keys and corresponding actions
     *         which follows the edge with given key
     */
    public Map<String,String> getNextKeysAndActions(String currentKey){
        QuestTreeEdge currentEdge = getEdgeByKey(currentKey);
        Map<String,String> result = new HashMap<>();
        for (String nextKey: currentEdge.getNextEdgeKeys()){
            QuestTreeEdge nextEdge = getEdgeByKey(nextKey);
            String key = nextEdge.getKey();
            String action = nextEdge.getAction();
            result.put(key,action);
        }
        return result;
    }

    private QuestTreeEdge getEdgeByKey(String key){
        return repository.getEdgeByKey(key);
    }

    private void determineStartEdgeKey(){
        for (String k: repository.getAllKeys()){
            if (repository.getEdgeByKey(k).getType() == EdgeType.START){
                startEdgeKey = k;
                return;
            }
        }
    }


}

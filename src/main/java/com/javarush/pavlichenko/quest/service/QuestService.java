package com.javarush.pavlichenko.quest.service;

import com.javarush.pavlichenko.quest.entity.QuestTreeEdge;
import com.javarush.pavlichenko.quest.entity.enums.EdgeType;
import com.javarush.pavlichenko.quest.repository.QuestRepository;
import lombok.RequiredArgsConstructor;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;


import static java.util.Objects.isNull;

@RequiredArgsConstructor
public class QuestService {
    private final QuestRepository repository;
    private static String startEdgeKey;

    public String getStartEdgeKey(){
        if (isNull(startEdgeKey)){
            determineStartEdgeId();
        }
        return startEdgeKey;
    }

    public String getQuestion(String key){
        return getEdgeByKey(key).getQuestion();
    }

    public EdgeType getType(String key){
        return getEdgeByKey(key).getType();
    }

    /**
     * @param currentKey id of current edge
     * @return list containing pairs of edge keys and corresponding actions
     *         which follows the edge with given key
     */
    public List<Map.Entry<String,String>> getNextIdsAndActions(String currentKey){
        QuestTreeEdge currentEdge = getEdgeByKey(currentKey);
        List<Map.Entry<String,String>> result = new ArrayList<>();
        for (String nextKey: currentEdge.getNextEdgeKeys()){
            QuestTreeEdge nextEdge = getEdgeByKey(nextKey);
            String key = nextEdge.getKey();
            String action = nextEdge.getAction();
            result.add(new AbstractMap.SimpleImmutableEntry<>(key, action));
        }
        return result;
    }

    private QuestTreeEdge getEdgeByKey(String key){
        return repository.getEdgeByKey(key);
    }

    private void determineStartEdgeId(){
        for (String k: repository.getAllKeys()){
            if (repository.getEdgeByKey(k).getType() == EdgeType.START){
                startEdgeKey = k;
                return;
            }
        }
    }


}

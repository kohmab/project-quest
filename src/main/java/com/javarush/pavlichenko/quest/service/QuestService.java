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
    private Integer startEdgeId;

    public QuestTreeEdge getStartEdge(){
        if (isNull(startEdgeId)){
            determineStartEdgeId();
        }
        return repository.getEdgeById(startEdgeId);
    }

    private  QuestTreeEdge getEdgeById(int id){
        return repository.getEdgeById(id);
    }

    public String getQuestion(int id){
        return getEdgeById(id).getQuestion();
    }

    public EdgeType getType(int id){
        return getEdgeById(id).getType();
    }


    /**
     * @param currentId id of current edge
     * @return list containing pairs of edge ids and corresponding actions
     *         which follows the edge with given id
     */
    public List<Map.Entry<Integer,String>> getNextIdsAndActions(int currentId){
        QuestTreeEdge currentEdge = getEdgeById(currentId);
        List<Map.Entry<Integer,String>> result = new ArrayList<>();
        for (int nextId: currentEdge.getNextEdgeIds()){
            QuestTreeEdge nextEdge = getEdgeById(nextId);
            final Integer id = nextEdge.getId();
            final String action = nextEdge.getAction();
            result.add(new AbstractMap.SimpleImmutableEntry<>(id, action));
        }
        return result;
    }


    private void determineStartEdgeId(){
        for (Integer i: repository.getAllIds()){
            if (repository.getEdgeById(i).getType() == EdgeType.START){
                startEdgeId = i;
                return;
            }
        }
    }


}

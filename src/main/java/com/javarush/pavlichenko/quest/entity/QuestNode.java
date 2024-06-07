package com.javarush.pavlichenko.quest.entity;

import com.javarush.pavlichenko.quest.entity.enums.QuestNodeType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class QuestNode {
    private String action;
    private String consequence;
    private QuestNodeType type;
    private List<String> nextNodeKeys;
}

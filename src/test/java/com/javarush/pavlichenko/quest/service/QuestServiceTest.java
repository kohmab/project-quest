package com.javarush.pavlichenko.quest.service;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.pavlichenko.quest.entity.QuestNode;
import com.javarush.pavlichenko.quest.entity.enums.QuestNodeType;
import com.javarush.pavlichenko.quest.repository.QuestRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.objenesis.ObjenesisException;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.util.Objects.isNull;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class QuestServiceTest {

    // TEST REPOSITORY
    private final static String TEST_QUEST_FILE = "json/test-quest-tree.json";
    private static QuestRepository questRepository = Mockito.mock(QuestRepository.class);
    private static Map<String, QuestNode> testQuestMap;
    private static QuestService questService;

    @BeforeAll
    static void initTestRepository() {
        ObjectMapper mapper = new ObjectMapper(new JsonFactory());
        try (InputStream ioStream = QuestServiceTest.class.getClassLoader().getResourceAsStream(TEST_QUEST_FILE)) {
            testQuestMap = mapper.readValue(ioStream, new TypeReference<Map<String, QuestNode>>() {
            });
        } catch (IOException exception) {
            fail("Could not load TEST_QUEST_FILE");
        }

        for (Map.Entry<String, QuestNode> entry : testQuestMap.entrySet()) {
            Mockito.doReturn(entry.getValue()).when(questRepository).getNodeByKey(entry.getKey());
        }
        Mockito.doReturn(testQuestMap.keySet()).when(questRepository).getAllKeys();
        questService = new QuestService(questRepository);
    }

    @Test
    void getStartNodeKey() {
        String startNodeKey = questService.getStartNodeKey();
        assertSame(testQuestMap.get("start"), testQuestMap.get(startNodeKey));
    }


    @Test
    void checkWin() {
        assertTrue(questService.checkWin("win"));
        assertFalse(questService.checkWin("node1"));
        assertFalse(questService.checkWin("defeat"));
    }

    @Test
    void checkDefeat() {
        assertFalse(questService.checkDefeat("win"));
        assertFalse(questService.checkDefeat("node1"));
        assertTrue(questService.checkDefeat("defeat"));
    }

    @Test
    void getNextKeysAndActions() {
        for (String testKey : testQuestMap.keySet()) {
            Map<String, String> expectedMap = new HashMap<>();
            List<String> nextNodeKeys = testQuestMap.get(testKey).getNextNodeKeys();
            for (String key : nextNodeKeys) {
                String action = testQuestMap.get(key).getAction();
                expectedMap.put(key, action);
            }
            assertEquals(expectedMap, questService.getNextKeysAndActions(testKey));
        }

    }

    @Test
    void getConcequence() {
        for (String key : testQuestMap.keySet()) {
            assertEquals(testQuestMap.get(key).getConsequence(), questService.getConsequence(key));
        }
    }

    @Test
    void getType() {
        for (String key : testQuestMap.keySet()) {
            assertEquals(testQuestMap.get(key).getType(), questService.getType(key));
        }
    }

}
package com.javarush.pavlichenko.quest.service;

import com.javarush.pavlichenko.quest.repository.QuestRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class QuestServiceTest {

    QuestRepository questRepository = Mockito.mock(QuestRepository.class);
    Mock edge;

    @Test
    void getStartEdgeKey() {

    }

    @Test
    void getQuestion() {
    }

    @Test
    void getType() {
    }

    @Test
    void checkWin() {
    }

    @Test
    void checkDefeat() {
    }

    @Test
    void getNextKeysAndActions() {
    }
}
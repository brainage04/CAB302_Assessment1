package com.example.cab222a.model.resist_train;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResistTrainExerciseTest {
    private ResistTrainExercise testItem;

    @BeforeEach void setUp() {
        testItem = new ResistTrainExercise(
                -1,
                "Test Exercise",
                -1,
                -1
        );
    }

    @Test void testId() {
        assertEquals(testItem.getId(), -1);

        int test = -2;
        testItem.setId(test);
        assertEquals(test, testItem.getId());
    }

    @Test void testName() {
        assertEquals(testItem.getName(), "Test Exercise");

        String test = "Test Exercise 1";
        testItem.setName(test);
        assertEquals(test, testItem.getName());
    }

    @Test void testSessionId() {
        assertEquals(testItem.getSessionId(), -1);

        int test = -2;
        testItem.setSessionId(test);
        assertEquals(test, testItem.getSessionId());
    }

    @Test void testExerciseInfoId() {
        assertEquals(testItem.getExerciseInfoId(), -1);

        int test = -2;
        testItem.setExerciseInfoId(test);
        assertEquals(test, testItem.getExerciseInfoId());
    }
}

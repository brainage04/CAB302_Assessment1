package com.example.cab222a.model.resist_train;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResistTrainSetTest {
    private ResistTrainSet testItem;

    @BeforeEach void setUp() {
        testItem = new ResistTrainSet(
                -1,
                "Test Set",
                -1,
                20,
                10,
                60,
                2
        );
    }

    @Test void testId() {
        assertEquals(testItem.getId(), -1);

        int test = -2;
        testItem.setId(test);
        assertEquals(test, testItem.getId());
    }

    @Test void testName() {
        assertEquals(testItem.getName(), "Test Set");

        String test = "Test Set     1";
        testItem.setName(test);
        assertEquals(test, testItem.getName());
    }

    @Test void testExerciseId() {
        assertEquals(testItem.getExerciseId(), -1);

        int test = -2;
        testItem.setExerciseId(test);
        assertEquals(test, testItem.getExerciseId());
    }

    @Test void testWeight() {
        assertEquals(testItem.getWeight(), 20);

        int test = 21;
        testItem.setWeight(test);
        assertEquals(test, testItem.getWeight());
    }

    @Test void testReps() {
        assertEquals(testItem.getReps(), 10);

        int test = 11;
        testItem.setReps(test);
        assertEquals(test, testItem.getReps());
    }

    @Test void testRest() {
        assertEquals(testItem.getRest(), 60);

        int test = 61;
        testItem.setRest(test);
        assertEquals(test, testItem.getRest());
    }

    @Test void testRepsInReserve() {
        assertEquals(testItem.getRepsInReserve(), 2);

        int test = 3;
        testItem.setRepsInReserve(test);
        assertEquals(test, testItem.getRepsInReserve());
    }
}

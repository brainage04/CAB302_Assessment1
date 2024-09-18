package com.example.cab222a.model.resist_train;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExerciseInfoTest {
    private ExerciseInfo testItem;

    @BeforeEach void setUp() {
        testItem = new ExerciseInfo(
                -1,
                "Test Exercise",
                "Primary Test",
                "Secondary Test",
                "Test Description"
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

    @Test void testPrimaryMuscleGroups() {
        assertEquals(testItem.getPrimaryMuscleGroups(), "Primary Test");

        String test = "Primary Test 1";
        testItem.setPrimaryMuscleGroups(test);
        assertEquals(test, testItem.getPrimaryMuscleGroups());
    }

    @Test void testSecondaryMuscleGroups() {
        assertEquals(testItem.getSecondaryMuscleGroups(), "Secondary Test");

        String test = "Secondary Test 1";
        testItem.setSecondaryMuscleGroups(test);
        assertEquals(test, testItem.getSecondaryMuscleGroups());
    }

    @Test void testDescription() {
        assertEquals(testItem.getDescription(), "Test Description");

        String test = "Test Description 1";
        testItem.setDescription(test);
        assertEquals(test, testItem.getDescription());
    }
}

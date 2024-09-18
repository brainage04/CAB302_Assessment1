package com.example.cab222a.model.resist_train;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResistTrainSessionTest {
    private ResistTrainSession testItem;

    @BeforeEach void setUp() {
        testItem = new ResistTrainSession(
                -1,
                "Test Session",
                -1,
                new Date(1_000_000_000)
        );
    }

    @Test void testId() {
        assertEquals(testItem.getId(), -1);

        int test = -2;
        testItem.setId(test);
        assertEquals(test, testItem.getId());
    }

    @Test void testName() {
        assertEquals(testItem.getName(), "Test Session");

        String test = "Test Session 1";
        testItem.setName(test);
        assertEquals(test, testItem.getName());
    }

    @Test void testUserId() {
        assertEquals(testItem.getUserId(), -1);

        int test = -2;
        testItem.setUserId(test);
        assertEquals(test, testItem.getUserId());
    }

    @Test void testCreated() {
        assertEquals(testItem.getCreated(), new Date(1_000_000_000));

        Date test = new Date(1_000_000_000 - 128);
        testItem.setCreated(test);
        assertEquals(test, testItem.getCreated());
    }
}

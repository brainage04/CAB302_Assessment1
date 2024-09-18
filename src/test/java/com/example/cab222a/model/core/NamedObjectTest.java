package com.example.cab222a.model.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NamedObjectTest {
    private NamedObject testItem;

    @BeforeEach void setUp() {
        testItem = new NamedObject(
                -1,
                "Test Name"
        );
    }

    @Test void testId() {
        assertEquals(testItem.getId(), -1);

        int test = -2;
        testItem.setId(test);
        assertEquals(test, testItem.getId());
    }

    @Test void testName() {
        assertEquals(testItem.getName(), "Test Name");

        String test = "Test Name 1";
        testItem.setName(test);
        assertEquals(test, testItem.getName());
    }
}

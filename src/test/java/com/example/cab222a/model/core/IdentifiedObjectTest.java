package com.example.cab222a.model.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IdentifiedObjectTest {
    private IdentifiedObject testItem;

    @BeforeEach void setUp() {
        testItem = new IdentifiedObject(
                -1
        );
    }

    @Test void testId() {
        assertEquals(testItem.getId(), -1);

        int test = -2;
        testItem.setId(test);
        assertEquals(test, testItem.getId());
    }
}

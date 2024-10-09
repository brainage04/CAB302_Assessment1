package com.example.cab222a.model.resist_train;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HealthMetricTest {
    private HealthMetric testItem;

    @BeforeEach void setUp() {
        testItem = new HealthMetric (-1,
                1,
                "Weight(kg)",
                65,
                new Date(2024, 10,1));
    }

    @Test void testId() {
        assertEquals(testItem.getId(), -1);
        int test = 2;
        testItem.setId(test);
        assertEquals(test, testItem.getId());
    }
}

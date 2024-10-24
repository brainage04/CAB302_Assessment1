package com.example.cab222a.model.resist_train;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HealthMetricTest {
    private HealthMetric testItem;

    @BeforeEach void setUp() {
        testItem = new HealthMetric(
                "Weight for 24 Oct 2024",
                HealthMetricType.WEIGHT,
                -1,
                65,
                new Date(System.currentTimeMillis())
        );
    }

    @Test void testId() {
        assertEquals(testItem.getId(), -1);
        int test = 2;
        testItem.setId(test);
        assertEquals(test, testItem.getId());
    }
}

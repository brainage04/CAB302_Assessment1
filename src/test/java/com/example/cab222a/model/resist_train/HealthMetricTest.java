package com.example.cab222a.model.resist_train;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HealthMetricTest {
    private HealthMetric testItem;

    @BeforeEach void setUp() {
        testItem = new HealthMetric(
                -1,
                "New Metric",
                HealthMetricType.HEIGHT,
                -1,
                180,
                new Date(1_000_000_000)
        );
    }

    @Test void testId() {
        assertEquals(testItem.getId(), -1);
        int test = -2;
        testItem.setId(test);
        assertEquals(test, testItem.getId());
    }

    @Test void testMetricType() {
        assertEquals(testItem.getMetricType(), HealthMetricType.HEIGHT);

        testItem.setMetricType(HealthMetricType.WEIGHT);
        assertEquals(HealthMetricType.WEIGHT, testItem.getMetricType());
    }

    @Test void testMeasurement() {
        assertEquals(testItem.getMeasurement(), 65);

        testItem.setMeasurement(70);
        assertEquals(70, testItem.getMeasurement());
    }

    @Test void testDate() {
        assertEquals(testItem.getCreated(), new Date(1_000_000_000));

        Date testDate = new Date(System.currentTimeMillis());

        testItem.setCreated(testDate);
        assertEquals(testItem.getCreated(), testDate);
    }
}

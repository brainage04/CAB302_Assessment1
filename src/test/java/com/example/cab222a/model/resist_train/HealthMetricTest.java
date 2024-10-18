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

    @Test void testMetricType() {
        assertEquals(testItem.getName(), "Weight(kg)");
        String test = "Steps";
        testItem.setMetricType(test);
        assertEquals(test, testItem.getMetricType());
    }

    @Test void testMeasurement() {
        assertEquals(testItem.getMeasurement(), 65);
        double test = 70;
        testItem.setMeasurement(test);
        assertEquals(test, testItem.getMeasurement());
    }

    @Test void testDate() {
        Date date = new Date (2024, 10 , 1);
        assertEquals(testItem.getDate(), date);

        Date testDate = new Date (2025, 11, 3);
        testItem.setDate(testDate);
        assertEquals(testItem.getDate(), testDate);
    }
}

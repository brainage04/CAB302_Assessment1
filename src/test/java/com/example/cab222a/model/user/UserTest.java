package com.example.cab222a.model.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    private User testItem;

    @BeforeEach void setUp() {
        testItem = new User(
                -1,
                new Date(1_000_000_000),
                "Test",
                "User",
                "test@example.com",
                "0450450450",
                "test"
        );
    }

    @Test void testId() {
        assertEquals(testItem.getId(), -1);

        int test = -2;
        testItem.setId(test);
        assertEquals(test, testItem.getId());
    }

    @Test void testCreated() {
        assertEquals(testItem.getCreated(), new Date(1_000_000_000));

        Date test = new Date(1_000_000_000 - 128);
        testItem.setCreated(test);
        assertEquals(test, testItem.getCreated());
    }

    @Test void testFirstName() {
        assertEquals(testItem.getFirstName(), "Test");

        String test = "Test1";
        testItem.setFirstName(test);
        assertEquals(test, testItem.getFirstName());
    }

    @Test void testLastName() {
        assertEquals(testItem.getLastName(), "User");

        String test = "User1";
        testItem.setLastName(test);
        assertEquals(test, testItem.getLastName());
    }

    @Test void testEmail() {
        assertEquals(testItem.getEmail(), "test@example.com");

        String test = "test1@example.com";
        testItem.setEmail(test);
        assertEquals(test, testItem.getEmail());
    }

    @Test void testPhone() {
        assertEquals(testItem.getPhone(), "0450450450");

        String test = "0450450451";
        testItem.setPhone(test);
        assertEquals(test, testItem.getPhone());
    }

    @Test void testPassword() {
        assertEquals(testItem.getPassword(), "test");

        String test = "test1";
        testItem.setPassword(test);
        assertEquals(test, testItem.getPassword());
    }
}

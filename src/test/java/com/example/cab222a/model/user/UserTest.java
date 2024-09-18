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

        int testId = -2;
        testItem.setId(testId);
        assertEquals(testId, testItem.getId());
    }

    @Test void testCreated() {
        assertEquals(testItem.getCreated(), new Date(1_000_000_000));

        Date testDate = new Date(1_000_000_000 - 128);
        testItem.setCreated(testDate);
        assertEquals(testDate, testItem.getCreated());
    }

    @Test void testFirstName() {
        assertEquals(testItem.getFirstName(), "Test");

        String testFirstName = "Test1";
        testItem.setFirstName(testFirstName);
        assertEquals(testFirstName, testItem.getFirstName());
    }

    @Test void testLastName() {
        assertEquals(testItem.getLastName(), "User");

        String testLastName = "User1";
        testItem.setLastName(testLastName);
        assertEquals(testLastName, testItem.getLastName());
    }

    @Test void testEmail() {
        assertEquals(testItem.getEmail(), "test@example.com");

        String testEmail = "test1@example.com";
        testItem.setEmail(testEmail);
        assertEquals(testEmail, testItem.getEmail());
    }

    @Test void testPhone() {
        assertEquals(testItem.getPhone(), "0450450450");

        String testPhone = "0450450451";
        testItem.setPhone(testPhone);
        assertEquals(testPhone, testItem.getPhone());
    }

    @Test void testPassword() {
        assertEquals(testItem.getPassword(), "test");

        String testPassword = "test1";
        testItem.setPassword(testPassword);
        assertEquals(testPassword, testItem.getPassword());
    }
}

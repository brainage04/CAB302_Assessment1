package com.example.cab222a.model.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    private User user;

    @BeforeEach void setUp() {
        user = new User(
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
        assertEquals(user.getId(), -1);

        int testId = -2;
        user.setId(testId);
        assertEquals(testId, user.getId());
    }

    @Test void testCreated() {
        assertEquals(user.getCreated(), new Date(1_000_000_000));

        Date testDate = new Date(1_000_000_000 - 128);
        user.setCreated(testDate);
        assertEquals(testDate, user.getCreated());
    }

    @Test void testFirstName() {
        assertEquals(user.getFirstName(), "Test");

        String testFirstName = "Test1";
        user.setFirstName(testFirstName);
        assertEquals(testFirstName, user.getFirstName());
    }

    @Test void testLastName() {
        assertEquals(user.getLastName(), "User");

        String testLastName = "User1";
        user.setLastName(testLastName);
        assertEquals(testLastName, user.getLastName());
    }

    @Test void testEmail() {
        assertEquals(user.getEmail(), "test@example.com");

        String testEmail = "test1@example.com";
        user.setEmail(testEmail);
        assertEquals(testEmail, user.getEmail());
    }

    @Test void testPhone() {
        assertEquals(user.getPhone(), "0450450450");

        String testPhone = "0450450451";
        user.setPhone(testPhone);
        assertEquals(testPhone, user.getPhone());
    }

    @Test void testPassword() {
        assertEquals(user.getPassword(), "test");

        String testPassword = "test1";
        user.setPassword(testPassword);
        assertEquals(testPassword, user.getPassword());
    }
}

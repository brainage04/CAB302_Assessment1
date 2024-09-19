package com.example.cab222a.dao.user;

import com.example.cab222a.common.SqliteConnection;
import com.example.cab222a.model.user.User;
import org.junit.jupiter.api.*;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserDAOTest {
    static UserDAO dao;
    static User defaultItem;
    static User updatedItem;

    @BeforeAll
    static void setUp() {
        dao = new UserDAO();
        defaultItem = new User(new Date(System.currentTimeMillis()), "Test", "User", "test@example.com", "testpassword", "0412345678");
        updatedItem = new User(new Date(System.currentTimeMillis() - 128), "Test1", "User2", "test3@example.com", "testpassword4", "0456789012");

        // drop table
        try (Statement statement = SqliteConnection.getInstance().createStatement()) {
            statement.execute("DROP TABLE " + dao.tableName());
            dao.createTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test @Order(1) void createReadUser() {
        User actual = defaultItem;
        int actualId = dao.addAndGetId(actual);

        assertTrue(actualId > 0);

        User expected = dao.getItem(actualId);

        assertEquals(actual.getCreated(), expected.getCreated());
        assertEquals(actual.getFirstName(), expected.getFirstName());
        assertEquals(actual.getLastName(), expected.getLastName());
        assertEquals(actual.getEmail(), expected.getEmail());
        assertEquals(actual.getPhone(), expected.getPhone());
        assertEquals(actual.getPassword(), expected.getPassword());

        // this will be useful in future tests
        defaultItem.setId(expected.getId());
    }

    @Test @Order(2) void updateUser() {
        User actual = defaultItem;
        User expected = updatedItem;
        expected.setId(actual.getId());
        int affectedRows = dao.updateItem(expected);

        assertEquals(affectedRows, 1);

        actual = dao.getItem(actual.getId());

        assertEquals(actual.getId(), expected.getId());
        assertEquals(actual.getCreated(), expected.getCreated());
        assertEquals(actual.getFirstName(), expected.getFirstName());
        assertEquals(actual.getLastName(), expected.getLastName());
        assertEquals(actual.getEmail(), expected.getEmail());
        assertEquals(actual.getPhone(), expected.getPhone());
        assertEquals(actual.getPassword(), expected.getPassword());
    }

    @Test @Order(3) void deleteUser() {
        User delete = dao.getItem(defaultItem.getId());
        int affectedRows = dao.deleteItem(delete.getId());

        assertEquals(affectedRows, 1);

        delete = dao.getItem(defaultItem.getId());

        assertNull(delete);
    }
}

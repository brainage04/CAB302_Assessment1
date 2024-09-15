package com.example.cab222a.dao.user;

import com.example.cab222a.common.SqliteConnection;
import com.example.cab222a.model.user.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

public class UserDAOTest {
    static UserDAO userDAO;

    @BeforeAll
    static void setUp() {
        userDAO = new UserDAO();

        // drop table
        try {
            Statement statement = SqliteConnection.getInstance().createStatement();
            statement.execute("DROP TABLE " + userDAO.tableName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void createTable() {
        userDAO.createTable();
    }

    @Test
    void createReadUser() {
        User actual = new User(new Date(System.currentTimeMillis()), "Test", "User", "test@example.com", "testpassword", "0412345678");
        userDAO.addItem(actual);

        User expected = userDAO.getItem(actual.getEmail(), actual.getPassword());

        // actual user's ID is null until retrieved from the database
        actual.setId(expected.getId());

        // must check each variable individually because Objects are weird
        assertEquals(actual.getId(), expected.getId());
        assertEquals(actual.getCreated(), expected.getCreated());
        assertEquals(actual.getFirstName(), expected.getFirstName());
        assertEquals(actual.getLastName(), expected.getLastName());
        assertEquals(actual.getEmail(), expected.getEmail());
        assertEquals(actual.getPhone(), expected.getPhone());
        assertEquals(actual.getPassword(), expected.getPassword());
    }

    @Test
    void updateUser() {
        User actual = new User(new Date(System.currentTimeMillis()), "Test", "User", "test@example.com", "testpassword", "0412345678");
        userDAO.addItem(actual);

        User expected = userDAO.getItem(actual.getEmail(), actual.getPassword());

        // actual user's ID is null until retrieved from the database
        actual.setId(expected.getId());

        // make changes to all fields except ID
        actual.setCreated(new Date(System.currentTimeMillis()));
        actual.setFirstName("Test1");
        actual.setLastName("User2");
        actual.setEmail("test1@example.com");
        actual.setPhone("0434567890");
        actual.setPassword("testpassword4");

        userDAO.updateItem(actual);

        // update expected user to contain updated values
        expected = userDAO.getItem(actual.getId());

        // must check each variable individually because Objects are weird
        assertEquals(actual.getId(), expected.getId());
        assertEquals(actual.getCreated(), expected.getCreated());
        assertEquals(actual.getFirstName(), expected.getFirstName());
        assertEquals(actual.getLastName(), expected.getLastName());
        assertEquals(actual.getEmail(), expected.getEmail());
        assertEquals(actual.getPhone(), expected.getPhone());
        assertEquals(actual.getPassword(), expected.getPassword());
    }

    @Test
    void deleteUser() {
        User userToDelete = userDAO.getItem("test1@example.com", "testpassword4");

        userDAO.deleteItem(userToDelete.getId());

        User deletedUser = userDAO.getItem("test1@example.com", "testpassword4");

        assertNull(deletedUser);
    }
}

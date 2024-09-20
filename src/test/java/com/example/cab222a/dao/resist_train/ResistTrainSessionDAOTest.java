package com.example.cab222a.dao.resist_train;

import com.example.cab222a.dao.util.DAOTestUtils;
import com.example.cab222a.model.resist_train.ResistTrainSession;
import org.junit.jupiter.api.*;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ResistTrainSessionDAOTest {
    static ResistTrainSessionDAO dao;
    static ResistTrainSession defaultItem;
    static ResistTrainSession updatedItem;

    @BeforeAll
    static void setUp() {
        // set up test items for each table in relational "chain" if you will
        // (user -> session -> exercise -> set)
        int userId = DAOTestUtils.setUpUser();

        dao = new ResistTrainSessionDAO();
        dao.resetTable();

        defaultItem = new ResistTrainSession("Test Session", userId, new Date(1_000_000_000));
        updatedItem = new ResistTrainSession("Test Session 1", userId, new Date(1_000_000_000 - 128));
    }

    @Test @Order(1) void createReadUser() {
        ResistTrainSession actual = defaultItem;
        int actualId = dao.addAndGetId(actual);

        assertTrue(actualId > 0);

        ResistTrainSession expected = dao.getItem(actualId);

        assertEquals(actual.getName(), expected.getName());
        assertEquals(actual.getUserId(), expected.getUserId());
        assertEquals(actual.getCreated(), expected.getCreated());

        // this will be useful in future tests
        defaultItem.setId(expected.getId());
    }

    @Test @Order(2) void updateUser() {
        ResistTrainSession actual = defaultItem;
        ResistTrainSession expected = updatedItem;
        expected.setId(actual.getId());
        int affectedRows = dao.updateItem(expected);

        assertEquals(affectedRows, 1);

        actual = dao.getItem(actual.getId());

        assertEquals(actual.getId(), expected.getId());
        assertEquals(actual.getName(), expected.getName());
        assertEquals(actual.getUserId(), expected.getUserId());
        assertEquals(actual.getCreated(), expected.getCreated());
    }

    @Test @Order(3) void deleteUser() {
        ResistTrainSession delete = dao.getItem(defaultItem.getId());
        int affectedRows = dao.deleteItem(delete.getId());

        assertEquals(affectedRows, 1);

        delete = dao.getItem(defaultItem.getId());

        assertNull(delete);
    }
}

package com.example.cab222a.dao.resist_train;

import com.example.cab222a.dao.core.IObjectDAO;
import com.example.cab222a.dao.util.DAOTestUtils;
import com.example.cab222a.mock_dao.core.AbstractObjectMockDAO;
import com.example.cab222a.model.resist_train.ResistTrainSession;
import org.junit.jupiter.api.*;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ResistTrainSessionDAOTest {
    static IObjectDAO<ResistTrainSession> dao;
    static ResistTrainSession defaultItem;
    static ResistTrainSession updatedItem;

    @BeforeAll
    static void setUp() {
        // set up test items for each table in relational "chain" if you will
        // (user -> session -> exercise -> set)
        int userId = DAOTestUtils.setUpUser();

        dao = new AbstractObjectMockDAO<>();
        dao.resetTable();

        defaultItem = new ResistTrainSession("Test Session", userId, new Date(1_000_000_000));
        updatedItem = new ResistTrainSession("Test Session 1", userId, new Date(1_000_000_000 - 128));
    }

    @Test @Order(1) void createReadUser() {
        ResistTrainSession actual = defaultItem;
        int actualId = dao.addItem(actual);

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
        dao.updateItem(expected);

        actual = dao.getItem(actual.getId());

        assertEquals(actual.getId(), expected.getId());
        assertEquals(actual.getName(), expected.getName());
        assertEquals(actual.getUserId(), expected.getUserId());
        assertEquals(actual.getCreated(), expected.getCreated());
    }

    @Test @Order(3) void deleteUser() {
        ResistTrainSession delete = dao.getItem(defaultItem.getId());
        dao.deleteItem(delete.getId());
        delete = dao.getItem(defaultItem.getId());

        assertNull(delete);
    }
}

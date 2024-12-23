package com.example.cab222a.dao.resist_train;

import com.example.cab222a.dao.core.IObjectDAO;
import com.example.cab222a.dao.util.DAOTestUtils;
import com.example.cab222a.mock_dao.core.AbstractObjectMockDAO;
import com.example.cab222a.model.resist_train.ResistTrainExercise;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ResistTrainExerciseDAOTest {
    static IObjectDAO<ResistTrainExercise> dao;
    static ResistTrainExercise defaultItem;
    static ResistTrainExercise updatedItem;

    @BeforeAll
    static void setUp() {
        // set up test items for each table in relational "chain" if you will
        // (user -> session -> exercise -> set)
        int userId = DAOTestUtils.setUpUser();
        int sessionId = DAOTestUtils.setUpSession(userId);

        dao = new AbstractObjectMockDAO<>();
        dao.resetTable();

        defaultItem = new ResistTrainExercise("Test Exercise", sessionId, -1);
        updatedItem = new ResistTrainExercise("Test Exercise 1", sessionId, -1);
    }

    @Test @Order(1) void createReadUser() {
        ResistTrainExercise actual = defaultItem;
        int actualId = dao.addItem(actual);

        assertTrue(actualId > 0);

        ResistTrainExercise expected = dao.getItem(actualId);

        assertEquals(actual.getName(), expected.getName());
        assertEquals(actual.getSessionId(), expected.getSessionId());
        assertEquals(actual.getExerciseInfoId(), expected.getExerciseInfoId());

        // this will be useful in future tests
        defaultItem.setId(expected.getId());
    }

    @Test @Order(2) void updateUser() {
        ResistTrainExercise actual = defaultItem;
        ResistTrainExercise expected = updatedItem;
        expected.setId(actual.getId());
        dao.updateItem(expected);

        actual = dao.getItem(actual.getId());

        assertEquals(actual.getId(), expected.getId());
        assertEquals(actual.getName(), expected.getName());
        assertEquals(actual.getSessionId(), expected.getSessionId());
        assertEquals(actual.getExerciseInfoId(), expected.getExerciseInfoId());
    }

    @Test @Order(3) void deleteUser() {
        ResistTrainExercise delete = dao.getItem(defaultItem.getId());
        dao.deleteItem(delete.getId());
        delete = dao.getItem(defaultItem.getId());

        assertNull(delete);
    }
}

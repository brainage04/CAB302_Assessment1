package com.example.cab222a.dao.resist_train;

import com.example.cab222a.dao.util.DAOTestUtils;
import com.example.cab222a.model.resist_train.ResistTrainSet;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ResistTrainSetDAOTest {
    static ResistTrainSetDAO dao;
    static ResistTrainSet defaultItem;
    static ResistTrainSet updatedItem;

    @BeforeAll
    static void setUp() {
        // set up test items for each table in relational "chain" if you will
        // (user -> session -> exercise -> set)
        int userId = DAOTestUtils.setUpUser();
        int sessionId = DAOTestUtils.setUpSession(userId);
        int exerciseId = DAOTestUtils.setUpExercise(sessionId);

        dao = new ResistTrainSetDAO();
        dao.resetTable();

        defaultItem = new ResistTrainSet("Test Set", exerciseId, 20, 10, 60, 2);
        updatedItem = new ResistTrainSet("Test Set 1", exerciseId, 21, 11, 61, 3);
    }

    @Test @Order(1) void createReadUser() {
        ResistTrainSet actual = defaultItem;
        int actualId = dao.addAndGetId(actual);

        assertTrue(actualId > 0);

        ResistTrainSet expected = dao.getItem(actualId);

        assertEquals(actual.getName(), expected.getName());
        assertEquals(actual.getExerciseId(), expected.getExerciseId());
        assertEquals(actual.getWeight(), expected.getWeight());
        assertEquals(actual.getReps(), expected.getReps());
        assertEquals(actual.getRest(), expected.getRest());
        assertEquals(actual.getRepsInReserve(), expected.getRepsInReserve());

        // this will be useful in future tests
        defaultItem.setId(expected.getId());
    }

    @Test @Order(2) void updateUser() {
        ResistTrainSet actual = defaultItem;
        ResistTrainSet expected = updatedItem;
        expected.setId(actual.getId());
        int affectedRows = dao.updateItem(expected);

        assertEquals(affectedRows, 1);

        actual = dao.getItem(actual.getId());

        assertEquals(actual.getId(), expected.getId());
        assertEquals(actual.getName(), expected.getName());
        assertEquals(actual.getExerciseId(), expected.getExerciseId());
        assertEquals(actual.getWeight(), expected.getWeight());
        assertEquals(actual.getReps(), expected.getReps());
        assertEquals(actual.getRest(), expected.getRest());
        assertEquals(actual.getRepsInReserve(), expected.getRepsInReserve());
    }

    @Test @Order(3) void deleteUser() {
        ResistTrainSet delete = dao.getItem(defaultItem.getId());
        int affectedRows = dao.deleteItem(delete.getId());

        assertEquals(affectedRows, 1);

        delete = dao.getItem(defaultItem.getId());

        assertNull(delete);
    }
}

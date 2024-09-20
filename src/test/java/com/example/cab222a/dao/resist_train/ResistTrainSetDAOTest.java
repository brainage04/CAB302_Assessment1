package com.example.cab222a.dao.resist_train;

import com.example.cab222a.dao.core.IObjectDAO;
import com.example.cab222a.dao.util.DAOTestUtils;
import com.example.cab222a.mock_dao.core.AbstractObjectMockDAO;
import com.example.cab222a.model.resist_train.ResistTrainSet;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ResistTrainSetDAOTest {
    static IObjectDAO<ResistTrainSet> dao;
    static ResistTrainSet defaultItem;
    static ResistTrainSet updatedItem;

    @BeforeAll
    static void setUp() {
        // set up test items for each table in relational "chain" if you will
        // (user -> session -> exercise -> set)
        int userId = DAOTestUtils.setUpUser();
        int sessionId = DAOTestUtils.setUpSession(userId);
        int exerciseId = DAOTestUtils.setUpExercise(sessionId);

        dao = new AbstractObjectMockDAO<>();
        dao.resetTable();

        defaultItem = new ResistTrainSet("Test Set", exerciseId, 20, 10, 60, 2);
        updatedItem = new ResistTrainSet("Test Set 1", exerciseId, 21, 11, 61, 3);
    }

    @Test @Order(1) void createReadUser() {
        ResistTrainSet actual = defaultItem;
        int actualId = dao.addItem(actual);

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
        dao.updateItem(expected);

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
        dao.deleteItem(delete.getId());
        delete = dao.getItem(defaultItem.getId());

        assertNull(delete);
    }
}

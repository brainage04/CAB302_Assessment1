package com.example.cab222a.dao.resist_train;

import com.example.cab222a.common.SqliteConnection;
import com.example.cab222a.dao.user.UserDAO;
import com.example.cab222a.model.resist_train.ResistTrainExercise;
import com.example.cab222a.model.resist_train.ResistTrainSession;
import com.example.cab222a.model.resist_train.ResistTrainSet;
import com.example.cab222a.model.user.User;
import org.junit.jupiter.api.*;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ResistTrainSetDAOTest {
    static UserDAO userDAO;
    static ResistTrainSessionDAO sessionDAO;
    static ResistTrainExerciseDAO exerciseDAO;
    static ResistTrainSetDAO dao;
    static ResistTrainSet defaultItem;
    static ResistTrainSet updatedItem;

    @BeforeAll
    static void setUp() {
        // set up users
        userDAO = new UserDAO();
        userDAO.resetTable();

        // add and set test user
        User testUser = new User(
                new Date(System.currentTimeMillis()),
                "Test",
                "User",
                "test@example.com",
                "password",
                "0450450450"
        );
        int userId = userDAO.addAndGetId(testUser);
        testUser.setId(userId);
        SqliteConnection.setCurrentUser(testUser);

        // set up resist train sessions
        sessionDAO = new ResistTrainSessionDAO();
        sessionDAO.resetTable();

        // add and set test session
        ResistTrainSession testSession = new ResistTrainSession(
                "Test Session",
                userId,
                new Date(System.currentTimeMillis())
        );
        int sessionId = sessionDAO.addAndGetId(testSession);
        testSession.setId(sessionId);
        SqliteConnection.setCurrentResistTrainSession(testSession);

        // set up resist train exercises
        exerciseDAO = new ResistTrainExerciseDAO();
        exerciseDAO.resetTable();

        // add test exercise
        ResistTrainExercise testExercise = new ResistTrainExercise(
                "Test Session",
                sessionId,
                -1
        );
        int exerciseId = exerciseDAO.addAndGetId(testExercise);
        testExercise.setId(exerciseId);
        SqliteConnection.setCurrentResistTrainExercise(testExercise);

        // set up DAOs
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

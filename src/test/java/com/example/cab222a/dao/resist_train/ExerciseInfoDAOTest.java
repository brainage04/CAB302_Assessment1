package com.example.cab222a.dao.resist_train;

import com.example.cab222a.common.SqliteConnection;
import com.example.cab222a.dao.user.UserDAO;
import com.example.cab222a.model.resist_train.ExerciseInfo;
import com.example.cab222a.model.user.User;
import org.junit.jupiter.api.*;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ExerciseInfoDAOTest {

    static UserDAO userDAO;
    static ExerciseInfoDAO exerciseInfoDAO;
    static ExerciseInfo defaultItem;
    static ExerciseInfo updatedItem;

    @BeforeAll static void setUp() {
        userDAO = new UserDAO();
        userDAO.resetTable();

        User testUser = new User(
                new Date(System.currentTimeMillis()),
                "Test",
                "User",
                "test@example.com",
                "password",
                "0413333333"
        );
        int userId = userDAO.addAndGetId(testUser);
        testUser.setId(userId);
        SqliteConnection.setCurrentUser(testUser);

        exerciseInfoDAO = new ExerciseInfoDAO();
        exerciseInfoDAO.resetTable();

        defaultItem = new ExerciseInfo(
                "Barbell Bench Press",
                "Chest",
                "Triceps and Shoulders",
                "Chest building essential."
        );
        updatedItem = new ExerciseInfo(
                "Incline Bench Press",
                "Upper Chest",
                "Shoulders and Triceps",
                "Upper chest movement."
        );
    }
        @Test @Order(1) void createReadExerciseInfo() {
            int actualId = exerciseInfoDAO.addAndGetId(defaultItem);

            assertTrue(actualId > 0);

            ExerciseInfo expected = exerciseInfoDAO.getItem(actualId);

            assertEquals(defaultItem.getName(), expected.getName());
            assertEquals(defaultItem.getPrimaryMuscleGroups(), expected.getPrimaryMuscleGroups());
            assertEquals(defaultItem.getSecondaryMuscleGroups(), expected.getSecondaryMuscleGroups());
            assertEquals(defaultItem.getDescription(), expected.getDescription());

            defaultItem.setId(actualId);
        }
        @Test @Order(2) void updateExerciseInfo() {
            ExerciseInfo actual = defaultItem;
            ExerciseInfo expected = updatedItem;
            expected.setId(actual.getId());
            int affectedRows = exerciseInfoDAO.updateItem(expected);

            assertEquals(affectedRows, 1);

            actual = exerciseInfoDAO.getItem(actual.getId());

            assertEquals(actual.getId(), expected.getId());
            assertEquals(actual.getName(), expected.getName());
            assertEquals(actual.getPrimaryMuscleGroups(), expected.getPrimaryMuscleGroups());
            assertEquals(actual.getSecondaryMuscleGroups(), expected.getSecondaryMuscleGroups());
            assertEquals(actual.getDescription(), expected.getDescription());

        }

        @Test @Order(3) void deleteExerciseInfo() {
            int affectedRows = exerciseInfoDAO.deleteItem(defaultItem.getId());

            assertEquals(affectedRows, 1);

            ExerciseInfo actual = exerciseInfoDAO.getItem(defaultItem.getId());

            assertNull(actual);
        }

        @Test @Order(4) void getAllExerciseInfos() {
            exerciseInfoDAO.addAndGetId(defaultItem);
            exerciseInfoDAO.addAndGetId(updatedItem);

            List<ExerciseInfo> items = exerciseInfoDAO.getAllItems();
            assertFalse(items.isEmpty());
            assertTrue(items.size() >= 2);
        }

        @Test @Order(5) void searchExerciseInfo() {
        // Search name
            List<ExerciseInfo> search = exerciseInfoDAO.searchExerciseInfo("Bench");
            assertFalse(search.isEmpty());
        // Search muscle
            search = exerciseInfoDAO.searchExerciseInfo("Chest");
            assertFalse(search.isEmpty());

        }

        @Test @Order(6) void testNullQuery () {
        List<ExerciseInfo> exerciseInfo = exerciseInfoDAO.searchExerciseInfo(null);
        // 48 Default exercises.
        assertEquals(48, exerciseInfo.size());
        }


        // No exercise or muscle called Deadlock
        // List should return 0 matches.
        @Test @Order(7) void testSearchNoResults () {
        List<ExerciseInfo> exerciseInfo = exerciseInfoDAO.searchExerciseInfo("Deadlock");
        assertEquals(0, exerciseInfo.size());
        }
    }


package com.example.cab222a.dao.resist_train;

import com.example.cab222a.dao.core.IObjectDAO;
import com.example.cab222a.dao.util.DAOTestUtils;
import com.example.cab222a.model.resist_train.ExerciseInfo;
import com.example.cab222a.mock_dao.core.AbstractObjectMockDAO;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ExerciseInfoDAOTest {

    static IObjectDAO<ExerciseInfo> exerciseInfoDAO;
    static ExerciseInfo defaultItem;
    static ExerciseInfo updatedItem;

    @BeforeAll static void setUp() {
        int userId = DAOTestUtils.setUpUser();

        exerciseInfoDAO = new AbstractObjectMockDAO<>();
        exerciseInfoDAO.resetTable();

        defaultItem = new ExerciseInfo(
                "Flat Barbell Bench Press Test",
                "Chest",
                "Triceps and Shoulders",
                "Chest building essential."
        );
        updatedItem = new ExerciseInfo(
                "Incline Dumbbell Bench Press Test",
                "Upper Chest",
                "Shoulders and Triceps",
                "Upper chest movement."
        );
    }
        @Test @Order(1) void createReadExerciseInfo() {
        ExerciseInfo actual = defaultItem;
        int actualId = exerciseInfoDAO.addItem(actual);

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
            exerciseInfoDAO.updateItem(expected);

            actual = exerciseInfoDAO.getItem(actual.getId());

            assertEquals(actual.getId(), expected.getId());
            assertEquals(actual.getName(), expected.getName());
            assertEquals(actual.getPrimaryMuscleGroups(), expected.getPrimaryMuscleGroups());
            assertEquals(actual.getSecondaryMuscleGroups(), expected.getSecondaryMuscleGroups());
            assertEquals(actual.getDescription(), expected.getDescription());

        }

        @Test @Order(3) void deleteExerciseInfo() {
            ExerciseInfo delete = exerciseInfoDAO.getItem(defaultItem.getId());
            exerciseInfoDAO.deleteItem(delete.getId());
            delete = exerciseInfoDAO.getItem(defaultItem.getId());

            assertNull(delete);
        }

        @Test @Order(4) void getAllExerciseInfos() {
            exerciseInfoDAO.addItem(defaultItem);
            exerciseInfoDAO.addItem(updatedItem);

            List<ExerciseInfo> items = exerciseInfoDAO.getAllItems();
            assertFalse(items.isEmpty());
            assertEquals(2, items.size());
        }
    @Test @Order(5)
    void searchExerciseInfo() {
        List<ExerciseInfo> allExercises = exerciseInfoDAO.getAllItems();

        String search = "Bench";
        List<ExerciseInfo> searchResults = allExercises.stream().filter(exercise -> exercise.getName().contains(search) ||
                        exercise.getPrimaryMuscleGroups().contains(search) ||
                        exercise.getSecondaryMuscleGroups().contains(search)).toList();

        assertFalse(searchResults.isEmpty());
        assertTrue(searchResults.stream().anyMatch(exercise -> exercise.getName().contains(search) ||
                                exercise.getPrimaryMuscleGroups().contains(search) ||
                                exercise.getSecondaryMuscleGroups().contains(search)));
        }

        @Test @Order(6) void testNullQuery () {
            List<ExerciseInfo> allExercises = exerciseInfoDAO.getAllItems();
            assertFalse(allExercises.isEmpty());
            assertEquals(2, allExercises.size());
        }

        @Test @Order(7) void testSearchNoResults () {
            List<ExerciseInfo> allExercises = exerciseInfoDAO.getAllItems();

            String search = "Steroids"; // No exercise or muscle called Steroids
            List<ExerciseInfo> searchResults = allExercises.stream().filter(exercise -> exercise.getName().contains(search) ||
                            exercise.getPrimaryMuscleGroups().contains(search) ||
                            exercise.getSecondaryMuscleGroups().contains(search)).toList();

            assertFalse(searchResults.stream().anyMatch(exercise -> exercise.getName().contains(search) ||
                    exercise.getPrimaryMuscleGroups().contains(search) ||
                    exercise.getSecondaryMuscleGroups().contains(search)));
            assertTrue(searchResults.isEmpty());

        }
    }


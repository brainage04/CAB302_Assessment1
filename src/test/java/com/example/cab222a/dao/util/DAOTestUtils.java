package com.example.cab222a.dao.util;

import com.example.cab222a.common.SqliteConnection;
import com.example.cab222a.dao.resist_train.ResistTrainExerciseDAO;
import com.example.cab222a.dao.resist_train.ResistTrainSessionDAO;
import com.example.cab222a.dao.user.UserDAO;
import com.example.cab222a.model.resist_train.ResistTrainExercise;
import com.example.cab222a.model.resist_train.ResistTrainSession;
import com.example.cab222a.model.user.User;

import java.sql.Date;

public class DAOTestUtils {
    public static int setUpUser() {
        // set up users
        UserDAO dao = new UserDAO();
        dao.resetTable();

        // add and set test user
        User testUser = new User(
                new Date(System.currentTimeMillis()),
                "Test",
                "User",
                "test@example.com",
                "password",
                "0450450450"
        );
        int userId = dao.addItem(testUser);
        testUser.setId(userId);
        SqliteConnection.setCurrentUser(testUser);

        return userId;
    }

    public static int setUpSession(int userId) {
        // set up resist train sessions
        ResistTrainSessionDAO dao = new ResistTrainSessionDAO();
        dao.resetTable();

        // add and set test session
        ResistTrainSession testSession = new ResistTrainSession(
                "Test Session",
                userId,
                new Date(System.currentTimeMillis())
        );
        int sessionId = dao.addItem(testSession);
        testSession.setId(sessionId);
        SqliteConnection.setCurrentResistTrainSession(testSession);

        return sessionId;
    }

    public static int setUpExercise(int sessionId) {
        // set up resist train exercises
        ResistTrainExerciseDAO dao = new ResistTrainExerciseDAO();
        dao.resetTable();

        // add test exercise
        ResistTrainExercise testExercise = new ResistTrainExercise(
                "Test Session",
                sessionId,
                -1
        );
        int exerciseId = dao.addItem(testExercise);
        testExercise.setId(exerciseId);
        SqliteConnection.setCurrentResistTrainExercise(testExercise);

        return exerciseId;
    }
}

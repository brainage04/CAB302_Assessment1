package com.example.cab222a.common;

import com.example.cab222a.model.resist_train.ResistTrainExercise;
import com.example.cab222a.model.resist_train.ResistTrainSession;
import com.example.cab222a.model.user.User;
import com.example.cab222a.model.resist_train.ExerciseInfo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqliteConnection {
    private static Connection instance = null;
    private static User currentUser = null;
    private static ResistTrainSession currentResistTrainSession = null;
    private static ResistTrainExercise currentResistTrainExercise = null;
    private static ExerciseInfo currentExerciseInfo = null;

    /**
     * Instantiates this class and establishes a connection to this application's database.
     */
    private SqliteConnection() {
        String url = "jdbc:sqlite:main.db";
        try {
            instance = DriverManager.getConnection(url);
            instance.setAutoCommit(true);

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
    }

    /**
     *
     * @return A new SQLite Connection to this application's database.
     */
    public static Connection getInstance() {
        if (instance == null) {
            new SqliteConnection();
        }
        return instance;
    }

    /**
     *
     * @return The user currently logged into the application.
     */
    public static User getCurrentUser() {
        return currentUser;
    }

    /**
     * Updates the current user of the application in this utility class for future use in other classes.
     * @param currentUser The user currently logged into the application.
     */
    public static void setCurrentUser(User currentUser) {
        SqliteConnection.currentUser = currentUser;
    }

    /**
     * Logs the current user out of the application.
     */
    public static void logOut() {
        setCurrentUser(null);
    }

    /**
     *
     * @return The current resistance training session being modified by the user.
     */
    public static ResistTrainSession getCurrentResistTrainSession() {
        return currentResistTrainSession;
    }

    /**
     * Updates the current resistance training session of the application in this utility class for future use in other classes.
     * @param currentResistTrainSession The resistance training session currently being modified.
     */
    public static void setCurrentResistTrainSession(ResistTrainSession currentResistTrainSession) {
        SqliteConnection.currentResistTrainSession = currentResistTrainSession;
    }

    /**
     *
     * @return The current resistance training exercise being modified by the user.
     */
    public static ResistTrainExercise getCurrentResistTrainExercise() {
        return currentResistTrainExercise;
    }

    /**
     * Updates the current resistance training exercise of the application in this utility class for future use in other classes.
     * @param currentResistTrainExercise The resistance training exercise currently being modified.
     */
    public static void setCurrentResistTrainExercise(ResistTrainExercise currentResistTrainExercise) {
        SqliteConnection.currentResistTrainExercise = currentResistTrainExercise;
    }

    /**
     *
     * @return The current exercise information being modified by the user.
     */
    public static ExerciseInfo getCurrentExerciseInfo() {
        return currentExerciseInfo;
    }

    /**
     * Updates the current resistance training exercise of the application in this utility class for future use in other classes.
     * @param currentExerciseInfo The exercise information currently being modified.
     */
    public static void setCurrentExerciseInfo (ExerciseInfo currentExerciseInfo){
        SqliteConnection.currentExerciseInfo = currentExerciseInfo;
    }
}

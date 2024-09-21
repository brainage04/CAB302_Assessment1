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

    private SqliteConnection() {
        String url = "jdbc:sqlite:main.db";
        try {
            instance = DriverManager.getConnection(url);
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
    }

    public static Connection getInstance() {
        if (instance == null) {
            new SqliteConnection();
        }
        return instance;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        SqliteConnection.currentUser = currentUser;
    }

    public static void logOut() {
        setCurrentUser(null);
    }

    public static ResistTrainSession getCurrentResistTrainSession() {
        return currentResistTrainSession;
    }

    public static void setCurrentResistTrainSession(ResistTrainSession currentResistTrainSession) {
        SqliteConnection.currentResistTrainSession = currentResistTrainSession;
    }

    public static ResistTrainExercise getCurrentResistTrainExercise() {
        return currentResistTrainExercise;
    }

    public static void setCurrentResistTrainExercise(ResistTrainExercise currentResistTrainExercise) {
        SqliteConnection.currentResistTrainExercise = currentResistTrainExercise;
    }

    public static ExerciseInfo getCurrentExerciseInfo() { return currentExerciseInfo;}

    public static void setCurrentExerciseInfo ( ExerciseInfo currentExerciseInfo){
        SqliteConnection.currentExerciseInfo = currentExerciseInfo;
    }
}

package com.example.cab222a.model.resist_train;

import com.example.cab222a.common.SqliteConnection;
import com.example.cab222a.model.core.IObjectDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqliteResistTrainSetDAO extends IObjectDAO<ResistTrainSet> {
    @Override
    protected String tableName() {
        return "resist_train_sets";
    }

    @Override
    protected String createTableQuery() {
        return "CREATE TABLE IF NOT EXISTS " + tableName() + " ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "name VARCHAR NOT NULL,"
                + "weight INTEGER NOT NULL,"
                + "repetitions INTEGER NOT NULL,"
                + "rest_seconds INTEGER NOT NULL,"
                + "exercise_id INTEGER NOT NULL,"
                + "FOREIGN KEY (exercise_id) REFERENCES resist_train_exercises(id)"
                + ")";
    }

    @Override
    protected PreparedStatement addItemStatement(ResistTrainSet item) throws SQLException {
        PreparedStatement statement = SqliteConnection.getInstance().prepareStatement("INSERT INTO " + tableName() + " (name, weight, repetitions, rest_seconds, exercise_id) VALUES (?, ?, ?, ?, ?)");
        statement.setString(1, item.getName());
        statement.setInt(2, item.getWeight());
        statement.setInt(3, item.getRepetitions());
        statement.setInt(4, item.getRestSeconds());
        statement.setInt(5, SqliteConnection.getCurrentResistTrainExercise().getId());
        return statement;
    }

    @Override
    protected PreparedStatement updateItemStatement(ResistTrainSet item) throws SQLException {
        PreparedStatement statement = SqliteConnection.getInstance().prepareStatement("UPDATE " + tableName() + " SET name = ?, weight = ?, repetitions = ?, rest_seconds = ? WHERE id = ?");
        statement.setString(1, item.getName());
        statement.setInt(2, item.getWeight());
        statement.setInt(3, item.getRepetitions());
        statement.setInt(4, item.getRestSeconds());
        statement.setInt(5, item.getId());
        return statement;
    }

    @Override
    protected PreparedStatement getAllItemsStatement() throws SQLException {
        PreparedStatement statement = SqliteConnection.getInstance().prepareStatement("SELECT * FROM " + tableName() + " WHERE exercise_id = ?");
        statement.setInt(1, SqliteConnection.getCurrentResistTrainExercise().getId());
        return statement;
    }

    @Override
    public ResistTrainSet getItem(int id) {
        try {
            ResultSet set = getItemStatement(id).executeQuery();

            if (set.next()) {
                int weight = set.getInt("weight");
                String name = set.getString("name");
                int repetitions = set.getInt("repetitions");
                int restSeconds = set.getInt("rest_seconds");
                int exerciseId = set.getInt("exercise_id");

                return new ResistTrainSet(id, name, weight, repetitions, restSeconds, exerciseId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<ResistTrainSet> getAllItems() {
        List<ResistTrainSet> items = new ArrayList<>();

        try {
            ResultSet set = getAllItemsStatement().executeQuery();

            while (set.next()) {
                int id = set.getInt("id");
                String name = set.getString("name");
                int weight = set.getInt("weight");
                int repetitions = set.getInt("repetitions");
                int restSeconds = set.getInt("rest_seconds");
                int exerciseId = set.getInt("exercise_id");

                items.add(
                        new ResistTrainSet(id, name, weight, repetitions, restSeconds, exerciseId)
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }
}
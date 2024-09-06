package com.example.cab222a.model.resist_train;

import com.example.cab222a.common.SqliteConnection;
import com.example.cab222a.model.core.IObjectDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqliteResistTrainSetDAO implements IObjectDAO<ResistTrainSet> {
    private final Connection connection = SqliteConnection.getInstance();

    public SqliteResistTrainSetDAO() {
        createTable();
    }

    private void createTable() {
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS resist_train_sets ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "name VARCHAR NOT NULL,"
                    + "weight INTEGER NOT NULL,"
                    + "repetitions INTEGER NOT NULL,"
                    + "rest_seconds INTEGER NOT NULL,"
                    + "exercise_id INTEGER NOT NULL,"
                    + "FOREIGN KEY (exercise_id) REFERENCES resist_train_exercises(id)"
                    + ")";
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addItem(ResistTrainSet item) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO resist_train_sets (name, weight, repetitions, rest_seconds, exercise_id) VALUES (?, ?, ?, ?, ?)");
            statement.setString(1, item.getName());
            statement.setInt(2, item.getWeight());
            statement.setInt(3, item.getRepetitions());
            statement.setInt(4, item.getRestSeconds());
            statement.setInt(5, SqliteConnection.getCurrentResistTrainExercise().getId());
            statement.executeUpdate();

            // get auto incremented id from database
            ResultSet set = statement.getGeneratedKeys();
            if (set.next()) {
                item.setId(set.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateItem(ResistTrainSet item) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE resist_train_sets SET name = ?, weight = ?, repetitions = ?, rest_seconds = ? WHERE id = ?");
            statement.setString(1, item.getName());
            statement.setInt(2, item.getWeight());
            statement.setInt(3, item.getRepetitions());
            statement.setInt(4, item.getRestSeconds());
            statement.setInt(5, item.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteItem(ResistTrainSet item) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM resist_train_sets WHERE id = ?");
            statement.setInt(1, item.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ResistTrainSet getItem(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM resist_train_sets WHERE id = ?");
            statement.setInt(1, id);
            ResultSet set = statement.executeQuery();

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
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM resist_train_sets WHERE exercise_id = ?");
            statement.setInt(1, SqliteConnection.getCurrentResistTrainExercise().getId());
            ResultSet set = statement.executeQuery();

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
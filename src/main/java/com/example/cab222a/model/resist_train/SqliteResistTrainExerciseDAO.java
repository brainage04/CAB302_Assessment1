package com.example.cab222a.model.resist_train;

import com.example.cab222a.common.SqliteConnection;
import com.example.cab222a.model.core.IObjectDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqliteResistTrainExerciseDAO implements IObjectDAO<ResistTrainExercise> {
    private final Connection connection = SqliteConnection.getInstance();

    public SqliteResistTrainExerciseDAO() {
        createTable();
    }

    private void createTable() {
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS resist_train_exercises ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "name VARCHAR NOT NULL,"
                    + "session_id INTEGER NOT NULL,"
                    + "FOREIGN KEY (session_id) REFERENCES resist_train_sessions(id)"
                    + ")";
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addItem(ResistTrainExercise item) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO resist_train_exercises (name, session_id) VALUES (?, ?)");
            statement.setString(1, item.getName());
            statement.setInt(2, SqliteConnection.getCurrentResistTrainSession().getId());
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
    public void updateItem(ResistTrainExercise item) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE resist_train_exercises SET name = ? WHERE id = ?");
            statement.setString(1, item.getName());
            statement.setInt(2, item.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteItem(ResistTrainExercise item) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM resist_train_exercises WHERE id = ?");
            statement.setInt(1, item.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ResistTrainExercise getItem(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM resist_train_exercises WHERE id = ?");
            statement.setInt(1, id);
            ResultSet set = statement.executeQuery();

            if (set.next()) {
                String name = set.getString("name");
                int sessionId = set.getInt("session_id");

                return new ResistTrainExercise(id, name, sessionId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<ResistTrainExercise> getAllItems() {
        List<ResistTrainExercise> items = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM resist_train_exercises WHERE session_id = ?");
            statement.setInt(1, SqliteConnection.getCurrentResistTrainSession().getId());
            ResultSet set = statement.executeQuery();

            while (set.next()) {
                int id = set.getInt("id");
                String name = set.getString("name");
                int sessionId = set.getInt("session_id");

                items.add(
                        new ResistTrainExercise(id, name, sessionId)
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }
}
package com.example.cab222a.model;

import com.example.cab222a.common.SqliteConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqliteResistTrainSessionDAO implements IResistTrainSessionDAO {
    private Connection connection = SqliteConnection.getInstance();

    public SqliteResistTrainSessionDAO() {
        createTable();
    }

    private void createTable() {
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS resist_train_sessions ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "name VARCHAR NOT NULL,"
                    + "user_id INTEGER NOT NULL,"
                    + "FOREIGN KEY (user_id) REFERENCES users(id)"
                    + ")";
            statement.execute(query);
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    @Override
    public void addItem(ResistTrainSession item) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO resist_train_sessions (user_id, name) VALUES (?, ?)");
            statement.setInt(1, SqliteConnection.getCurrentUser().getId());
            statement.setString(2, item.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    @Override
    public void updateItem(ResistTrainSession item) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE resist_train_sessions SET name = ? WHERE id = ?");
            statement.setString(1, item.getName());
            statement.setInt(2, item.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    @Override
    public void deleteItem(ResistTrainSession item) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM resist_train_sessions WHERE id = ?");
            statement.setInt(1, item.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    @Override
    public ResistTrainSession getItem(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM resist_train_sessions WHERE id = ?");
            statement.setInt(1, id);
            ResultSet set = statement.executeQuery();

            if (set.next()) {
                String name = set.getString("name");
                int userId = set.getInt("user_id");

                return new ResistTrainSession(id, name, userId);
            }
        } catch (SQLException e) {
            System.err.println(e);
        }

        return null;
    }

    @Override
    public List<ResistTrainSession> getAllItems() {
        List<ResistTrainSession> sessions = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM resist_train_sessions WHERE user_id = ?");
            statement.setInt(1, SqliteConnection.getCurrentUser().getId());
            ResultSet set = statement.executeQuery();

            while (set.next()) {
                int id = set.getInt("id");
                String name = set.getString("name");
                int userId = set.getInt("user_id");

                sessions.add(
                        new ResistTrainSession(id, name, userId)
                );
            }
        } catch (SQLException e) {
            System.err.println(e);
        }

        return sessions;
    }
}
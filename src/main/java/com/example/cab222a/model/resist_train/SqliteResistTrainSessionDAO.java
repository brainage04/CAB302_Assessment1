package com.example.cab222a.model.resist_train;

import com.example.cab222a.common.SqliteConnection;
import com.example.cab222a.model.core.IObjectDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqliteResistTrainSessionDAO implements IObjectDAO<ResistTrainSession> {
    private final Connection connection = SqliteConnection.getInstance();

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
            e.printStackTrace();
        }
    }

    @Override
    public void addItem(ResistTrainSession item) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO resist_train_sessions (name, user_id) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, item.getName());
            statement.setInt(2, SqliteConnection.getCurrentUser().getId());
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
    public void updateItem(ResistTrainSession item) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE resist_train_sessions SET name = ? WHERE id = ?");
            statement.setString(1, item.getName());
            statement.setInt(2, item.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteItem(ResistTrainSession item) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM resist_train_sessions WHERE id = ?");
            statement.setInt(1, item.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<ResistTrainSession> getAllItems() {
        List<ResistTrainSession> items = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM resist_train_sessions WHERE user_id = ?");
            statement.setInt(1, SqliteConnection.getCurrentUser().getId());
            ResultSet set = statement.executeQuery();

            while (set.next()) {
                int id = set.getInt("id");
                String name = set.getString("name");
                int userId = set.getInt("user_id");

                items.add(
                        new ResistTrainSession(id, name, userId)
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }
}
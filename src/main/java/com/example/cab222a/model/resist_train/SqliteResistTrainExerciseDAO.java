package com.example.cab222a.model.resist_train;

import com.example.cab222a.common.SqliteConnection;
import com.example.cab222a.model.core.IObjectDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqliteResistTrainExerciseDAO extends IObjectDAO<ResistTrainExercise> {
    @Override
    protected String tableName() {
        return "resist_train_exercises";
    }

    @Override
    protected String createTableQuery() {
        return "CREATE TABLE IF NOT EXISTS " + tableName() + " ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "name VARCHAR NOT NULL,"
                + "session_id INTEGER NOT NULL,"
                + "FOREIGN KEY (session_id) REFERENCES resist_train_sessions(id)"
                + ")";
    }

    @Override
    protected PreparedStatement addItemStatement(ResistTrainExercise item) throws SQLException {
        PreparedStatement statement = SqliteConnection.getInstance().prepareStatement("INSERT INTO " + tableName() + " (name, session_id) VALUES (?, ?)");
        statement.setString(1, item.getName());
        statement.setInt(2, SqliteConnection.getCurrentResistTrainSession().getId());
        return statement;
    }

    @Override
    protected PreparedStatement updateItemStatement(ResistTrainExercise item) throws SQLException {
        PreparedStatement statement = SqliteConnection.getInstance().prepareStatement("UPDATE " + tableName() + " SET name = ? WHERE id = ?");
        statement.setString(1, item.getName());
        statement.setInt(2, item.getId());
        return statement;
    }

    @Override
    protected PreparedStatement getAllItemsStatement() throws SQLException {
        PreparedStatement statement = SqliteConnection.getInstance().prepareStatement("SELECT * FROM " + tableName() + " WHERE session_id = ?");
        statement.setInt(1, SqliteConnection.getCurrentResistTrainSession().getId());
        return statement;
    }

    @Override
    public ResistTrainExercise getItem(int id) {
        try {
            ResultSet set = getItemStatement(id).executeQuery();

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
            ResultSet set = getAllItemsStatement().executeQuery();

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
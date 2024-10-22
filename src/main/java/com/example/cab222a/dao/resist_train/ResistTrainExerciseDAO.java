package com.example.cab222a.dao.resist_train;

import com.example.cab222a.common.SqliteConnection;
import com.example.cab222a.dao.core.AbstractObjectDAO;
import com.example.cab222a.model.resist_train.ResistTrainExercise;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ResistTrainExerciseDAO extends AbstractObjectDAO<ResistTrainExercise> {

    @Override
    public String tableName() {
        return "resistTrainExercises";
    }

    @Override
    protected String createTableVariables() {
        return "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name VARCHAR NOT NULL, "
                + "sessionId INTEGER NOT NULL, "
                + "exerciseInfoId INTEGER NOT NULL, "
                + "FOREIGN KEY (sessionId) REFERENCES resistTrainSessions(id), "
                + "FOREIGN KEY (exerciseInfoId) REFERENCES exerciseInfo(id)";
    }

    @Override
    protected PreparedStatement addItemStatement(ResistTrainExercise item) throws SQLException {
        PreparedStatement statement = SqliteConnection.getInstance().prepareStatement(
                "INSERT INTO " + tableName() + " (name, sessionId, exerciseInfoId) VALUES (?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, item.getName());
        statement.setInt(2, SqliteConnection.getCurrentResistTrainSession().getId());
        statement.setInt(3, item.getExerciseInfoId());
        return statement;
    }

    @Override
    protected PreparedStatement updateItemStatement(ResistTrainExercise item) throws SQLException {
        PreparedStatement statement = SqliteConnection.getInstance().prepareStatement(
                "UPDATE " + tableName() + " SET name = ?, sessionId = ?, exerciseInfoId = ? WHERE id = ?");
        statement.setString(1, item.getName());
        statement.setInt(2, item.getSessionId());
        statement.setInt(3, item.getExerciseInfoId());
        statement.setInt(4, item.getId());
        return statement;
    }

    @Override
    protected PreparedStatement getAllItemsStatement() throws SQLException {
        PreparedStatement statement = SqliteConnection.getInstance().prepareStatement(
                "SELECT * FROM " + tableName() + " WHERE sessionId = ?");
        statement.setInt(1, SqliteConnection.getCurrentResistTrainSession().getId());
        return statement;
    }

    @Override
    public ResistTrainExercise getItem(int id) {
        try (ResultSet set = getItemStatement(id).executeQuery()) {
            if (set.next()) {
                String name = set.getString("name");
                int sessionId = set.getInt("sessionId");
                int exerciseInfoId = set.getInt("exerciseInfoId");

                return new ResistTrainExercise(id, name, sessionId, exerciseInfoId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<ResistTrainExercise> getAllItems() {
        List<ResistTrainExercise> items = new ArrayList<>();
        try (ResultSet set = getAllItemsStatement().executeQuery()) {
            while (set.next()) {
                int id = set.getInt("id");
                String name = set.getString("name");
                int sessionId = set.getInt("sessionId");
                int exerciseInfoId = set.getInt("exerciseInfoId");

                items.add(new ResistTrainExercise(id, name, sessionId, exerciseInfoId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    // This is the new method you should add for fetching all exercises for a specific session
    public List<ResistTrainExercise> getAllItemsForSession(int sessionId) {
        List<ResistTrainExercise> items = new ArrayList<>();
        try {
            PreparedStatement statement = SqliteConnection.getInstance().prepareStatement(
                    "SELECT * FROM " + tableName() + " WHERE sessionId = ?");
            statement.setInt(1, sessionId);
            ResultSet set = statement.executeQuery();

            while (set.next()) {
                int id = set.getInt("id");
                String name = set.getString("name");
                int exerciseInfoId = set.getInt("exerciseInfoId");

                items.add(new ResistTrainExercise(id, name, sessionId, exerciseInfoId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }
}

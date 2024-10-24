package com.example.cab222a.dao.resist_train;

import com.example.cab222a.common.SqliteConnection;
import com.example.cab222a.dao.core.AbstractObjectDAO;
import com.example.cab222a.model.resist_train.ResistTrainSet;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ResistTrainSetDAO extends AbstractObjectDAO<ResistTrainSet> {
    @Override
    public String tableName() {
        return "resistTrainSets";
    }

    @Override
    protected String createTableVariables() {
        return "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "exerciseId INTEGER NOT NULL, "
                + "name VARCHAR NOT NULL, "
                + "weight INTEGER NOT NULL, "
                + "reps INTEGER NOT NULL, "
                + "rest INTEGER, "
                + "repsInReserve INTEGER, "
                + "FOREIGN KEY (exerciseId) REFERENCES resistTrainExercises(id)";
    }

    @Override
    protected PreparedStatement addItemStatement(ResistTrainSet item) throws SQLException {
        PreparedStatement statement = SqliteConnection.getInstance().prepareStatement("INSERT INTO " + tableName() + " (name, exerciseId, weight, reps, rest, repsInReserve) VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, item.getName());
        statement.setInt(2, SqliteConnection.getCurrentResistTrainExercise().getId());
        statement.setInt(3, item.getWeight());
        statement.setInt(4, item.getReps());
        statement.setInt(5, item.getRest());
        statement.setInt(6, item.getRepsInReserve());
        return statement;
    }

    @Override
    protected PreparedStatement addCopiedItemStatement(ResistTrainSet item) throws SQLException {
        PreparedStatement statement = SqliteConnection.getInstance().prepareStatement("INSERT INTO " + tableName() + " (name, exerciseId, weight, reps, rest, repsInReserve) VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, item.getName());
        statement.setInt(2, item.getExerciseId());
        statement.setInt(3, item.getWeight());
        statement.setInt(4, item.getReps());
        statement.setInt(5, item.getRest());
        statement.setInt(6, item.getRepsInReserve());
        return statement;
    }

    @Override
    protected PreparedStatement updateItemStatement(ResistTrainSet item) throws SQLException {
        PreparedStatement statement = SqliteConnection.getInstance().prepareStatement("UPDATE " + tableName() + " SET name = ?, weight = ?, reps = ?, rest = ?, repsInReserve = ? WHERE id = ?");
        statement.setString(1, item.getName());
        statement.setInt(2, item.getWeight());
        statement.setInt(3, item.getReps());
        statement.setInt(4, item.getRest());
        statement.setInt(5, item.getRepsInReserve());
        statement.setInt(6, item.getId());
        return statement;
    }

    @Override
    protected PreparedStatement getAllItemsStatement() throws SQLException {
        PreparedStatement statement = SqliteConnection.getInstance().prepareStatement("SELECT * FROM " + tableName() + " WHERE exerciseId = ?");
        statement.setInt(1, SqliteConnection.getCurrentResistTrainExercise().getId());
        return statement;
    }

    // New method to get sets for a specific exercise
    public List<ResistTrainSet> getSetsForExercise(int exerciseId) {
        List<ResistTrainSet> sets = new ArrayList<>();
        try (
                PreparedStatement statement =
                        SqliteConnection.getInstance().prepareStatement(
                                "SELECT * FROM " + tableName() + " WHERE exerciseId = ?"
                        )
        ) {

            statement.setInt(1, exerciseId);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                int id = set.getInt("id");
                String name = set.getString("name");
                int weight = set.getInt("weight");
                int reps = set.getInt("reps");
                int rest = set.getInt("rest");
                int repsInReserve = set.getInt("repsInReserve");

                sets.add(new ResistTrainSet(id, name, exerciseId, weight, reps, rest, repsInReserve));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return sets;
    }

    @Override
    public ResistTrainSet getItem(int id) {
        try (ResultSet set = getItemStatement(id).executeQuery()) {
            if (set.next()) {
                String name = set.getString("name");
                int exerciseId = set.getInt("exerciseId");
                int weight = set.getInt("weight");
                int reps = set.getInt("reps");
                int rest = set.getInt("rest");
                int repsInReserve = set.getInt("repsInReserve");

                return new ResistTrainSet(id, name, exerciseId, weight, reps, rest, repsInReserve);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public List<ResistTrainSet> getAllItems() {
        List<ResistTrainSet> items = new ArrayList<>();

        try (ResultSet set = getAllItemsStatement().executeQuery()) {
            while (set.next()) {
                int id = set.getInt("id");
                String name = set.getString("name");
                int exerciseId = set.getInt("exerciseId");
                int weight = set.getInt("weight");
                int reps = set.getInt("reps");
                int rest = set.getInt("rest");
                int repsInReserve = set.getInt("repsInReserve");

                items.add(
                        new ResistTrainSet(id, name, exerciseId, weight, reps, rest, repsInReserve)
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return items;
    }
}

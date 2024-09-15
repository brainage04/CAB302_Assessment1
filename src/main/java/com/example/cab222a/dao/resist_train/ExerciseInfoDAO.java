package com.example.cab222a.dao.resist_train;

import com.example.cab222a.common.SqliteConnection;
import com.example.cab222a.dao.core.AbstractObjectDAO;
import com.example.cab222a.model.resist_train.ExerciseInfo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExerciseInfoDAO extends AbstractObjectDAO<ExerciseInfo> {
    @Override
    protected String tableName() {
        return "exerciseInfo";
    }

    @Override
    protected String createTableVariables() {
        return "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "userId INTEGER NOT NULL, "
                + "name VARCHAR NOT NULL, "
                + "primaryMuscleGroups VARCHAR NOT NULL, "
                + "secondaryMuscleGroups VARCHAR NOT NULL, "
                + "description VARCHAR NOT NULL, "
                + "FOREIGN KEY (userId) REFERENCES users(id)";
    }

    @Override
    protected PreparedStatement addItemStatement(ExerciseInfo item) throws SQLException {
        PreparedStatement statement = SqliteConnection.getInstance().prepareStatement("INSERT INTO " + tableName() + " (userId, name, primaryMuscleGroups, secondaryMuscleGroups, description) VALUES (?, ?, ?, ?, ?)");
        statement.setInt(1, SqliteConnection.getCurrentUser().getId());
        statement.setString(2, item.getName());
        statement.setString(3, item.getPrimaryMuscleGroups());
        statement.setString(4, item.getSecondaryMuscleGroups());
        statement.setString(5, item.getDescription());
        return statement;
    }

    @Override
    protected PreparedStatement updateItemStatement(ExerciseInfo item) throws SQLException {
        PreparedStatement statement = SqliteConnection.getInstance().prepareStatement("UPDATE " + tableName() + " SET name = ?, primaryMuscleGroups = ?, secondaryMuscleGroups = ?, description = ? WHERE id = ?");
        statement.setString(1, item.getName());
        statement.setString(2, item.getPrimaryMuscleGroups());
        statement.setString(3, item.getSecondaryMuscleGroups());
        statement.setString(4, item.getDescription());
        statement.setInt(5, item.getId());
        return statement;
    }

    @Override
    protected PreparedStatement getAllItemsStatement() throws SQLException {
        return SqliteConnection.getInstance().prepareStatement("SELECT * FROM " + tableName());
    }

    @Override
    public int addAndGetId(ExerciseInfo item) {
        try (ResultSet set = addItemStatement(item).executeQuery()) {
            if (set.next()) {
                return set.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public ExerciseInfo getItem(int id) {
        try (ResultSet set = getItemStatement(id).executeQuery()) {
            if (set.next()) {
                String name = set.getString("name");
                String primaryMuscleGroups = set.getString("primaryMuscleGroups");
                String secondaryMuscleGroups = set.getString("secondaryMuscleGroups");
                String description = set.getString("description");

                return new ExerciseInfo(id, name, primaryMuscleGroups, secondaryMuscleGroups, description);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<ExerciseInfo> getAllItems() {
        List<ExerciseInfo> items = new ArrayList<>();

        try (ResultSet set = getAllItemsStatement().executeQuery()) {
            while (set.next()) {
                int id = set.getInt("id");
                String name = set.getString("name");
                String primaryMuscleGroups = set.getString("primaryMuscleGroups");
                String secondaryMuscleGroups = set.getString("secondaryMuscleGroups");
                String description = set.getString("description");

                items.add(
                    new ExerciseInfo(id, name, primaryMuscleGroups, secondaryMuscleGroups, description)
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }
}
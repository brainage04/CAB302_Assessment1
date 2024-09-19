package com.example.cab222a.dao.resist_train;

import com.example.cab222a.common.SqliteConnection;
import com.example.cab222a.dao.core.AbstractObjectDAO;
import com.example.cab222a.model.resist_train.ExerciseInfo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
        PreparedStatement statement = SqliteConnection.getInstance().prepareStatement("INSERT INTO " + tableName() + " (userId, name, primaryMuscleGroups, secondaryMuscleGroups, description) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, SqliteConnection.getCurrentUser().getId());
        statement.setString(2, item.getName());
        statement.setString(3, item.getPrimaryMuscleGroups());
        statement.setString(4, item.getSecondaryMuscleGroups());
        statement.setString(5, item.getDescription());
        return statement;
    }

    // Does not used the current users' ID for when the table is initially created.
    protected PreparedStatement addDefaultItemStatement(ExerciseInfo item) throws SQLException {
        PreparedStatement statement = SqliteConnection.getInstance().prepareStatement("INSERT INTO " + tableName() + " (userId, name, primaryMuscleGroups, secondaryMuscleGroups, description) VALUES (?, ?, ?, ?, ?)");
        statement.setInt(1, item.getUserId());
        statement.setString(2, item.getName());
        statement.setString(3, item.getPrimaryMuscleGroups());
        statement.setString(4, item.getSecondaryMuscleGroups());
        statement.setString(5, item.getDescription());
        return statement;
    }

    /**
     * Adds a new item to the database.
     *
     * @param item The item to add.
     * @return The number of rows affected by the statement.
     */
    public int addDefaultItem(ExerciseInfo item) {
        try {
            return addDefaultItemStatement(item).executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
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

    public List<ExerciseInfo> searchExerciseInfo(String query) {
        return getAllItems()
                .stream()
                .filter(exerciseInfo -> isExerciseInfoMatched(exerciseInfo, query))
                .toList();
    }

    private boolean isExerciseInfoMatched ( ExerciseInfo exerciseInfo, String query){
        if (query == null || query.isEmpty()) return true;
        query = query.toLowerCase();
        String searchString = exerciseInfo.getName()
                + " " + exerciseInfo.getPrimaryMuscleGroups()
                + " " + exerciseInfo.getSecondaryMuscleGroups();
        return searchString.toLowerCase().contains(query);
    }

    @Override
    public void createTable() {
        try (Statement statement = SqliteConnection.getInstance().createStatement()){
            statement.execute("CREATE TABLE IF NOT EXISTS " + tableName() + " (" + createTableVariables() + ")");

            if(isTableEmpty()) {
                addDefaultExercises();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean isTableEmpty() throws SQLException {
        String query = "SELECT COUNT (*) AS count FROM " + tableName();
        try (ResultSet result = SqliteConnection.getInstance().createStatement().executeQuery(query)){
            // Count returns the number of rows in a table form
            // If the value in the first/only column is a 0 then return true
            // else return false
            if(result.getInt(1) == 0) {
                return true;
            }
        }
        return false;
    }

    private void addDefaultExercises() throws SQLException {
        addDefaultItem(new ExerciseInfo(-1, "Bench Press", "Chest", "Triceps", "Bench press is a chest fundamental.", -1));
        addDefaultItem(new ExerciseInfo(-1, "Deadlift", "Back", "Hamstrings", "Deadlift is a back fundamental.", -1));
        addDefaultItem(new ExerciseInfo(-1, "Squat", "Legs", "Glutes", "Squat is a lower body fundamental.", -1));
        addDefaultItem(new ExerciseInfo(-1, "Calf Raisers", "Calf", "", "Build them calves", -1));
    }

//    @Override
//    public void createTable() {
//        super.createTable();
//        addItem(new ExerciseInfo(-1, "Bench Press", "Chest", "Triceps", "Bench press is a chest fundamental."));
//    }

//    @Override
//    public void createTable() {
//        try (Statement statement = SqliteConnection.getInstance().createStatement()) {
//            statement.execute("CREATE TABLE IF NOT EXISTS " + tableName() + " (" + createTableVariables() + ")");
//            addItem(new ExerciseInfo(-1, "Bench Press", "Chest", "Triceps", "Bench press is a chest fundamental."));
//            addItem(new ExerciseInfo(-1, "Deadlift", "Back", "Hamstrings", "Deadlift is a back fundamental."));
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }



}
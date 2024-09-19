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
                + "name VARCHAR UNIQUE NOT NULL, "
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

    protected PreparedStatement getItemStatement(String name) throws SQLException {
        PreparedStatement statement = SqliteConnection.getInstance().prepareStatement("SELECT * FROM " + tableName() + " WHERE name = ?");
        statement.setString(1, name);
        return statement;
    }

    public ExerciseInfo getItem(String name) {
        try (ResultSet set = getItemStatement(name).executeQuery()) {
            if (set.next()) {
                int id = set.getInt("id");
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
        addDefaultItem(new ExerciseInfo(-1, "Barbell Bench Press", "Chest", "Triceps, Shoulders", "Barbell bench press is essential for chest development, also working triceps and shoulders.", -1));
        addDefaultItem(new ExerciseInfo(-1, "Dumbbell Bench Press", "Chest", "Triceps, Shoulders", "Dumbbell bench press allows a greater range of motion for the chest, engaging stabilizer muscles.", -1));
        addDefaultItem(new ExerciseInfo(-1, "Incline Barbell Bench Press", "Upper Chest", "Triceps, Shoulders", "Incline bench press emphasizes the upper chest while working triceps and shoulders.", -1));
        addDefaultItem(new ExerciseInfo(-1, "Incline Dumbbell Bench Press", "Upper Chest", "Triceps, Shoulders", "Incline dumbbell press helps focus on the upper chest with a more natural range of motion.", -1));
        addDefaultItem(new ExerciseInfo(-1, "Decline Barbell Bench Press", "Lower Chest", "Triceps, Shoulders", "Decline bench press focuses on the lower chest muscles.", -1));
        addDefaultItem(new ExerciseInfo(-1, "Decline Dumbbell Bench Press", "Lower Chest", "Triceps, Shoulders", "Decline dumbbell press works the lower chest while providing stabilization benefits.", -1));
        addDefaultItem(new ExerciseInfo(-1, "Barbell Deadlift", "Back", "Hamstrings, Glutes", "Barbell deadlift targets the entire posterior chain for overall strength.", -1));
        addDefaultItem(new ExerciseInfo(-1, "Romanian Deadlift", "Hamstrings", "Glutes, Lower Back", "Romanian deadlifts focus on the hamstrings and glutes while strengthening the lower back.", -1));
        addDefaultItem(new ExerciseInfo(-1, "Dumbbell Deadlift", "Back", "Hamstrings, Glutes", "Dumbbell deadlift works the same muscles as the barbell version but with increased stabilization demands.", -1));
        addDefaultItem(new ExerciseInfo(-1, "Sumo Deadlift", "Legs", "Glutes, Lower Back", "Sumo deadlift places more emphasis on the legs and glutes due to the wider stance.", -1));
        addDefaultItem(new ExerciseInfo(-1, "Conventional Deadlift", "Back", "Hamstrings, Core", "Conventional deadlift builds overall body strength, focusing on the back and core.", -1));
        addDefaultItem(new ExerciseInfo(-1, "Barbell Squat", "Legs", "Glutes, Core", "Barbell squats are fundamental for building leg and glute strength.", -1));
        addDefaultItem(new ExerciseInfo(-1, "Dumbbell Squat", "Legs", "Glutes, Core", "Dumbbell squats target the same muscles as the barbell squat but with increased stabilization.", -1));
        addDefaultItem(new ExerciseInfo(-1, "Front Squat", "Legs", "Core, Upper Back", "Front squats focus on quads and core with added upper body involvement for stabilization.", -1));
        addDefaultItem(new ExerciseInfo(-1, "Bulgarian Split Squat", "Legs", "Glutes, Core", "Bulgarian split squats isolate each leg for unilateral strength and stability.", -1));
        addDefaultItem(new ExerciseInfo(-1, "Leg Press", "Legs", "Glutes", "Leg press machine focuses on leg and glute development with reduced back strain.", -1));
        addDefaultItem(new ExerciseInfo(-1, "Seated Leg Curl", "Hamstrings", "", "Seated leg curls target the hamstrings while supporting the back.", -1));
        addDefaultItem(new ExerciseInfo(-1, "Lying Leg Curl", "Hamstrings", "", "Lying leg curls isolate the hamstrings for more focused contraction.", -1));
        addDefaultItem(new ExerciseInfo(-1, "Standing Calf Raise", "Calves", "", "Standing calf raises build lower leg strength, focusing on the gastrocnemius.", -1));
        addDefaultItem(new ExerciseInfo(-1, "Seated Calf Raise", "Calves", "", "Seated calf raises isolate the soleus muscle for calf definition.", -1));
        addDefaultItem(new ExerciseInfo(-1, "Overhead Barbell Press", "Shoulders", "Triceps, Upper Chest", "Overhead barbell press builds shoulder strength with tricep involvement.", -1));
        addDefaultItem(new ExerciseInfo(-1, "Overhead Dumbbell Press", "Shoulders", "Triceps", "Overhead dumbbell press offers more range of motion and stabilizer engagement.", -1));
        addDefaultItem(new ExerciseInfo(-1, "Arnold Press", "Shoulders", "Triceps", "Arnold press targets all three heads of the shoulder muscles with rotation.", -1));
        addDefaultItem(new ExerciseInfo(-1, "Barbell Curl", "Biceps", "Forearms", "Barbell curls build mass in the biceps, with forearm engagement.", -1));
        addDefaultItem(new ExerciseInfo(-1, "Dumbbell Curl", "Biceps", "Forearms", "Dumbbell curls allow for supination of the wrist, enhancing biceps peak.", -1));
        addDefaultItem(new ExerciseInfo(-1, "Hammer Curl", "Biceps", "Forearms", "Hammer curls emphasize the brachialis and forearms for balanced arm development.", -1));
        addDefaultItem(new ExerciseInfo(-1, "Concentration Curl", "Biceps", "", "Concentration curls isolate the biceps for focused contraction.", -1));
        addDefaultItem(new ExerciseInfo(-1, "Preacher Curl", "Biceps", "", "Preacher curls eliminate momentum, focusing entirely on biceps.", -1));
        addDefaultItem(new ExerciseInfo(-1, "Close-Grip Bench Press", "Triceps", "Chest, Shoulders", "Close-grip bench press targets the triceps while engaging the chest.", -1));
        addDefaultItem(new ExerciseInfo(-1, "Skull Crushers", "Triceps", "", "Skull crushers isolate the triceps, helping build upper arm strength.", -1));
        addDefaultItem(new ExerciseInfo(-1, "Tricep Kickback", "Triceps", "", "Tricep kickbacks provide isolated triceps activation for definition.", -1));
        addDefaultItem(new ExerciseInfo(-1, "Cable Tricep Pushdown", "Triceps", "", "Cable pushdowns offer constant tension for effective triceps development.", -1));
        addDefaultItem(new ExerciseInfo(-1, "Face Pull", "Shoulders", "Rear Delts, Upper Back", "Face pulls help improve shoulder stability and posture.", -1));
        addDefaultItem(new ExerciseInfo(-1, "Barbell Row", "Back", "Biceps, Rear Delts", "Barbell rows strengthen the upper back and biceps.", -1));
        addDefaultItem(new ExerciseInfo(-1, "Dumbbell Row", "Back", "Biceps, Rear Delts", "Dumbbell rows engage more stabilizers while building upper back strength.", -1));
        addDefaultItem(new ExerciseInfo(-1, "T-Bar Row", "Back", "Biceps", "T-bar rows are great for thickening the mid-back muscles.", -1));
        addDefaultItem(new ExerciseInfo(-1, "Seated Cable Row", "Back", "Biceps", "Seated cable rows offer controlled contraction for the upper back.", -1));
        addDefaultItem(new ExerciseInfo(-1, "Lat Pulldown", "Back", "Biceps", "Lat pulldowns develop the lat muscles and improve pulling strength.", -1));
        addDefaultItem(new ExerciseInfo(-1, "Pull-Up", "Back", "Biceps, Core", "Pull-ups are a compound exercise for the back and biceps.", -1));
        addDefaultItem(new ExerciseInfo(-1, "Chin-Up", "Back", "Biceps", "Chin-ups emphasize the biceps more than pull-ups.", -1));
        addDefaultItem(new ExerciseInfo(-1, "Plank", "Core", "Shoulders, Back", "Planks strengthen the core and stabilize the shoulders.", -1));
        addDefaultItem(new ExerciseInfo(-1, "Hanging Leg Raise", "Abs", "Hip Flexors", "Hanging leg raises target the lower abs and improve core strength.", -1));
        addDefaultItem(new ExerciseInfo(-1, "Russian Twist", "Core", "Obliques", "Russian twists strengthen the obliques and improve rotational strength.", -1));
        addDefaultItem(new ExerciseInfo(-1, "Ab Wheel Rollout", "Core", "Abs, Lower Back", "Ab wheel rollouts engage the entire core, emphasizing the abs.", -1));
        addDefaultItem(new ExerciseInfo(-1, "Cable Crunch", "Abs", "", "Cable crunches focus on building upper abdominal strength.", -1));
        addDefaultItem(new ExerciseInfo(-1, "Farmer's Walk", "Forearms", "Core, Shoulders", "Farmer's walks improve grip strength and core stability.", -1));

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
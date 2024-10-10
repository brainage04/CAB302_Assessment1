package com.example.cab222a.dao.resist_train;

import com.example.cab222a.common.SqliteConnection;
import com.example.cab222a.dao.core.AbstractObjectDAO;
import com.example.cab222a.model.resist_train.ExerciseInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ExerciseInfoDAO class is used to handle CRUD operations related to the ExerciseInfo SQL table in the database.
 * This class provides methods for adding, updating, retrieving, searching and finding alternative ExerciseInfo.
 */
public class ExerciseInfoDAO extends AbstractObjectDAO<ExerciseInfo> {
    /**
     * Returns name of the table in the database.
     * @return Table name as a string
     */
    @Override
    public String tableName() {
        return "exerciseInfo";
    }

    /**
     * Returns the SQL string for creating the ExerciseInfo table with its variables.
     * This is then used for the create table method.
     * @return SQL creation statement for table.
     */
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

    /**
     * Prepares a SQL statement to add a new exercise for the current user.
     * @param item ExerciseInfo object to be added
     * @return A statement to be executed.
     * @throws SQLException if an error occurs.
     */
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

    /**
     * Prepares a SQL statement to add default exercises where userId will be -1.
     * This method is used to initialise default exercises.
     * @param item The ExerciseInfo object to be added as a default exercise.
     * @return A statement ready for execution.
     * @throws SQLException if an error occurs.
     */
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
     * @param item The ExerciseInfo item to add.
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
        PreparedStatement statement = SqliteConnection.getInstance()
                .prepareStatement("SELECT * FROM " + tableName() + " WHERE userId = ? OR userId = -1");
        statement.setInt(1, SqliteConnection.getCurrentUser().getId());
        return statement;
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

    /**
     * Prepares  a SQL statement to retrieve an exercise based on the provided exercise name.
     * @param name The name of hte exercise to retireve from the database.
     * @return A statement that contains the SQL query to find the exercise based on the name.
     * @throws SQLException if an error occurs.
     */
    protected PreparedStatement getItemStatement(String name) throws SQLException {
        PreparedStatement statement = SqliteConnection.getInstance().prepareStatement("SELECT * FROM " + tableName() + " WHERE name = ?");
        statement.setString(1, name);
        return statement;
    }

    /**
     * Retrieves the ExerciseInfo object from the database base on the getItemStatement exercise.
     * @param name The name of the exercise to retrieve
     * @return An ExerciseInfo object containing its details if found or null.
     */
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

    /**
     * Retrieves all ExerciseInfo items associated with the current user + the default exercises.
     * @return A list of ExerciseInfo objects of the user.
     */
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

                items.add(new ExerciseInfo(id, name, primaryMuscleGroups, secondaryMuscleGroups, description));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }

    /**
     * Searches for exercises that match the query string.
     * Search looks for matches in the exercise name, primary muscle group or secondary muscle group.
     * @param query The search query string
     * @return A list of ExerciseInfo objects that match the query.
     */
    public List<ExerciseInfo> searchExerciseInfo(String query) {
        return getAllItems()
                .stream()
                .filter(exerciseInfo -> isExerciseInfoMatched(exerciseInfo, query))
                .toList();
    }

    /**
     * Determines if an ExerciseInfo object matches the search query.
     * @param exerciseInfo The ExerciseInfo object to check.
     * @param query The search query string
     * @return True if the ExerciseInfo objects matches the query, otherwise false.
     */
    private boolean isExerciseInfoMatched(ExerciseInfo exerciseInfo, String query) {
        if (query == null || query.isEmpty()) return true;
        query = query.toLowerCase();
        String searchString = exerciseInfo.getName()
                + " " + exerciseInfo.getPrimaryMuscleGroups()
                + " " + exerciseInfo.getSecondaryMuscleGroups();
        return searchString.toLowerCase().contains(query);
    }

    /**
     * Creates the exerciseInfo table in the database if it does not exist.
     * If the table is created and empty, default exercises are added ot the table.
     */
    @Override
    public void createTable() {
        try (Statement statement = SqliteConnection.getInstance().createStatement()){
            statement.execute("CREATE TABLE IF NOT EXISTS " + tableName() + " (" + createTableVariables() + ")");

            if (isTableEmpty()) {
                addDefaultExercises();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if the table is empty
     * @return True if there are no rows in the table, otherwise false.
     * @throws SQLException if an error occurs.
     */
    private boolean isTableEmpty() throws SQLException {
        String query = "SELECT COUNT (*) AS count FROM " + tableName();
        try (ResultSet result = SqliteConnection.getInstance().createStatement().executeQuery(query)) {
            // Count returns the number of rows in a table form
            // If the value in the first/only column is a 0 then return true
            // else return false
            if (result.getInt(1) == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * A list of default exercises for the user.
     * Uses an userId of -1.
     * @throws SQLException if an error occurs.
     */
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

        addDefaultItem(new ExerciseInfo(-1, "Barbell 45° Hyperextension", "Lower Back", "Glutes, Hamstrings", "Hyperextensions strengthen the lower back, glutes, and hamstrings.", -1));
        addDefaultItem(new ExerciseInfo(-1, "Hack Squat", "Legs", "Glutes", "Hack squat emphasizes the quads while reducing strain on the lower back.", -1));
        addDefaultItem(new ExerciseInfo(-1, "Glute Ham Raise", "Hamstrings", "Glutes, Lower Back", "Glute ham raise is a challenging bodyweight exercise focused on hamstrings and glutes.", -1));
        addDefaultItem(new ExerciseInfo(-1, "Smith Machine Reverse Lunge", "Legs", "Glutes", "Reverse lunges in the Smith machine help improve stability and target quads and glutes.", -1));
        addDefaultItem(new ExerciseInfo(-1, "Dumbbell Walking Lunge", "Legs", "Glutes", "Walking lunges with dumbbells engage the quads, glutes, and hamstrings dynamically.", -1));
        addDefaultItem(new ExerciseInfo(-1, "Cable Pull-Through", "Glutes", "Hamstrings", "Cable pull-throughs isolate the glutes and hamstrings in a hinge movement.", -1));
        addDefaultItem(new ExerciseInfo(-1, "Glute Kickback", "Glutes", "", "Glute kickbacks isolate and target the glutes.", -1));
        addDefaultItem(new ExerciseInfo(-1, "Deficit Deadlift", "Back", "Glutes, Hamstrings", "Deficit deadlifts increase range of motion, emphasizing hamstring and glute development.", -1));
        addDefaultItem(new ExerciseInfo(-1, "Block Pull", "Back", "Glutes, Hamstrings", "Block pulls reduce the range of motion in deadlifts, focusing on the lockout.", -1));
        addDefaultItem(new ExerciseInfo(-1, "Dumbbell Step-Up", "Legs", "Glutes", "Step-ups with dumbbells target the quads and glutes.", -1));
        addDefaultItem(new ExerciseInfo(-1, "Eccentric Overloaded Leg Extension", "Quads", "", "Leg extensions focus on strengthening the quads with an emphasis on eccentric control.", -1));
        addDefaultItem(new ExerciseInfo(-1, "Goblet Squat", "Legs", "Glutes", "Goblet squats are a beginner-friendly squat variation targeting quads and glutes.", -1));
        addDefaultItem(new ExerciseInfo(-1, "Safety Bar Squat", "Legs", "Core", "Safety bar squats reduce shoulder strain while emphasizing quads.", -1));
        addDefaultItem(new ExerciseInfo(-1, "Good Morning", "Lower Back", "Hamstrings", "Good mornings strengthen the lower back and hamstrings through a hip hinge movement.", -1));
        addDefaultItem(new ExerciseInfo(-1, "Hip Thrust", "Glutes", "Hamstrings", "Hip thrusts are a key glute isolation exercise.", -1));
        addDefaultItem(new ExerciseInfo(-1, "Glute Bridge", "Glutes", "Hamstrings", "Glute bridges activate the glutes and hamstrings in a hip thrust movement.", -1));
        addDefaultItem(new ExerciseInfo(-1, "Knee-Banded Leg Press", "Legs", "Glutes", "Knee-banded leg press adds glute activation to the traditional leg press.", -1));
        addDefaultItem(new ExerciseInfo(-1, "Leg Press Calf Press", "Calves", "", "Calf presses on the leg press machine target the entire calf muscle group.", -1));
        addDefaultItem(new ExerciseInfo(-1, "Stiff Leg Deadlift", "Hamstrings", "Lower Back", "Stiff leg deadlifts increase hamstring engagement by reducing knee bend.", -1));
    }

    /**
     * Finds alternative exercises based on the target exercise's primary and secondary muscle group.
     * @param exerciseName The name of the target exercise to find alternatives of.
     * @return The list of alternative exercises of the target exercise.
     */
    public List<ExerciseInfo> findAlternatives(String exerciseName) {
        // Find the exercise info for the specified exercise name
        ExerciseInfo targetExercise = getAllItems()
                .stream()
                .filter(exerciseInfo -> exerciseInfo.getName().equalsIgnoreCase(exerciseName))
                .findFirst()
                .orElse(null);

        // Find alternatives based on muscle
        // There is only 1 Primary muscle for each exercise
        assert targetExercise != null;
        String targetPrimaryMuscles = targetExercise.getPrimaryMuscleGroups();

        String secondaryMuscleGroups = targetExercise.getSecondaryMuscleGroups();

        String[] targetSecondaryMuscles;
        if (secondaryMuscleGroups != null && !secondaryMuscleGroups.isEmpty()) {
            targetSecondaryMuscles = secondaryMuscleGroups.split(",\\s*");
        } else {
            targetSecondaryMuscles = new String[0]; // Length == 0, empty array
        }

        return getAllItems()
                .stream()
                .filter(exerciseInfo ->
                        !exerciseInfo.getName().equalsIgnoreCase(exerciseName) && // Exclude target exercise
                                isAlternativeMatched(exerciseInfo, targetPrimaryMuscles, targetSecondaryMuscles))
                .toList();
    }

    /**
     * Checks if exercise in the list is an alternative based on the muscles.
     * Method compares the primary and secondary muscle groups of the target exercise.
     * Primary muscle group of the target exercise has to be part of the exercise.
     * @param exerciseInfo The ExerciseInfo object to be compared.
     * @param targetPrimaryMuscles Primary muscle group of the target exercise.
     * @param targetSecondaryMuscles Secondary muscle groups of the target exercise.
     * @return True if criteria is met, otherwise false.
     */
    private boolean isAlternativeMatched(ExerciseInfo exerciseInfo, String targetPrimaryMuscles, String[] targetSecondaryMuscles) {
        String exercisePrimaryMuscle = exerciseInfo.getPrimaryMuscleGroups();
        String[] exerciseSecondaryMuscles = exerciseInfo.getSecondaryMuscleGroups().split(",\\s*");

        // Constraint where the Primary Muscle of the target Exercise is required
        // In this case it is if the Primary Muscle is not in either the Primary Muscle or Secondary muscle return false.
        if (!exercisePrimaryMuscle.equalsIgnoreCase(targetPrimaryMuscles) &&
                Arrays.stream(exerciseSecondaryMuscles).noneMatch(muscle -> muscle.equalsIgnoreCase(targetPrimaryMuscles))) {
            return false;
        }

        int matchCount = 0;

        // Check if primary muscle matches the target's primary muscle
        if (exercisePrimaryMuscle.equalsIgnoreCase(targetPrimaryMuscles)) {
            matchCount++;
        }

        // Check if primary muscle matches any of the target's secondary muscles
        if (Arrays.asList(targetSecondaryMuscles).contains(exercisePrimaryMuscle)) {
            matchCount++;
        }

        // Check how many secondary muscles match the target's primary or secondary muscles
        matchCount += (int) Arrays.stream(exerciseSecondaryMuscles)
                .filter(muscle -> muscle.equalsIgnoreCase(targetPrimaryMuscles) || Arrays.asList(targetSecondaryMuscles).contains(muscle))
                .count();

        // Case 1: Only primary muscle group (no secondary muscles)
        if (targetSecondaryMuscles.length == 0) {
            return matchCount >= 1;
        }

        // Case 2: One or more secondary muscles, require at least two matches
        return matchCount >= 2;
    }
}
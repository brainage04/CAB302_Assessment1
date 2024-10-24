package com.example.cab222a.model.resist_train;

import com.example.cab222a.model.core.NamedObject;

/**
 * Represents the class and information about an exercise. This includes its primary muscle groups, secondary muscle groups,
 * a description and the userID of the user who created it.
 */
public class ExerciseInfo extends NamedObject {
    private int userId;
    private String primaryMuscleGroups;
    private String secondaryMuscleGroups;
    private String description;

    /**
     * Constructs a new ExerciseInfo object with the specified name, primary muscle group, secondary muscle groups and description.
     * Constructor currently has limited use.
     * @param name The name of the exercise.
     * @param primaryMuscleGroups The primary muscle group used by the exercise.
     * @param secondaryMuscleGroups The secondary muscle groups used by the exercise.
     * @param description A description of the exercise.
     */
    public ExerciseInfo(String name, String primaryMuscleGroups, String secondaryMuscleGroups, String description) {
        super(name);
        this.primaryMuscleGroups = primaryMuscleGroups;
        this.secondaryMuscleGroups = secondaryMuscleGroups;
        this.description = description;
    }

    /**
     * Constructs a new ExerciseInfo object with the specified id, name, primary muscle group, secondary muscle groups and description.
     * Constructor is used in the get item methods in the ExerciseInfoDAO.
     * @param id The unique identifier of the exercise.
     * @param name The name of the exercise.
     * @param primaryMuscleGroups The primary muscle group used by the exercise.
     * @param secondaryMuscleGroups The secondary muscle groups used by the exercise.
     * @param description A description of the exercise.
     */
    public ExerciseInfo(int id, String name, String primaryMuscleGroups, String secondaryMuscleGroups, String description) {
        super(id, name);
        this.primaryMuscleGroups = primaryMuscleGroups;
        this.secondaryMuscleGroups = secondaryMuscleGroups;
        this.description = description;
    }

    /**
     * Constructs a new ExerciseInfo object with the specified id, name, primary muscle group, secondary muscle groups, description and userID.
     * Constructor is used to create default exercises and user specific exercises.
     * @param id The unique identifier of the exercise.
     * @param name The name of the exercise.
     * @param primaryMuscleGroups The primary muscle group used by the exercise.
     * @param secondaryMuscleGroups The secondary muscle groups used by the exercise.
     * @param description A description of the exercise.
     * @param userId The ID of the user associated with the exercise.
     */
    public ExerciseInfo(int id, String name, String primaryMuscleGroups, String secondaryMuscleGroups, String description, int userId) {
        super(id, name);
        this.primaryMuscleGroups = primaryMuscleGroups;
        this.secondaryMuscleGroups = secondaryMuscleGroups;
        this.description = description;
        this.userId = userId;
    }

    /**
     * Gets the ID of the user associated with the exercise.
     * @return User ID associated.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the user ID associated with the exercise.
     * @param userId The userID to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets primary muscle groups of the exercise.
     * @return A string of the primary muscle group.
     */
    public String getPrimaryMuscleGroups() {
        return primaryMuscleGroups;
    }

    /**
     * Sets the primary muscle group of the exercise.
     * @param primaryMuscleGroups A string of the primary muscle group to set.
     */
    public void setPrimaryMuscleGroups(String primaryMuscleGroups) {
        this.primaryMuscleGroups = primaryMuscleGroups;
    }

    /**
     * Gets the secondary muscle groups of the exercise.
     * @return A string of the secondary muscle groups.
     */
    public String getSecondaryMuscleGroups() {
        return secondaryMuscleGroups;
    }

    /**
     * Sets the secondary muscle group of the exercise.
     * @param secondaryMuscleGroups A string of the secondary muscle group to set.
     */
    public void setSecondaryMuscleGroups(String secondaryMuscleGroups) {
        this.secondaryMuscleGroups = secondaryMuscleGroups;
    }

    /**
     * Gets the description of the exercise.
     * @return A string of the description of the exercise.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the exercise.
     * @param description A string of the description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }
}

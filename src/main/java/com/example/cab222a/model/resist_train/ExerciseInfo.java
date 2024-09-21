package com.example.cab222a.model.resist_train;

import com.example.cab222a.model.core.NamedObject;

public class ExerciseInfo extends NamedObject {
    private int userId;
    private String primaryMuscleGroups;
    private String secondaryMuscleGroups;
    private String description;

    public ExerciseInfo(String name, String primaryMuscleGroups, String secondaryMuscleGroups, String description) {
        super(name);
        this.primaryMuscleGroups = primaryMuscleGroups;
        this.secondaryMuscleGroups = secondaryMuscleGroups;
        this.description = description;
    }

    public ExerciseInfo(int id, String name, String primaryMuscleGroups, String secondaryMuscleGroups, String description) {
        super(id, name);
        this.primaryMuscleGroups = primaryMuscleGroups;
        this.secondaryMuscleGroups = secondaryMuscleGroups;
        this.description = description;
    }

    // Constructor used to make default exercise info.
    public ExerciseInfo(int id, String name, String primaryMuscleGroups, String secondaryMuscleGroups, String description, int userId) {
        super(id, name);
        this.primaryMuscleGroups = primaryMuscleGroups;
        this.secondaryMuscleGroups = secondaryMuscleGroups;
        this.description = description;
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPrimaryMuscleGroups() {
        return primaryMuscleGroups;
    }

    public void setPrimaryMuscleGroups(String primaryMuscleGroups) {
        this.primaryMuscleGroups = primaryMuscleGroups;
    }

    public String getSecondaryMuscleGroups() {
        return secondaryMuscleGroups;
    }

    public void setSecondaryMuscleGroups(String secondaryMuscleGroups) {
        this.secondaryMuscleGroups = secondaryMuscleGroups;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

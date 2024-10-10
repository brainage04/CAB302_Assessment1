package com.example.cab222a.model.resist_train;


import com.example.cab222a.model.resist_train.ExerciseTemplate; // Import for ExerciseTemplate
import java.util.ArrayList;
import java.util.List;

public class WorkoutTemplate {
    private String workoutName;
    private List<ExerciseTemplate> exercises; // List of exercises in this workout template
    private String notes;

    // Constructor
    public WorkoutTemplate(String workoutName, String notes) {
        this.workoutName = workoutName;
        this.notes = notes;
        this.exercises = new ArrayList<>();
    }

    // Methods to add and remove exercises
    public void addExercise(ExerciseTemplate exercise) {
        exercises.add(exercise);
    }

    public void removeExercise(ExerciseTemplate exercise) {
        exercises.remove(exercise);
    }

    // Getters and setters
    public String getWorkoutName() {
        return workoutName;
    }

    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }

    public List<ExerciseTemplate> getExercises() {
        return exercises;
    }

    public void setExercises(List<ExerciseTemplate> exercises) {
        this.exercises = exercises;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}

package com.example.cab222a.controller;

import com.example.cab222a.model.resist_train.ExerciseTemplate;
import com.example.cab222a.model.resist_train.WorkoutTemplate;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class WorkoutTemplateController {

    @FXML
    private TextField workoutNameField;

    @FXML
    private TextField notesField;

    @FXML
    private ListView<ExerciseTemplate> exerciseListView;

    private WorkoutTemplate workoutTemplate;

    public WorkoutTemplateController() {
        this.workoutTemplate = new WorkoutTemplate("", "");
    }

    @FXML
    private void handleAddExercise() {
        // For simplicity, we’ll add a placeholder exercise. You might create a form to add specific details.
        ExerciseTemplate newExercise = new ExerciseTemplate("Squat", "Strength", 3, 10, 100, "");
        workoutTemplate.addExercise(newExercise);
        exerciseListView.getItems().add(newExercise); // Update the ListView to show the exercise
    }

    @FXML
    private void handleSaveWorkout() {
        workoutTemplate.setWorkoutName(workoutNameField.getText());
        workoutTemplate.setNotes(notesField.getText());

        // For now, show a confirmation alert when the workout is saved
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Workout Saved");
        alert.setHeaderText(null);
        alert.setContentText("Workout " + workoutTemplate.getWorkoutName() + " has been saved successfully.");
        alert.showAndWait();

        // Clear the fields after saving
        workoutNameField.clear();
        notesField.clear();
        exerciseListView.getItems().clear();
        workoutTemplate.getExercises().clear();
    }
}

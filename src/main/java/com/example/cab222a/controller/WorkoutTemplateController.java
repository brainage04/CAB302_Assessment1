package com.example.cab222a.controller;

import com.example.cab222a.model.resist_train.ExerciseTemplate;
import com.example.cab222a.model.resist_train.WorkoutTemplate;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class WorkoutTemplateController {

    @FXML
    private TextField workoutNameField;

    @FXML
    private ComboBox<String> exerciseChoiceBox;

    @FXML
    private ListView<ExerciseTemplate> exerciseListView;

    private WorkoutTemplate workoutTemplate;

    public WorkoutTemplateController() {
        this.workoutTemplate = new WorkoutTemplate("", "");
    }

    @FXML
    public void initialize() {
        // List of predefined exercises
        List<String> predefinedExercises = Arrays.asList("Squat", "Bench Press", "Deadlift", "Pull-up", "Bicep Curl");

        // Populate the ComboBox with predefined exercises
        exerciseChoiceBox.getItems().addAll(predefinedExercises);
    }

    @FXML
    private void handleAddExercise() {
        String selectedExercise = exerciseChoiceBox.getValue();

        // Check if an exercise is selected
        if (selectedExercise != null) {
            // Create a new ExerciseTemplate based on the selected exercise
            ExerciseTemplate newExercise = new ExerciseTemplate(selectedExercise, "Strength", 3, 10, 100, "");

            // Add to the workout template and update the ListView
            workoutTemplate.addExercise(newExercise);
            exerciseListView.getItems().add(newExercise);

            // Show confirmation alert
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Exercise Added");
            alert.setHeaderText(null);
            alert.setContentText("Exercise '" + newExercise.getName() + "' has been added.");
            alert.showAndWait();
        } else {
            // Show an alert if no exercise was selected
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("No Exercise Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select an exercise to add.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleSaveWorkout() {
        workoutTemplate.setWorkoutName(workoutNameField.getText());

        // Show a confirmation alert when the workout is saved
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Workout Saved");
        alert.setHeaderText(null);
        alert.setContentText("Workout '" + workoutTemplate.getWorkoutName() + "' has been saved successfully.");
        alert.showAndWait();

        // Clear the fields after saving
        workoutNameField.clear();
        exerciseListView.getItems().clear();
        workoutTemplate.getExercises().clear();
    }

    @FXML
    private void handleBackButtonClick() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/cab222a/main-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            Stage stage = (Stage) workoutNameField.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package com.example.cab222a.controller;

import com.example.cab222a.model.resist_train.ExerciseTemplate;
import com.example.cab222a.model.resist_train.WorkoutTemplate;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class WorkoutTemplateController {

    @FXML
    private TextField workoutNameField;

    @FXML
    private ComboBox<ExerciseTemplate> exerciseChoiceBox;

    @FXML
    private ListView<ExerciseTemplate> exerciseListView;

    private WorkoutTemplate workoutTemplate;

    public WorkoutTemplateController() {
        this.workoutTemplate = new WorkoutTemplate("", "");
    }

    @FXML
    public void initialize() {
        // Populate the ComboBox with some example exercises
        ObservableList<ExerciseTemplate> exercises = FXCollections.observableArrayList(
                new ExerciseTemplate("Squat", "Strength", 3, 10, 100, ""),
                new ExerciseTemplate("Push-up", "Strength", 3, 15, 0, ""),
                new ExerciseTemplate("Plank", "Core", 1, 60, 0, "")
        );
        exerciseChoiceBox.setItems(exercises);

        // Set the ComboBox to display the name of each ExerciseTemplate
        exerciseChoiceBox.setCellFactory(lv -> new ListCell<ExerciseTemplate>() {
            @Override
            protected void updateItem(ExerciseTemplate item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getName());
            }
        });
        exerciseChoiceBox.setButtonCell(new ListCell<ExerciseTemplate>() {
            @Override
            protected void updateItem(ExerciseTemplate item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getName());
            }
        });
    }

    @FXML
    private void handleAddExercise() {
        // Get the selected exercise from the ComboBox
        ExerciseTemplate selectedExercise = exerciseChoiceBox.getSelectionModel().getSelectedItem();

        if (selectedExercise != null) {
            // Add the selected exercise to the workout template and update the ListView
            workoutTemplate.addExercise(selectedExercise);
            exerciseListView.getItems().add(selectedExercise);

            System.out.println("Added exercise: " + selectedExercise.getName());
        } else {
            // Show an alert if no exercise is selected
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("No Exercise Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select an exercise to add.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleRemoveExercise() {
        // Get the selected exercise from the ListView
        ExerciseTemplate selectedExercise = exerciseListView.getSelectionModel().getSelectedItem();

        if (selectedExercise != null) {
            // Remove the selected exercise from both the workout template and the ListView
            workoutTemplate.getExercises().remove(selectedExercise);
            exerciseListView.getItems().remove(selectedExercise);

            System.out.println("Removed exercise: " + selectedExercise.getName());
        } else {
            // Show an alert if no exercise is selected
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("No Exercise Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select an exercise to remove.");
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
        alert.setContentText("Workout " + workoutTemplate.getWorkoutName() + " has been saved successfully.");
        alert.showAndWait();

        // Clear the fields after saving
        workoutNameField.clear();
        exerciseListView.getItems().clear();
        workoutTemplate.getExercises().clear();
    }

    @FXML
    private void handleBackButtonClick() {
        // Logic to go back to the previous view, if implemented
    }
}

package com.example.cab222a.controller;

import com.example.cab222a.model.resist_train.ExerciseTemplate;
import com.example.cab222a.model.resist_train.WorkoutTemplate;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;

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
        ObservableList<ExerciseTemplate> exercises = FXCollections.observableArrayList(
                new ExerciseTemplate("Squat", "Strength", 3, 10, 100, ""),
                new ExerciseTemplate("Push-up", "Strength", 3, 15, 0, ""),
                new ExerciseTemplate("Plank", "Core", 1, 60, 0, "")
        );
        exerciseChoiceBox.setItems(exercises);

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
        ExerciseTemplate selectedExercise = exerciseChoiceBox.getSelectionModel().getSelectedItem();
        if (selectedExercise != null) {
            workoutTemplate.addExercise(selectedExercise);
            exerciseListView.getItems().add(selectedExercise);
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("No Exercise Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select an exercise to add.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleRemoveExercise() {
        ExerciseTemplate selectedExercise = exerciseListView.getSelectionModel().getSelectedItem();
        if (selectedExercise != null) {
            workoutTemplate.getExercises().remove(selectedExercise);
            exerciseListView.getItems().remove(selectedExercise);
        } else {
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

        // Display a confirmation message
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Workout Saved");
        alert.setHeaderText(null);
        alert.setContentText("Workout " + workoutTemplate.getWorkoutName() + " has been saved successfully.");
        alert.showAndWait();

        // Optionally, clear the fields after saving
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

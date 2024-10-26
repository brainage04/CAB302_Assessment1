package com.example.cab222a.controller;

import com.example.cab222a.common.SqliteConnection;
import com.example.cab222a.controller.core.SqliteControllerFunctions;
import com.example.cab222a.dao.core.AbstractObjectDAO;
import com.example.cab222a.dao.resist_train.ExerciseInfoDAO;
import com.example.cab222a.dao.resist_train.ResistTrainExerciseDAO;
import com.example.cab222a.dao.resist_train.ResistTrainSetDAO;
import com.example.cab222a.model.resist_train.*;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Controller for the resistance training exercise view.
 */
public class ResistTrainExerciseController extends SqliteControllerFunctions<ResistTrainExercise> {

    @Override
    public AbstractObjectDAO<ResistTrainExercise> initItemDAO() {
        return new ResistTrainExerciseDAO();
    }

    @Override
    public String getNextSceneName() {
        return "resist-train-set-view.fxml";
    }

    @Override
    public String getPreviousSceneName() {
        return "resist-train-session-view.fxml";
    }

    @Override
    public ResistTrainExercise generateDefaultItem() {
        return new ResistTrainExercise("New Exercise", SqliteConnection.getCurrentResistTrainSession().getId(), 1);
    }

    @FXML
    private ComboBox<String> exerciseField;
    @FXML
    private Label heaviestSetLabel; // Label to show heaviest set details

    @Override
    protected void selectItem(ResistTrainExercise item) {
        super.selectItem(item);
        ExerciseInfo exerciseInfo = new ExerciseInfoDAO().getItem(item.getExerciseInfoId());
        exerciseField.setValue(exerciseInfo.getName());

        // Call the method to display the heaviest set for the selected exercise
        showHeaviestSet(item);
    }

    // Method to show the heaviest set for a selected exercise
    private void showHeaviestSet(ResistTrainExercise selectedExercise) {
        if (selectedExercise != null) {
            // Fetch the sets associated with the selected exercise
            List<ResistTrainSet> sets = new ResistTrainSetDAO().getSetsForExercise(selectedExercise.getId());

            // Find the heaviest set (based on weight)
            ResistTrainSet heaviestSet = sets.stream()
                    .max(Comparator.comparingDouble(ResistTrainSet::getWeight))
                    .orElse(null);

            if (heaviestSet != null) {
                String displayText = "Heaviest Set: " + heaviestSet.getWeight() + " kg";
                if (heaviestSet.getReps() > 1) {
                    displayText += " for " + heaviestSet.getReps() + " reps";
                }
                heaviestSetLabel.setText(displayText);
            } else {
                heaviestSetLabel.setText("No sets recorded.");
            }
        }
    }

    @Override
    protected void onEditConfirm() {
        // Get the selected item from the list view
        ResistTrainExercise selectedItem = getItemListView().getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            selectedItem.setName(getNameTextField().getText());

            String name = exerciseField.getValue();
            ExerciseInfo exerciseInfo = new ExerciseInfoDAO().getItem(name);

            selectedItem.setExerciseInfoId(exerciseInfo.getId());
            selectedItem.setName(exerciseInfo.getName());
            getItemDAO().updateItem(selectedItem);
            syncItems();
        }
    }

    @Override
    @FXML
    public void onEditButtonClick() throws IOException {
        ResistTrainExercise selectedItem = itemListView.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            return;
        }

        SqliteConnection.setCurrentResistTrainExercise(selectedItem);
        System.out.println("Resist train EXERCISE stored in memory:\n" + selectedItem);

        MainController.changeScene(editButton, nextScene);
    }

    @FXML
    @Override
    public void initialize() {
        Label itemLabel = new Label("Exercise Name:");
        setNameTextField(MainController.customTextField("nameTextField"));
        getGridPaneContainer().add(itemLabel, 0, 0);
        getGridPaneContainer().add(getNameTextField(), 1, 0);

        Label exerciseLabel = new Label("Exercise:");
        List<String> exerciseNames = new ExerciseInfoDAO().getAllItems().stream().map(ExerciseInfo::getName).toList();
        List<String> mutableExerciseNames = new ArrayList<>(exerciseNames);
        mutableExerciseNames.sort(String.CASE_INSENSITIVE_ORDER);
        exerciseField = new ComboBox<>(FXCollections.observableArrayList(mutableExerciseNames));
        exerciseField.setMaxWidth(Double.POSITIVE_INFINITY);
        getGridPaneContainer().add(exerciseLabel, 0, 1);
        getGridPaneContainer().add(exerciseField, 1, 1);

        // Add the label for showing the heaviest set
        heaviestSetLabel = new Label("No set selected.");
        getGridPaneContainer().add(heaviestSetLabel, 0, 2, 2, 1); // Spanning two columns

        super.initialize();
    }
}

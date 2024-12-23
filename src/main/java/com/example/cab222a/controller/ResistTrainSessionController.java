package com.example.cab222a.controller;

import com.example.cab222a.common.SqliteConnection;
import com.example.cab222a.controller.core.SqliteControllerFunctions;
import com.example.cab222a.dao.core.AbstractObjectDAO;
import com.example.cab222a.dao.resist_train.ExerciseInfoDAO;
import com.example.cab222a.dao.resist_train.ResistTrainExerciseDAO;
import com.example.cab222a.dao.resist_train.ResistTrainSetDAO;
import com.example.cab222a.model.resist_train.ExerciseInfo;
import com.example.cab222a.model.resist_train.ResistTrainExercise;
import com.example.cab222a.model.resist_train.ResistTrainSession;
import com.example.cab222a.dao.resist_train.ResistTrainSessionDAO;
import com.example.cab222a.model.resist_train.ResistTrainSet;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

public class ResistTrainSessionController extends SqliteControllerFunctions<ResistTrainSession> {
    @FXML
    private Label totalHeaviestSetsLabel;  // New label to display total weight

    @Override
    public AbstractObjectDAO<ResistTrainSession> initItemDAO() {
        return new ResistTrainSessionDAO();
    }
    @Override
    public String getNextSceneName() {
        return "resist-train-exercise-view.fxml";
    }
    @Override
    public String getPreviousSceneName() {
        return "main-view.fxml";
    }
    @Override
    public ResistTrainSession generateDefaultItem() {
        return new ResistTrainSession("New Session", SqliteConnection.getCurrentUser().getId(), new Date(System.currentTimeMillis()));
    }


    @Override
    protected void selectItem(ResistTrainSession item) {
        super.selectItem(item);
        updateTotalHeaviestSets(item);
    }

    // Method to calculate the total of the heaviest sets for all exercises in the session
    private void updateTotalHeaviestSets(ResistTrainSession session) {
        List<ResistTrainExercise> exercises = new ResistTrainExerciseDAO().getAllItemsForSession(session.getId());

        // Debug: Check how many exercises were found
        System.out.println("Exercises found: " + exercises.size());

        int totalWeight = exercises.stream()
                .map(exercise -> {
                    List<ResistTrainSet> sets = new ResistTrainSetDAO().getSetsForExercise(exercise.getId());

                    // Debug: Print how many sets were found for each exercise
                    System.out.println("Exercise ID: " + exercise.getId() + " - Sets found: " + sets.size());

                    return sets;
                })
                .map(sets -> sets.stream()
                        .max((set1, set2) -> Double.compare(set1.getWeight(), set2.getWeight()))
                        .map(ResistTrainSet::getWeight)
                        .orElse(0)) // Default to 0 if no sets found
                .mapToInt(Integer::intValue)
                .sum();

        // Debug: Print the total weight calculated
        System.out.println("Total Heaviest Sets Weight: " + totalWeight + " kg");

        totalHeaviestSetsLabel.setText("Total Heaviest Sets: " + totalWeight + " kg");
    }


    @Override
    @FXML
    public void onEditButtonClick() throws IOException {
        ResistTrainSession selectedItem = itemListView.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            return;
        }

        SqliteConnection.setCurrentResistTrainSession(selectedItem);
        System.out.println("Resist train SESSION stored in memory:\n" + selectedItem);

        // Update the total heaviest sets label
        updateTotalHeaviestSets(selectedItem);

        MainController.changeScene(editButton, nextScene);
    }

    public void copySession() {
        System.out.println("COPYING SESSION");

        ResistTrainSession oldSession = itemListView.getSelectionModel().getSelectedItem();

        // copy session, add to DB, get session ID for exercises
        ResistTrainSession newSession = new ResistTrainSession(
                "Copy of " + oldSession.getName(),
                oldSession.getUserId(),
                new Date(System.currentTimeMillis())
        );
        newSession.setId(getItemDAO().addItem(newSession));

        System.out.printf("Session - Old ID: %d, New ID: %d%n", oldSession.getId(), newSession.getId());

        // get exercises with old ID, for each exercise, copy with new ID
        ResistTrainExerciseDAO exerciseDAO = new ResistTrainExerciseDAO();
        List<ResistTrainExercise> oldExercises = exerciseDAO.getAllItemsForSession(oldSession.getId());

        for (ResistTrainExercise oldExercise : oldExercises) {
            ResistTrainExercise newExercise = new ResistTrainExercise(
                    oldExercise.getName(),
                    newSession.getId(), // replace old id with new id
                    oldExercise.getExerciseInfoId()
            );
            newExercise.setId(exerciseDAO.addCopiedItem(newExercise));

            System.out.printf("Exercise - Old ID: %d, New ID: %d%n", oldExercise.getId(), newExercise.getId());

            // get sets with old ID, for each set, copy with new ID
            ResistTrainSetDAO setDAO = new ResistTrainSetDAO();
            List<ResistTrainSet> oldSets = setDAO.getSetsForExercise(oldExercise.getId());

            for (ResistTrainSet oldSet : oldSets) {
                ResistTrainSet newSet = new ResistTrainSet(
                        oldSet.getName(),
                        newExercise.getId(), // replace old id with new id
                        oldSet.getWeight(),
                        oldSet.getReps(),
                        oldSet.getRest(),
                        oldSet.getReps()
                );
                newSet.setId(setDAO.addCopiedItem(newSet));

                System.out.printf("Set - Old ID: %d, New ID: %d%n", oldSet.getId(), newSet.getId());
            }
        }

        super.syncItems();

        System.out.println("SESSION COPIED");
    }

    @FXML
    public void initialize() {
        // populate item container
        // <Label text="Session Name:" GridPane.columnIndex="0" GridPane.rowIndex="0" />
        // <TextField fx:id="nameTextField" GridPane.columnIndex="1" GridPane.rowIndex="0" maxWidth="Infinity"/>

        Label itemLabel = new Label("Session Name:");
        setNameTextField(MainController.customTextField("nameTextField"));

        getGridPaneContainer().add(itemLabel, 0, 0);
        getGridPaneContainer().add(getNameTextField(), 1, 0);

        // Set relevant labels
        getEditButton().setText("Edit Session");

        super.initialize();
    }
}

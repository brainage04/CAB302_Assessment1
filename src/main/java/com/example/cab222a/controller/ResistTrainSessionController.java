package com.example.cab222a.controller;

import com.example.cab222a.common.SqliteConnection;
import com.example.cab222a.controller.core.SqliteControllerFunctions;
import com.example.cab222a.dao.core.AbstractObjectDAO;
import com.example.cab222a.dao.resist_train.ResistTrainExerciseDAO;
import com.example.cab222a.dao.resist_train.ResistTrainSetDAO;
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
    @FXML
    public void onEditButtonClick() throws IOException {
        ResistTrainSession selectedItem = itemListView.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            return;
        }

        SqliteConnection.setCurrentResistTrainSession(selectedItem);
        System.out.println("Resist train SESSION stored in memory:\n" + selectedItem);

        MainController.changeScene(editButton, nextScene);
    }

    protected void copySession(ResistTrainSession oldSession) {
        // copy session, add to DB, get session ID for exercises
        ResistTrainSession newSession = new ResistTrainSession(
                "Copy of " + oldSession.getName(),
                oldSession.getUserId(),
                oldSession.getCreated()
        );
        newSession.setId(getItemDAO().addItem(newSession));

        // get exercises with old ID, for each exercise, copy with new ID
        ResistTrainExerciseDAO sessionDAO = new ResistTrainExerciseDAO();
        List<ResistTrainExercise> oldExercises = sessionDAO.getAllItemsForSession(oldSession.getId());

        for (ResistTrainExercise oldExercise : oldExercises) {
            ResistTrainExercise newExercise = new ResistTrainExercise(
                    oldExercise.getName(),
                    newSession.getId(), // replace old id with new id
                    oldExercise.getExerciseInfoId()
            );
            newExercise.setId(sessionDAO.addItem(newExercise));

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
                // setting new IDs for new sets not needed - this is the last layer of relations in the series
                setDAO.addItem(newSet);
            }
        }
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
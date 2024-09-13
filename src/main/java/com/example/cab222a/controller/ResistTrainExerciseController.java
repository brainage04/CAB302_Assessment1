package com.example.cab222a.controller;

import com.example.cab222a.common.SqliteConnection;
import com.example.cab222a.controller.core.SqliteControllerFunctions;
import com.example.cab222a.dao.core.AbstractObjectDAO;
import com.example.cab222a.dao.resist_train.ResistTrainExerciseDAO;
import com.example.cab222a.model.resist_train.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ResistTrainExerciseController extends SqliteControllerFunctions<ResistTrainExercise> {
    @Override
    public AbstractObjectDAO<ResistTrainExercise> initItemDAO() {
        return new ResistTrainExerciseDAO();
    }
    @Override
    public String initNextScene() {
        return "resist-train-set-view.fxml";
    }
    @Override
    public String initPreviousScene() {
        return "resist-train-session-view.fxml";
    }
    @Override
    public ResistTrainExercise generateDefaultItem() {
        return new ResistTrainExercise("New Exercise", SqliteConnection.getCurrentResistTrainSession().getId(), -1);
    }

    @FXML
    public void initialize() {
        // populate item container
        // <Label text="Exercise Name:" GridPane.columnIndex="0" GridPane.rowIndex="0" />
        // <TextField fx:id="nameTextField" GridPane.columnIndex="1" GridPane.rowIndex="0" maxWidth="Infinity"/>

        Label itemLabel = new Label("Exercise Name:");
        setNameTextField(MainController.customTextField("nameTextField"));

        getGridPaneContainer().add(itemLabel, 0, 0);
        getGridPaneContainer().add(getNameTextField(), 1, 0);

        // Set relevant labels
        getEditButton().setText("Edit Exercise");
        getDetailsLabel().setText("Currently editing: " + SqliteConnection.getCurrentResistTrainSession().getName());

        super.initialize();
    }
}
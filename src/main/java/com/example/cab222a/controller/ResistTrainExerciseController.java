package com.example.cab222a.controller;

import com.example.cab222a.common.SqliteConnection;
import com.example.cab222a.controller.core.SqliteControllerFunctions;
import com.example.cab222a.dao.core.IObjectDAO;
import com.example.cab222a.dao.resist_train.SqliteResistTrainExerciseDAO;
import com.example.cab222a.model.resist_train.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class ResistTrainExerciseController extends SqliteControllerFunctions<ResistTrainExercise> {
    @Override
    public IObjectDAO<ResistTrainExercise> initItemDAO() {
        return new SqliteResistTrainExerciseDAO();
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
        return new ResistTrainExercise("New Exercise", SqliteConnection.getCurrentResistTrainSession().getId());
    }

    @FXML
    public void initialize() {
        // populate item container
        // <Label text="Exercise Name:" GridPane.columnIndex="0" GridPane.rowIndex="0" />
        // <TextField fx:id="nameTextField" GridPane.columnIndex="1" GridPane.rowIndex="0" maxWidth="Infinity"/>

        Label itemLabel = new Label("Exercise Name:");
        GridPane.setColumnIndex(itemLabel, 0);
        GridPane.setRowIndex(itemLabel, 0);

        setNameTextField(new TextField());
        getNameTextField().setId("nameTextField");
        GridPane.setColumnIndex(getNameTextField(), 1);
        GridPane.setRowIndex(getNameTextField(), 0);
        getNameTextField().setMaxWidth(Double.POSITIVE_INFINITY);

        getGridPaneContainer().getChildren().add(itemLabel);
        getGridPaneContainer().getChildren().add(getNameTextField());

        // Set relevant labels
        getEditButton().setText("Edit Exercise");
        getDetailsLabel().setText("Currently editing: " + SqliteConnection.getCurrentResistTrainSession().getName());

        super.initialize();
    }
}
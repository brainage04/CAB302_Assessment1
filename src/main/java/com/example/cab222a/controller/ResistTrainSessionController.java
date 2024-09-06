package com.example.cab222a.controller;

import com.example.cab222a.common.SqliteConnection;
import com.example.cab222a.controller.core.SqliteControllerFunctions;
import com.example.cab222a.model.core.IObjectDAO;
import com.example.cab222a.model.resist_train.ResistTrainSession;
import com.example.cab222a.model.resist_train.SqliteResistTrainSessionDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class ResistTrainSessionController extends SqliteControllerFunctions<ResistTrainSession> {
    @Override
    public IObjectDAO<ResistTrainSession> initItemDAO() {
        return new SqliteResistTrainSessionDAO();
    }
    @Override
    public String initNextScene() {
        return "resist-train-exercise-view.fxml";
    }
    @Override
    public String initPreviousScene() {
        return "main-view.fxml";
    }
    @Override
    public ResistTrainSession generateDefaultItem() {
        return new ResistTrainSession("New Session", SqliteConnection.getCurrentUser().getId());
    }

    @FXML
    public void initialize() {
        // populate item container
        // <Label text="Session Name:" GridPane.columnIndex="0" GridPane.rowIndex="0" />
        // <TextField fx:id="nameTextField" GridPane.columnIndex="1" GridPane.rowIndex="0" maxWidth="Infinity"/>

        Label itemLabel = new Label("Session Name:");
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
        getEditButton().setText("Edit Session");

        super.initialize();
    }
}
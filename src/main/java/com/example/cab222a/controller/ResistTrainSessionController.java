package com.example.cab222a.controller;

import com.example.cab222a.common.SqliteConnection;
import com.example.cab222a.controller.core.SqliteControllerFunctions;
import com.example.cab222a.dao.core.AbstractObjectDAO;
import com.example.cab222a.model.resist_train.ResistTrainSession;
import com.example.cab222a.dao.resist_train.ResistTrainSessionDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.Date;

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
package com.example.cab222a.controller;

import com.example.cab222a.common.SqliteConnection;
import com.example.cab222a.controller.core.SqliteControllerFunctions;
import com.example.cab222a.dao.core.AbstractObjectDAO;
import com.example.cab222a.dao.resist_train.ExerciseInfoDAO;
import com.example.cab222a.dao.resist_train.ResistTrainExerciseDAO;
import com.example.cab222a.model.resist_train.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.List;

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
        return new ResistTrainExercise("New Exercise", SqliteConnection.getCurrentResistTrainSession().getId(), 1);
    }

    @FXML private ComboBox exerciseField;

    @Override
    protected void selectItem(ResistTrainExercise item) {
        super.selectItem(item);

        ExerciseInfo exerciseInfo = new ExerciseInfoDAO().getItem(item.getExerciseInfoId());
        exerciseField.setValue(exerciseInfo.getName());
    }

    @Override
    protected void onEditConfirm() {
        // Get the selected item from the list view
        ResistTrainExercise selectedItem = getItemListView().getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            selectedItem.setName(getNameTextField().getText());

            System.out.println(exerciseField.getValue());
            String name = (String) exerciseField.getValue();
            System.out.println(name);
            ExerciseInfo exerciseInfo = new ExerciseInfoDAO().getItem(name);

            selectedItem.setExerciseInfoId(exerciseInfo.getId());
            getItemDAO().updateItem(selectedItem);
            syncItems();
        }
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

        // <Label text="Exercise:" GridPane.columnIndex="0" GridPane.rowIndex="1" />
        // <ComboBox fx:id="exerciseField" GridPane.columnIndex="1" GridPane.rowIndex="1" maxWidth="Infinity"/>

        Label exerciseLabel = new Label("Exercise:");

        List<String> exerciseNames = new ArrayList<>();
        List<ExerciseInfo> exercises = new ExerciseInfoDAO().getAllItems();
        for (ExerciseInfo exercise : exercises) {
            exerciseNames.add(exercise.getName());
        }
        ObservableList<String> options = FXCollections.observableArrayList(
                exerciseNames
        );
        exerciseField = new ComboBox(options);
        exerciseField.setId("exerciseField");
        exerciseField.setMaxWidth(Double.POSITIVE_INFINITY);

        getGridPaneContainer().add(exerciseLabel, 0, 1);
        getGridPaneContainer().add(exerciseField, 1, 1);

        // Set relevant labels
        getEditButton().setText("Edit Exercise");
        getDetailsLabel().setText("Currently editing: " + SqliteConnection.getCurrentResistTrainSession().getName());

        super.initialize();
    }
}
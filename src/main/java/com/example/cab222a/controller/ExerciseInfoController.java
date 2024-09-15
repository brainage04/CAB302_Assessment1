package com.example.cab222a.controller;

import com.example.cab222a.common.SqliteConnection;
import com.example.cab222a.controller.core.SqliteControllerFunctions;
import com.example.cab222a.dao.core.AbstractObjectDAO;
import com.example.cab222a.dao.resist_train.ExerciseInfoDAO;
import com.example.cab222a.model.resist_train.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ExerciseInfoController extends SqliteControllerFunctions<ExerciseInfo> {
    @Override
    public AbstractObjectDAO<ExerciseInfo> initItemDAO(){ return new ExerciseInfoDAO(); }
    @Override
    public String initNextScene() { return "main-view.fxml"; }
    @Override
    public String initPreviousScene() { return "main-view.fxml"; }
    @Override
    public ExerciseInfo generateDefaultItem() {
        return new ExerciseInfo("Bench", "Chest", "Triceps", "Bench Press one of the most affective chest exercises");
    }

    @FXML
    public void initialize(){
        Label itemLabel = new Label("Exercise Name:");
        setNameTextField(MainController.customTextField("nameTextField"));

        getGridPaneContainer().add(itemLabel, 0, 0);
        getGridPaneContainer().add(getNameTextField(), 1, 0);

        // Set relevant labels
        getEditButton().setText("Edit Exercise");
        //getDetailsLabel().setText("Currently editing: " + SqliteConnection.getCurrentExerciseInfo().getName());
    }
}

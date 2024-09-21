package com.example.cab222a.controller;

import com.example.cab222a.common.SqliteConnection;
import com.example.cab222a.controller.core.SqliteControllerFunctions;
import com.example.cab222a.dao.core.AbstractObjectDAO;
import com.example.cab222a.dao.resist_train.ExerciseInfoDAO;
import com.example.cab222a.model.resist_train.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;


import java.io.IOException;
import java.util.List;

import static com.example.cab222a.controller.MainController.changeScene;

public class ExerciseInfoController extends SqliteControllerFunctions<ExerciseInfo> {
    @Override
    public AbstractObjectDAO<ExerciseInfo> initItemDAO(){ return new ExerciseInfoDAO(); }
    @Override
    public String initNextScene() { return "main-view.fxml"; }
    @Override
    public String initPreviousScene() { return "main-view.fxml"; }
    @Override
    public ExerciseInfo generateDefaultItem() {
        return new ExerciseInfo("New Exercise", "Primary Muscle", "Secondary Muscle", "Description of exercise.");
    }

    @FXML private TextField primaryMuscleTextField;
    @FXML private TextField secondaryMuscleTextField;
    @FXML private TextField descriptionTextField;

    @FXML
    private ListView<ExerciseInfo> itemListView;

    private ExerciseInfoDAO exerciseInfoDAO;

    @FXML
    private TextField searchTextField;

    @FXML private VBox itemContainer;


    @Override
    protected void selectItem(ExerciseInfo exerciseInfo){
        super.selectItem(exerciseInfo);

        primaryMuscleTextField.setText(exerciseInfo.getPrimaryMuscleGroups());
        secondaryMuscleTextField.setText(exerciseInfo.getSecondaryMuscleGroups());
        descriptionTextField.setText(exerciseInfo.getDescription());
    }

    @Override
    protected void onEditConfirm() {
        // Get the selected item from the list view
        ExerciseInfo selectedItem = getItemListView().getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            selectedItem.setName(getNameTextField().getText());
            selectedItem.setPrimaryMuscleGroups(primaryMuscleTextField.getText());
            selectedItem.setSecondaryMuscleGroups(secondaryMuscleTextField.getText());
            selectedItem.setDescription(descriptionTextField.getText());
            getItemDAO().updateItem(selectedItem);
            syncItems();
        }
    }

    // Override due to implementing search
    // Should update current list depending on user search query
    @Override
    protected void syncItems() {
        itemListView.getItems().clear();
        String query = searchTextField.getText();
        List<ExerciseInfo> exerciseInfo = exerciseInfoDAO.searchExerciseInfo(query);
        boolean hasExerciseInfo = !exerciseInfo.isEmpty();
        if (hasExerciseInfo) {
            itemListView.getItems().addAll(exerciseInfo);
        }
        itemContainer.setVisible(hasExerciseInfo);
    }

    @FXML
    public void initialize(){
        // Initialise exerciseInfoDAO to access methods
        exerciseInfoDAO = new ExerciseInfoDAO();

        // Set relevant labels
        Label itemLabel = new Label("Exercise Name:");
        setNameTextField(MainController.customTextField("nameTextField"));

        getGridPaneContainer().add(itemLabel, 0, 0);
        getGridPaneContainer().add(getNameTextField(), 1, 0);

        Label primaryMuscleLabel = new Label("Primary Muscle:");
        primaryMuscleTextField = MainController.customTextField("primaryMuscleTextField");

        getGridPaneContainer().add(primaryMuscleLabel, 0, 1);
        getGridPaneContainer().add(primaryMuscleTextField, 1, 1);

        Label secondaryMuscleLabel = new Label("Secondary Muscle:");
        secondaryMuscleTextField = MainController.customTextField("secondaryMuscleTextField");

        getGridPaneContainer().add(secondaryMuscleLabel, 0, 2);
        getGridPaneContainer().add(secondaryMuscleTextField, 1, 2);

        Label descriptionLabel = new Label("Description:");
        descriptionTextField = MainController.customTextField("descriptionTextField");

        getGridPaneContainer().add(descriptionLabel, 0, 3);
        getGridPaneContainer().add(descriptionTextField, 1, 3);

        // getEditButton().setText("Edit Exercise");
        // getDetailsLabel().setText("Currently editing: " + SqliteConnection.getCurrentExerciseInfo().getName());

        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> syncItems());

        super.initialize();
    }

    // If we ever need the edit button again.
    // We do already have edit confirm

//    @Override
//    @FXML
//    public void onEditButtonClick() throws IOException {
//        ExerciseInfo selectedItem = itemListView.getSelectionModel().getSelectedItem();
//        if (selectedItem == null) {
//            return;
//        }
//
//        SqliteConnection.setCurrentExerciseInfo(selectedItem);
//        System.out.println("Exercise INFO stored in memory:\n" + selectedItem);
//
//        MainController.changeScene(editButton, nextScene);
//    }
}

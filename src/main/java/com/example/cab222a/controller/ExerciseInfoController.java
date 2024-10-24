package com.example.cab222a.controller;

import com.example.cab222a.controller.core.SqliteControllerFunctions;
import com.example.cab222a.dao.core.AbstractObjectDAO;
import com.example.cab222a.dao.resist_train.ExerciseInfoDAO;
import com.example.cab222a.model.resist_train.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Controller class to manage ExerciseInfo view for JavaFX.
 * Allows users to perform Create, Read, Update, Delete (CRUD) operations alternatives of exercises.
 */
public class ExerciseInfoController extends SqliteControllerFunctions<ExerciseInfo> {
    public Button alternativeExerciseButton;

    @Override
    public AbstractObjectDAO<ExerciseInfo> initItemDAO() { return new ExerciseInfoDAO(); }
    @Override
    public String getNextSceneName() { return "main-view.fxml"; }
    @Override
    public String getPreviousSceneName() { return "main-view.fxml"; }
    @Override
    public ExerciseInfo generateDefaultItem() {
        return new ExerciseInfo("New Exercise", "Primary Muscle", "", "Description of exercise.");
    }

    @FXML private TextField primaryMuscleTextField;
    @FXML private TextField secondaryMuscleTextField;
    //@FXML private TextField descriptionTextField;
    @FXML private TextArea descriptionTextArea;

    @FXML
    private ListView<ExerciseInfo> itemListView;

    private ExerciseInfoDAO exerciseInfoDAO;

    @FXML
    private TextField searchTextField;

    @FXML private VBox itemContainer;

    @Override
    protected void selectItem(ExerciseInfo exerciseInfo) {
        super.selectItem(exerciseInfo);
        System.out.println("Selected Exercise: " + exerciseInfo.getName() + ", UserID: " + exerciseInfo.getUserId());

        primaryMuscleTextField.setText(exerciseInfo.getPrimaryMuscleGroups());
        secondaryMuscleTextField.setText(exerciseInfo.getSecondaryMuscleGroups());
        //descriptionTextField.setText(exerciseInfo.getDescription());
        descriptionTextArea.setText(exerciseInfo.getDescription());
    }

    @Override
    protected void onEditConfirm() {
        // Get the selected item from the list view
        ExerciseInfo selectedItem = getItemListView().getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            // User cannot edit default items where userId is -1.
            if(selectedItem.getUserId() != -1){
                selectedItem.setName(getNameTextField().getText());
                selectedItem.setPrimaryMuscleGroups(primaryMuscleTextField.getText());
                selectedItem.setSecondaryMuscleGroups(secondaryMuscleTextField.getText());
                selectedItem.setDescription(descriptionTextArea.getText());
                getItemDAO().updateItem(selectedItem);

            }
            else {
                Alert errorAlert = new Alert(Alert.AlertType.INFORMATION);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText("Cannot edit default exercises.");
                errorAlert.setContentText(selectedItem.getName() + " is a default exercise.");
                errorAlert.showAndWait();
            }
            syncItems();
        }
    }

    @Override
    protected void syncItems() {
        itemListView.getItems().clear();
        String query = searchTextField.getText();
        List<ExerciseInfo> exerciseInfo = exerciseInfoDAO.searchExerciseInfo(query);
        List<ExerciseInfo> mutableExerciseInfo = new ArrayList<>(exerciseInfo);

        boolean hasExerciseInfo = !exerciseInfo.isEmpty();

        mutableExerciseInfo.sort(Comparator.comparing(ExerciseInfo::getName, String.CASE_INSENSITIVE_ORDER));

        if (hasExerciseInfo) {
            itemListView.getItems().addAll(mutableExerciseInfo);
        }
        itemContainer.setVisible(hasExerciseInfo);
    }

    /**
     * Searches for an alternative exercise based on the selected exercise's name and displays them in the list view.
     * If there are no alternatives, an alert is shown to the user.
     */
    @FXML
    private void onAlternativeButtonClick() {
        ExerciseInfo selectedExercise = itemListView.getSelectionModel().getSelectedItem();

        if (selectedExercise != null) {
            // Find alternatives using exercise's name
            List<ExerciseInfo> alternativeExercises = exerciseInfoDAO.findAlternatives(selectedExercise.getName());

            if (!alternativeExercises.isEmpty()) {
                // Clear ListView and show alternative exercises
                itemListView.getItems().clear();
                itemListView.getItems().addAll(alternativeExercises);
            } else {
                // https://stackoverflow.com/questions/39149242/how-can-i-do-an-error-messages-in-javafx
                // If no alternatives are found
                Alert errorAlert = new Alert(Alert.AlertType.INFORMATION);
                errorAlert.setTitle("No Alternatives Found");
                errorAlert.setHeaderText("Alternatives not found");
                errorAlert.setContentText("No alternative exercises found for " + selectedExercise.getName());
                errorAlert.showAndWait();
            }
        }
    }

    @Override
    @FXML
    protected void onDelete() {
        ExerciseInfo selectedItem = itemListView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            if (selectedItem.getUserId() != -1) {
                getItemDAO().deleteItem(selectedItem.getId());
                syncItems();
            } else {
                Alert errorAlert = new Alert(Alert.AlertType.INFORMATION);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText("Cannot delete default exercises.");
                errorAlert.setContentText(selectedItem.getName() + " is a default exercise.");
                errorAlert.showAndWait();
            }
        }
    }

    /**
     * Creates and returns a TextArea with predefined properties.
     * @param id The ID to assign to the TextArea
     * @return A predefined TextArea.
     */
    public static TextArea customTextArea(String id) {
        TextArea textArea = new TextArea();
        textArea.setId(id);
        textArea.setWrapText(true);
        return textArea;
    }

    @FXML
    @Override
    public void initialize() {
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
        descriptionTextArea = customTextArea("descriptionTextArea");

        getGridPaneContainer().add(descriptionLabel, 0, 3);
        getGridPaneContainer().add(descriptionTextArea, 1, 3);

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

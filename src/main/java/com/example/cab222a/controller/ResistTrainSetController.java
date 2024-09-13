package com.example.cab222a.controller;

import com.example.cab222a.common.SqliteConnection;
import com.example.cab222a.controller.core.SqliteControllerFunctions;
import com.example.cab222a.dao.core.AbstractObjectDAO;
import com.example.cab222a.dao.resist_train.ResistTrainSetDAO;
import com.example.cab222a.model.resist_train.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ResistTrainSetController extends SqliteControllerFunctions<ResistTrainSet> {
    @Override
    public AbstractObjectDAO<ResistTrainSet> initItemDAO() {
        return new ResistTrainSetDAO();
    }
    @Override
    public String initNextScene() {
        return null;
    }
    @Override
    public String initPreviousScene() {
        return "resist-train-exercise-view.fxml";
    }
    @Override
    public ResistTrainSet generateDefaultItem() {
        return new ResistTrainSet("New Set", SqliteConnection.getCurrentResistTrainExercise().getId(), 20, 10, 60, 2);
    }

    @FXML private TextField weightTextField;
    @FXML private TextField repsTextField;
    @FXML private TextField restTextField;
    @FXML private TextField repsInReserveTextField;

    @Override
    protected void selectItem(ResistTrainSet item) {
        super.selectItem(item);

        weightTextField.setText("" + item.getWeight());
        repsTextField.setText("" + item.getReps());
        restTextField.setText("" + item.getRest());
        repsInReserveTextField.setText("" + item.getRepsInReserve());
    }

    @Override
    protected void onEditConfirm() {
        // Get the selected item from the list view
        ResistTrainSet selectedItem = getItemListView().getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            selectedItem.setName(getNameTextField().getText());
            selectedItem.setWeight(Integer.parseInt(weightTextField.getText()));
            selectedItem.setReps(Integer.parseInt(repsTextField.getText()));
            selectedItem.setRest(Integer.parseInt(restTextField.getText()));
            selectedItem.setRepsInReserve(Integer.parseInt(repsInReserveTextField.getText()));
            getItemDAO().updateItem(selectedItem);
            syncItems();
        }
    }

    @FXML
    public void initialize() {
        // populate item container
        // <Label text="Set Name:" GridPane.columnIndex="0" GridPane.rowIndex="0" />
        // <TextField fx:id="nameTextField" GridPane.columnIndex="1" GridPane.rowIndex="0" maxWidth="Infinity"/>

        Label itemLabel = new Label("Set Name:");
        setNameTextField(MainController.customTextField("nameTextField"));

        getGridPaneContainer().add(itemLabel, 0, 0);
        getGridPaneContainer().add(getNameTextField(), 1, 0);

        // <Label text="Weight:" GridPane.columnIndex="0" GridPane.rowIndex="1" />
        // <TextField fx:id="weightTextField" GridPane.columnIndex="1" GridPane.rowIndex="1" maxWidth="Infinity"/>

        Label weightLabel = new Label("Weight:");
        weightTextField = MainController.customTextField("weightTextField");

        getGridPaneContainer().add(weightLabel, 0, 1);
        getGridPaneContainer().add(weightTextField, 1, 1);

        // <Label text="Reps:" GridPane.columnIndex="0" GridPane.rowIndex="2" />
        // <TextField fx:id="repetitionsTextField" GridPane.columnIndex="1" GridPane.rowIndex="2" maxWidth="Infinity"/>

        Label repetitionsLabel = new Label("Reps:");
        repsTextField = MainController.customTextField("repetitionsTextField");

        getGridPaneContainer().add(repetitionsLabel, 0, 2);
        getGridPaneContainer().add(repsTextField, 1, 2);

        // <Label text="Rest (seconds):" GridPane.columnIndex="0" GridPane.rowIndex="3" />
        // <TextField fx:id="restSecondsTextField" GridPane.columnIndex="1" GridPane.rowIndex="3" maxWidth="Infinity"/>

        Label restSecondsLabel = new Label("Rest (seconds):");
        restTextField = MainController.customTextField("restSecondsTextField");

        getGridPaneContainer().add(restSecondsLabel, 0, 3);
        getGridPaneContainer().add(restTextField, 1, 3);

        // <Label text="Reps in Reserve:" GridPane.columnIndex="0" GridPane.rowIndex="4" />
        // <TextField fx:id="repsInReserveTextField" GridPane.columnIndex="1" GridPane.rowIndex="4" maxWidth="Infinity"/>

        Label repsInReserveSecondsLabel = new Label("Reps in Reserve:");
        repsInReserveTextField = MainController.customTextField("repsInReserveTextField");

        getGridPaneContainer().add(repsInReserveSecondsLabel, 0, 4);
        getGridPaneContainer().add(repsInReserveTextField, 1, 4);

        // Set relevant labels
        getDetailsLabel().setText("Currently editing: " + SqliteConnection.getCurrentResistTrainSession().getName() + " - " + SqliteConnection.getCurrentResistTrainExercise().getName());

        super.initialize();
    }
}
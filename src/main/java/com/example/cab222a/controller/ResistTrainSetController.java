package com.example.cab222a.controller;

import com.example.cab222a.common.SqliteConnection;
import com.example.cab222a.controller.core.SqliteControllerFunctions;
import com.example.cab222a.model.core.IObjectDAO;
import com.example.cab222a.model.resist_train.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class ResistTrainSetController extends SqliteControllerFunctions<ResistTrainSet> {
    @Override
    public IObjectDAO<ResistTrainSet> initItemDAO() {
        return new SqliteResistTrainSetDAO();
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
        return new ResistTrainSet("New Set", 20, 10, 60, SqliteConnection.getCurrentResistTrainExercise().getId());
    }

    @FXML private TextField weightTextField;
    @FXML private TextField repetitionsTextField;
    @FXML private TextField restSecondsTextField;

    @Override
    protected void selectItem(ResistTrainSet item) {
        super.selectItem(item);

        weightTextField.setText("" + item.getWeight());
        repetitionsTextField.setText("" + item.getRepetitions());
        restSecondsTextField.setText("" + item.getRestSeconds());
    }

    @Override
    protected void onEditConfirm() {
        // Get the selected item from the list view
        ResistTrainSet selectedItem = getItemListView().getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            selectedItem.setName(getNameTextField().getText());
            selectedItem.setWeight(Integer.parseInt(weightTextField.getText()));
            selectedItem.setRepetitions(Integer.parseInt(repetitionsTextField.getText()));
            selectedItem.setRestSeconds(Integer.parseInt(restSecondsTextField.getText()));
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
        GridPane.setColumnIndex(itemLabel, 0);
        GridPane.setRowIndex(itemLabel, 0);

        setNameTextField(new TextField());
        getNameTextField().setId("nameTextField");
        GridPane.setColumnIndex(getNameTextField(), 1);
        GridPane.setRowIndex(getNameTextField(), 0);
        getNameTextField().setMaxWidth(Double.POSITIVE_INFINITY);

        // <Label text="Weight:" GridPane.columnIndex="0" GridPane.rowIndex="1" />
        // <TextField fx:id="weightTextField" GridPane.columnIndex="1" GridPane.rowIndex="1" maxWidth="Infinity"/>

        Label weightLabel = new Label("Weight:");
        GridPane.setColumnIndex(itemLabel, 0);
        GridPane.setRowIndex(itemLabel, 1);

        weightTextField = new TextField();
        weightTextField.setId("weightTextField");
        GridPane.setColumnIndex(weightTextField, 1);
        GridPane.setRowIndex(weightTextField, 1);
        weightTextField.setMaxWidth(Double.POSITIVE_INFINITY);

        // <Label text="Repetitions:" GridPane.columnIndex="0" GridPane.rowIndex="2" />
        // <TextField fx:id="repetitionsTextField" GridPane.columnIndex="1" GridPane.rowIndex="2" maxWidth="Infinity"/>

        Label repetitionsLabel = new Label("Repetitions:");
        GridPane.setColumnIndex(itemLabel, 0);
        GridPane.setRowIndex(itemLabel, 2);

        repetitionsTextField = new TextField();
        repetitionsTextField.setId("repetitionsTextField");
        GridPane.setColumnIndex(repetitionsTextField, 1);
        GridPane.setRowIndex(repetitionsTextField, 2);
        repetitionsTextField.setMaxWidth(Double.POSITIVE_INFINITY);

        // <Label text="Rest Seconds:" GridPane.columnIndex="0" GridPane.rowIndex="3" />
        // <TextField fx:id="restSecondsTextField" GridPane.columnIndex="1" GridPane.rowIndex="3" maxWidth="Infinity"/>

        Label restSecondsLabel = new Label("Rest Seconds:");
        GridPane.setColumnIndex(itemLabel, 0);
        GridPane.setRowIndex(itemLabel, 3);

        restSecondsTextField = new TextField();
        restSecondsTextField.setId("restSecondsTextField");
        GridPane.setColumnIndex(restSecondsTextField, 1);
        GridPane.setRowIndex(restSecondsTextField, 3);
        restSecondsTextField.setMaxWidth(Double.POSITIVE_INFINITY);

        getGridPaneContainer().getChildren().add(itemLabel);
        getGridPaneContainer().getChildren().add(getNameTextField());
        getGridPaneContainer().getChildren().add(weightLabel);
        getGridPaneContainer().getChildren().add(weightTextField);
        getGridPaneContainer().getChildren().add(repetitionsLabel);
        getGridPaneContainer().getChildren().add(repetitionsTextField);
        getGridPaneContainer().getChildren().add(restSecondsLabel);
        getGridPaneContainer().getChildren().add(restSecondsTextField);

        // Set relevant labels
        getDetailsLabel().setText("Currently editing: " + SqliteConnection.getCurrentResistTrainSession().getName() + " - " + SqliteConnection.getCurrentResistTrainExercise().getName());

        super.initialize();
    }
}
package com.example.cab222a.controller;

import com.example.cab222a.common.SqliteConnection;
import com.example.cab222a.model.resist_train.IResistTrainDAO;
import com.example.cab222a.model.resist_train.ResistTrainSession;
import com.example.cab222a.model.resist_train.SqliteResistTrainSessionDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class ResistTrainSessionController {
    @FXML
    private ListView<ResistTrainSession> itemListView;
    private final IResistTrainDAO<ResistTrainSession> itemDAO;
    public ResistTrainSessionController() {
        itemDAO = new SqliteResistTrainSessionDAO();
    }

    @FXML
    private TextField nameTextField;

    @FXML
    private Button editButton;
    @FXML
    private Button goBackButton;

    @FXML
    private VBox itemContainer;
    @FXML
    public GridPane gridPaneContainer;

    /**
     * Programmatically selects an item in the list view and
     * updates the text fields with the item's information.
     * @param item The item to select.
     */
    private void selectItem(ResistTrainSession item) {
        itemListView.getSelectionModel().select(item);

        nameTextField.setText(item.getName());
    }

    /**
     * Renders a cell in the item list view by setting the text to the item's full name.
     * @param listView The list view to render the cell for.
     * @return The rendered cell.
     */
    private ListCell<ResistTrainSession> renderCell(ListView<ResistTrainSession> listView) {
        return new ListCell<>() {
            /**
             * Handles the event when an item is selected in the list view.
             * @param mouseEvent The event to handle.
             */
            private void onItemSelected(MouseEvent mouseEvent) {
                ListCell<ResistTrainSession> clickedCell = (ListCell<ResistTrainSession>) mouseEvent.getSource();
                // Get the selected item from the list view
                ResistTrainSession selectedItem = clickedCell.getItem();
                if (selectedItem != null) selectItem(selectedItem);
            }

            /**
             * Updates the item in the cell by setting the text to the item's full name.
             * @param item The item to update the cell with.
             * @param empty Whether the cell is empty.
             */
            @Override
            protected void updateItem(ResistTrainSession item, boolean empty) {
                super.updateItem(item, empty);
                // If the cell is empty, set the text to null, otherwise set it to the item's full name
                if (empty || item == null || item.getName() == null) {
                    setText(null);
                    super.setOnMouseClicked(this::onItemSelected);
                } else {
                    setText(item.getName());
                }
            }
        };
    }

    /**
     * Synchronizes the item list view with the items in the database.
     */
    private void syncItems() {
        ResistTrainSession currentItem = itemListView.getSelectionModel().getSelectedItem();

        itemListView.getItems().clear();
        List<ResistTrainSession> items = itemDAO.getAllItems();
        boolean hasItem = !items.isEmpty();
        if (hasItem) {
            itemListView.getItems().addAll(items);

            // If the current item is still in the list, re-select it
            // Otherwise, select the first item in the list
            ResistTrainSession nextItem = items.contains(currentItem) ? currentItem : items.getFirst();
            itemListView.getSelectionModel().select(nextItem);
            selectItem(nextItem);
        }
        // Show / hide based on whether there are items
        itemContainer.setVisible(hasItem);
    }

    @FXML
    private void onEditConfirm() {
        // Get the selected item from the list view
        ResistTrainSession selectedItem = itemListView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            selectedItem.setName(nameTextField.getText());

            itemDAO.updateItem(selectedItem);
            syncItems();
        }
    }

    @FXML
    private void onCancel() {
        // Find the selected item
        ResistTrainSession selectedItem = itemListView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            // Since the item hasn't been modified,
            // we can just re-select it to refresh the text fields
            selectItem(selectedItem);
        }
    }

    @FXML
    private void onDelete() {
        // Get the selected item from the list view
        ResistTrainSession selectedItem = itemListView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            itemDAO.deleteItem(selectedItem);
            syncItems();
        }
    }

    @FXML
    private void onAdd() {
        // Default values for a new item
        ResistTrainSession newItem = new ResistTrainSession("New Session", SqliteConnection.getCurrentUser().getId());
        // Add the new item to the database
        itemDAO.addItem(newItem);
        syncItems();
        // Select the new item in the list view
        // and focus the first text field
        selectItem(newItem);
        nameTextField.requestFocus();
    }

    @FXML
    public void onEditButtonClick() throws IOException {
        ResistTrainSession resistTrainSession = itemListView.getSelectionModel().getSelectedItem();
        if (resistTrainSession == null) {
            return;
        }

        SqliteConnection.setCurrentResistTrainSession(resistTrainSession);

        MainController.changeScene(editButton, "resist-train-exercise-view.fxml");
    }

    @FXML
    public void onGoBackButtonClick() throws IOException {
        MainController.changeScene(goBackButton, "main-view.fxml");
    }

    @FXML
    public void initialize() {
        // populate item container

        Label sessionName = new Label("Session Name:");
        GridPane.setColumnIndex(sessionName, 0);
        GridPane.setRowIndex(sessionName, 0);

        nameTextField = new TextField();
        nameTextField.setId("nameTextField");
        GridPane.setColumnIndex(nameTextField, 1);
        GridPane.setRowIndex(nameTextField, 0);
        nameTextField.setMaxWidth(Double.POSITIVE_INFINITY);

        gridPaneContainer.getChildren().add(sessionName);
        gridPaneContainer.getChildren().add(nameTextField);

        //                    <Label text="Session Name:" GridPane.columnIndex="0" GridPane.rowIndex="0" />
        //                    <TextField fx:id="nameTextField" GridPane.columnIndex="1" GridPane.rowIndex="0" maxWidth="Infinity"/>

        itemListView.setCellFactory(this::renderCell);
        syncItems();
        // Select the first item and display its information
        itemListView.getSelectionModel().selectFirst();
        ResistTrainSession firstItem = itemListView.getSelectionModel().getSelectedItem();
        if (firstItem != null) {
            selectItem(firstItem);
        }
    }
}
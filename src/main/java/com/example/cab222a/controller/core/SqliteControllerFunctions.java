package com.example.cab222a.controller.core;

import com.example.cab222a.common.SqliteConnection;
import com.example.cab222a.controller.MainController;
import com.example.cab222a.model.core.NamedObject;
import com.example.cab222a.model.core.IObjectDAO;
import com.example.cab222a.model.resist_train.ResistTrainExercise;
import com.example.cab222a.model.resist_train.ResistTrainSession;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public abstract class SqliteControllerFunctions<T extends NamedObject> {
    @FXML ListView<T> itemListView;
    private final IObjectDAO<T> itemDAO;

    @FXML private TextField nameTextField;

    @FXML private Label detailsLabel;

    @FXML private Button editButton;
    @FXML private Button goBackButton;

    private final String nextScene;
    private final String previousScene;

    @FXML private VBox itemContainer;
    @FXML private GridPane gridPaneContainer;

    public SqliteControllerFunctions() {
        itemDAO = initItemDAO();
        nextScene = initNextScene();
        previousScene = initPreviousScene();
    }

    // abstract methods
    public abstract IObjectDAO<T> initItemDAO();
    public abstract String initNextScene();
    public abstract String initPreviousScene();
    public abstract T generateDefaultItem();

    // getters/setters
    public ListView<T> getItemListView() {
        return itemListView;
    }

    public TextField getNameTextField() {
        return nameTextField;
    }

    public Label getDetailsLabel() {
        return detailsLabel;
    }

    public Button getEditButton() {
        return editButton;
    }

    public void setNameTextField(TextField nameTextField) {
        this.nameTextField = nameTextField;
    }

    public VBox getItemContainer() {
        return itemContainer;
    }

    public GridPane getGridPaneContainer() {
        return gridPaneContainer;
    }

    /**
     * Programmatically selects an item in the list view and
     * updates the text fields with the item's information.
     * @param item The item to select.
     */
    protected void selectItem(T item) {
        itemListView.getSelectionModel().select(item);

        nameTextField.setText(item.getName());
    }

    /**
     * Renders a cell in the item list view by setting the text to the item's full name.
     * @param listView The list view to render the cell for.
     * @return The rendered cell.
     */
    protected ListCell<T> renderCell(ListView<T> listView) {
        return new ListCell<T>() {
            /**
             * Handles the event when an item is selected in the list view.
             * @param mouseEvent The event to handle.
             */
            private void onItemSelected(MouseEvent mouseEvent) {
                ListCell<T> clickedCell = (ListCell<T>) mouseEvent.getSource();
                // Get the selected item from the list view
                T selectedItem = clickedCell.getItem();
                if (selectedItem != null) selectItem(selectedItem);
            }

            /**
             * Updates the item in the cell by setting the text to the item's full name.
             * @param item The item to update the cell with.
             * @param empty Whether the cell is empty.
             */
            @Override
            protected void updateItem(T item, boolean empty) {
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
    protected void syncItems() {
        T currentItem = itemListView.getSelectionModel().getSelectedItem();
        itemListView.getItems().clear();
        List<T> items = itemDAO.getAllItems();
        boolean hasItem = !items.isEmpty();
        if (hasItem) {
            itemListView.getItems().addAll(items);

            // If the current item is still in the list, re-select it
            // Otherwise, select the first item in the list
            T nextItem = items.contains(currentItem) ? currentItem : items.getFirst();
            selectItem(nextItem);
        }
        // Show / hide based on whether there are items
        itemContainer.setVisible(hasItem);
    }

    @FXML
    private void onEditConfirm() {
        // Get the selected item from the list view
        T selectedItem = itemListView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            selectedItem.setName(nameTextField.getText());
            itemDAO.updateItem(selectedItem);
            syncItems();
        }
    }

    @FXML
    private void onCancel() {
        // Find the selected item
        T selectedItem = itemListView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            // Since the item hasn't been modified,
            // we can just re-select it to refresh the text fields
            selectItem(selectedItem);
        }
    }

    @FXML
    private void onDelete() {
        // Get the selected item from the list view
        T selectedItem = itemListView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            itemDAO.deleteItem(selectedItem);
            syncItems();
        }
    }

    @FXML
    private void onAdd() {
        // Default values for a new item
        T newItem = generateDefaultItem();
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
        T selectedItem = itemListView.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            return;
        }

        if (selectedItem.getClass().equals(ResistTrainSession.class)) {
            System.out.println("Storing current resistance training session.");
            SqliteConnection.setCurrentResistTrainSession((ResistTrainSession) selectedItem);
        } else if (selectedItem.getClass().equals(ResistTrainExercise.class)) {
            System.out.println("Storing current resistance training exercise.");
            SqliteConnection.setCurrentResistTrainExercise((ResistTrainExercise) selectedItem);
        }

        MainController.changeScene(editButton, nextScene);
    }

    @FXML
    public void onGoBackButtonClick() throws IOException {
        MainController.changeScene(goBackButton, previousScene);
    }

    @FXML
    public void initialize() {
        itemListView.setCellFactory(this::renderCell);
        syncItems();
        // Select the first item and display its information
        itemListView.getSelectionModel().selectFirst();
        T firstItem = itemListView.getSelectionModel().getSelectedItem();
        if (firstItem != null) {
            selectItem(firstItem);
        }
    }
}
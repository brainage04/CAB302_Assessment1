package com.example.cab222a.controller.core;

import com.example.cab222a.controller.MainController;
import com.example.cab222a.model.core.NamedObject;
import com.example.cab222a.dao.core.AbstractObjectDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

/**
 * Abstract class that contains commonly used elements and functions for
 * interacting with Data Access Objects (DAOs).
 * @param <T> The type of object the DAO will be interacting with.
 */
public abstract class SqliteControllerFunctions<T extends NamedObject> {
    @FXML protected ListView<T> itemListView;
    protected final AbstractObjectDAO<T> itemDAO;

    @FXML private TextField nameTextField;

    @FXML private Label detailsLabel;

    @FXML protected Button editButton;
    @FXML private Button goBackButton;

    protected final String nextScene;
    private final String previousScene;

    @FXML private VBox itemContainer;
    @FXML private GridPane gridPaneContainer;

    /**
     * Instantiates this class with the appropriate Data Access Object (DAO) as well as next and previous scene names.
     */
    public SqliteControllerFunctions() {
        itemDAO = initItemDAO();
        nextScene = getNextSceneName();
        previousScene = getPreviousSceneName();
    }

    // abstract methods
    /**
     * Initialises the Data Access Object (DAO) for this controller.
     * @return The DAO to be used for this controller.
     */
    public abstract AbstractObjectDAO<T> initItemDAO();
    /**
     *
     * @return The name of the next scene file.
     */
    public abstract String getNextSceneName();
    /**
     *
     * @return The name of the previous scene file.
     */
    public abstract String getPreviousSceneName();
    /**
     * Helper method to generate default paramaters for the generic type specified.
     * @return The default item generated.
     */
    public abstract T generateDefaultItem();

    // getters/setters
    /**
     *
     * @return The ListView for this controller.
     */
    public ListView<T> getItemListView() {
        return itemListView;
    }

    /**
     *
     * @return The DAO for this controller.
     */
    public AbstractObjectDAO<T> getItemDAO() {
        return itemDAO;
    }

    /**
     *
     * @return The TextField containing the name of the scene.
     */
    public TextField getNameTextField() {
        return nameTextField;
    }

    /**
     *
     * @return The Label containing details of the current and previous scenes.
     */
    public Label getDetailsLabel() {
        return detailsLabel;
    }

    /**
     *
     * @return The Button which transitions to a scene where the user can
     * perform CRUD operations on items related to the item being edited.
     */
    public Button getEditButton() {
        return editButton;
    }

    /**
     * Helper method which sets the name TextField of the scene.
     * @param nameTextField The TextField to assign as this controller's name TextField.
     */
    protected void setNameTextField(TextField nameTextField) {
        this.nameTextField = nameTextField;
    }

    /**
     *
     * @return The main container of this scene.
     */
    public VBox getItemContainer() {
        return itemContainer;
    }

    /**
     *
     * @return The container for CRUD controls of this scene's database table.
     */
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
        return new ListCell<>() {
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
            // Otherwise, select the last item in the list
            T nextItem = items.getLast();
            if (currentItem != null) {
                for (T item : items) {
                    if (item.getId() == currentItem.getId()) {
                        nextItem = currentItem;
                        break;
                    }
                }
            }
            System.out.println("Next item: " + nextItem);
            selectItem(nextItem);
        }
        // Show / hide based on whether there are items
        itemContainer.setVisible(hasItem);
    }

    /**
     * Edits an item in the database.
     */
    @FXML
    protected void onEditConfirm() {
        // Get the selected item from the list view
        T selectedItem = itemListView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            selectedItem.setName(nameTextField.getText());
            itemDAO.updateItem(selectedItem);
            syncItems();
        }
    }

    /**
     * Reverts modifications made to an item's properties in the GUI,
     * restoring them to what they were before any modifications.
     */
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

    /**
     * Deletes an item from the database.
     */
    @FXML
    private void onDelete() {
        // Get the selected item from the list view
        T selectedItem = itemListView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            itemDAO.deleteItem(selectedItem.getId());
            syncItems();
        }
    }

    /**
     * Adds an item to the database.
     */
    @FXML
    private void onAdd() {
        // Default values for a new item
        T newItem = generateDefaultItem();
        // Add the new item to the database
        int newId = itemDAO.addItem(newItem);
        newItem.setId(newId);
        System.out.println("Item added to table " + itemDAO.tableName() + " with ID " + newId);
        syncItems();
        // Select the new item in the list view
        // and focus the first text field
        selectItem(newItem);
        nameTextField.requestFocus();
    }

    /**
     * Transitions to the next scene.
     * @throws IOException If the specified next scene does not exist.
     */
    @FXML
    public void onEditButtonClick() throws IOException {
        T selectedItem = itemListView.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            return;
        }

        MainController.changeScene(editButton, nextScene);
    }

    /**
     * Transitions to the previous scene.
     * @throws IOException If the specified previous scene does not exist.
     */
    @FXML
    public void onGoBackButtonClick() throws IOException {
        MainController.changeScene(goBackButton, previousScene);
    }

    /**
     * Initialises the controller by setting up the cell factory and
     * reading items from the database for the list view.
     */
    @FXML
    public void initialize() {
        // todo: create helper method for overriding methods that build GridPaneContainers
        itemListView.setCellFactory(this::renderCell);
        syncItems();
        // Select the first item and display its information
        itemListView.getSelectionModel().selectLast();
        T lastItem = itemListView.getSelectionModel().getSelectedItem();
        if (lastItem != null) {
            selectItem(lastItem);
        }
    }
}

package com.example.cab222a.controllers;

import com.example.cab222a.HelloApplication;
import com.example.cab222a.common.IUserDAO;
import com.example.cab222a.common.MockUserDAO;
import com.example.cab222a.common.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class MainController {
    @FXML
    private ListView<User> usersListView;
    private final IUserDAO userDAO;
    public MainController() {
        userDAO = new MockUserDAO();
        userDAO.addUser(new User(
                "admin",
                "mcadmin",
                "admin@admin.com",
                "0450450450",
                "admin"
        ));
    }

    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField phoneTextField;
    @FXML
    private TextField passwordField;

    @FXML
    private Button logoutButton;

    @FXML
    private VBox userContainer;

    /**
     * Programmatically selects a user in the list view and
     * updates the text fields with the user's information.
     * @param user The user to select.
     */
    private void selectUser(User user) {
        usersListView.getSelectionModel().select(user);

        firstNameTextField.setText(user.getFirstName());
        lastNameTextField.setText(user.getLastName());
        emailTextField.setText(user.getEmail());
        phoneTextField.setText(user.getPhone());
        passwordField.setText(user.getPassword());
    }

    /**
     * Renders a cell in the users list view by setting the text to the user's full name.
     * @param userListView The list view to render the cell for.
     * @return The rendered cell.
     */
    private ListCell<User> renderCell(ListView<User> userListView) {
        return new ListCell<>() {
            /**
             * Handles the event when a user is selected in the list view.
             * @param mouseEvent The event to handle.
             */
            private void onUserSelected(MouseEvent mouseEvent) {
                ListCell<User> clickedCell = (ListCell<User>) mouseEvent.getSource();
                // Get the selected user from the list view
                User selectedUser = clickedCell.getItem();
                if (selectedUser != null) selectUser(selectedUser);
            }

            /**
             * Updates the item in the cell by setting the text to the user's full name.
             * @param user The user to update the cell with.
             * @param empty Whether the cell is empty.
             */
            @Override
            protected void updateItem(User user, boolean empty) {
                super.updateItem(user, empty);
                // If the cell is empty, set the text to null, otherwise set it to the contact's full name
                if (empty || user == null || user.getFullName() == null) {
                    setText(null);
                    super.setOnMouseClicked(this::onUserSelected);
                } else {
                    setText(user.getFullName());
                }
            }
        };
    }

    /**
     * Synchronizes the user list view with the users in the database.
     */
    private void syncUsers() {
        User currentUser = usersListView.getSelectionModel().getSelectedItem();

        usersListView.getItems().clear();
        List<User> users = userDAO.getAllUsers();
        boolean hasUser = !users.isEmpty();
        if (hasUser) {
            usersListView.getItems().addAll(users);

            // If the current contact is still in the list, re-select it
            // Otherwise, select the first contact in the list
            User nextUser = users.contains(currentUser) ? currentUser : users.getFirst();
            usersListView.getSelectionModel().select(nextUser);
            selectUser(nextUser);
        }
        // Show / hide based on whether there are contacts
        userContainer.setVisible(hasUser);
    }

    @FXML
    private void onEditConfirm() {
        // Get the selected user from the list view
        User selectedUser = usersListView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            selectedUser.setFirstName(firstNameTextField.getText());
            selectedUser.setLastName(lastNameTextField.getText());
            selectedUser.setEmail(emailTextField.getText());
            selectedUser.setPhone(phoneTextField.getText());
            selectedUser.setPassword(passwordField.getText());

            userDAO.updateUser(selectedUser);
            syncUsers();
        }
    }

    @FXML
    private void onCancel() {
        // Find the selected user
        User selectedUser = usersListView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            // Since the user hasn't been modified,
            // we can just re-select it to refresh the text fields
            selectUser(selectedUser);
        }
    }

    @FXML
    private void onDelete() {
        // Get the selected user from the list view
        User selectedUser = usersListView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            userDAO.deleteUser(selectedUser);
            syncUsers();
        }
    }

    @FXML
    private void onAdd() {
        // Default values for a new user
        final String DEFAULT_FIRST_NAME = "New";
        final String DEFAULT_LAST_NAME = "User";
        final String DEFAULT_EMAIL = "";
        final String DEFAULT_PHONE = "";
        final String DEFAULT_PASSWORD = "password";
        User newUser = new User(DEFAULT_FIRST_NAME, DEFAULT_LAST_NAME, DEFAULT_EMAIL, DEFAULT_PHONE, DEFAULT_PASSWORD);
        // Add the new user to the database
        userDAO.addUser(newUser);
        syncUsers();
        // Select the new user in the list view
        // and focus the first name text field
        selectUser(newUser);
        firstNameTextField.requestFocus();
    }

    @FXML
    public void onLogoutButtonClick() throws IOException {
        // logout user here

        Stage stage = (Stage) logoutButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);
    }

    @FXML
    public void initialize() {
        usersListView.setCellFactory(this::renderCell);
        syncUsers();
        // Select the first contact and display its information
        usersListView.getSelectionModel().selectFirst();
        User firstUser = usersListView.getSelectionModel().getSelectedItem();
        if (firstUser != null) {
            selectUser(firstUser);
        }
    }
}
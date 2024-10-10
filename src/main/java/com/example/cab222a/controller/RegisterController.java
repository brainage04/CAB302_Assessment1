package com.example.cab222a.controller;

import com.example.cab222a.common.SqliteConnection;
import com.example.cab222a.dao.user.UserDAO;
import com.example.cab222a.model.user.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Date;

/**
 * Controller for the register stage (register-view.fxml).
 */
public class RegisterController {
    private final UserDAO userDAO = new UserDAO();
    // todo: create abstract/interface classes for the Login/RegisterController classes
    /**
     * Text field for the first name of the user.
     */
    @FXML
    public TextField firstNameTextField;
    /**
     * Text field for the last name of the user.
     */
    @FXML
    public TextField lastNameTextField;
    /**
     * Text field for the email address of the user.
     */
    @FXML
    public TextField emailTextField;
    /**
     * Text field for the phone number of the user.
     */
    @FXML
    public TextField phoneTextField;
    /**
     * Text field for the password of the user.
     */
    @FXML
    public TextField passwordTextField;

    @FXML
    private Button registerButton;
    @FXML
    private Button returnButton;
    /**
     * Label for error messages to be displayed in case something goes wrong.
     */
    @FXML
    public Label errorMessageLabel;

    /**
     * Creates a user in the database and stores their information
     * in memory for future reference, then transitions to the main
     * scene (main-view.fxml).
     * @throws IOException If the main scene does not exist (for some reason).
     */
    @FXML
    public void onRegisterButtonClick() throws IOException {
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String email = emailTextField.getText();
        String phone = phoneTextField.getText();
        String password = passwordTextField.getText();

        // prevent registration if one or more values only contains whitespace
        if (firstName.isBlank()) {
            errorMessageLabel.setText("Please enter a first name.");
            return;
        }
        if (lastName.isBlank()) {
            errorMessageLabel.setText("Please enter a last name.");
            return;
        }
        if (email.isBlank()) {
            errorMessageLabel.setText("Please enter a email.");
            return;
        }
        if (phone.isBlank()) {
            errorMessageLabel.setText("Please enter a phone.");
            return;
        }
        if (password.isBlank()) {
            errorMessageLabel.setText("Please enter a password.");
            return;
        }

        User user = new User(new Date(System.currentTimeMillis()), firstName, lastName, email, password, phone);
        int userId = userDAO.addItem(user);
        user.setId(userId);

        SqliteConnection.setCurrentUser(user);

        MainController.changeScene(registerButton, "main-view.fxml");
    }

    /**
     * Transitions to the initial scene (hello-view.fxml)
     * @throws IOException If the initial scene does not exist (for some reason).
     */
    @FXML
    public void onReturnButtonClick() throws IOException {
        MainController.changeScene(returnButton, "hello-view.fxml");
    }
}
package com.example.cab222a.controller;

import com.example.cab222a.common.SqliteConnection;
import com.example.cab222a.dao.user.UserDAO;
import com.example.cab222a.model.user.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

/**
 * Controller for the login stage (login-view.fxml).
 */
public class LoginController {
    private final UserDAO userDAO = new UserDAO();
    // todo: create abstract/interface classes for the Login/RegisterController classes
    /**
     * Text field for the email address of the user.
     */
    @FXML
    public TextField emailTextField;
    /**
     * Text field for the password of the user.
     */
    @FXML
    public TextField passwordTextField;

    @FXML
    private Button loginButton;
    @FXML
    private Button returnButton;
    /**
     * Label for error messages to be displayed in case something goes wrong.
     */
    @FXML
    public Label errorMessageLabel;

    /**
     * Retrieves a user from the database and stores their information
     * in memory for future reference, then transitions to the main
     * scene (main-view.fxml).
     * @throws IOException If the main scene does not exist (for some reason).
     */
    @FXML
    public void onLoginButtonClick() throws IOException {
        String email = emailTextField.getText();
        String password = passwordTextField.getText();

        User user = userDAO.getItem(email, password);

        if (user != null) {
            SqliteConnection.setCurrentUser(user);

            MainController.changeScene(loginButton, "main-view.fxml");
        } else {
            errorMessageLabel.setText("Incorrect email or password.");
        }
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
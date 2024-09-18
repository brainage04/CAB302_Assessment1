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

public class RegisterController {
    private final UserDAO userDAO = new UserDAO();

    @FXML
    public TextField firstNameTextField;
    @FXML
    public TextField lastNameTextField;
    @FXML
    public TextField emailTextField;
    @FXML
    public TextField phoneTextField;
    @FXML
    public TextField passwordTextField;

    @FXML
    private Button registerButton;
    @FXML
    private Button returnButton;
    @FXML
    public Label errorMessageLabel;

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
        int userId = userDAO.addAndGetId(user);
        user.setId(userId);

        SqliteConnection.setCurrentUser(user);

        MainController.changeScene(registerButton, "main-view.fxml");
    }

    @FXML
    public void onReturnButtonClick() throws IOException {
        MainController.changeScene(returnButton, "hello-view.fxml");
    }
}
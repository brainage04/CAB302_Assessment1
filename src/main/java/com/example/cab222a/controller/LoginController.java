package com.example.cab222a.controller;

import com.example.cab222a.common.SqliteConnection;
import com.example.cab222a.model.user.SqliteUserDAO;
import com.example.cab222a.model.user.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController {
    private final SqliteUserDAO userDAO = new SqliteUserDAO();

    @FXML
    public TextField emailTextField;
    @FXML
    public TextField passwordTextField;

    @FXML
    private Button loginButton;
    @FXML
    private Button returnButton;
    @FXML
    public Label errorMessageLabel;

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

    @FXML
    public void onReturnButtonClick() throws IOException {
        MainController.changeScene(returnButton, "hello-view.fxml");
    }
}
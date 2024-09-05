package com.example.cab222a.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class HelloController {
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;

    @FXML
    public void onLoginButtonClick() throws IOException {
        MainController.changeScene(loginButton, "login-view.fxml");
    }

    @FXML
    public void onRegisterButtonClick() throws IOException {
        MainController.changeScene(registerButton, "register-view.fxml");
    }
}
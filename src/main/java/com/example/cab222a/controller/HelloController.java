package com.example.cab222a.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

/**
 * Controller for the initial stage (hello-view.fxml).
 */
public class HelloController {
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;

    /**
     * Transitions to the login stage.
     * @throws IOException If the login stage does not exist (for some reason).
     */
    @FXML
    public void onLoginButtonClick() throws IOException {
        MainController.changeScene(loginButton, "login-view.fxml");
    }

    /**
     * Transitions to the register stage.
     * @throws IOException If the register stage does not exist (for some reason).
     */
    @FXML
    public void onRegisterButtonClick() throws IOException {
        MainController.changeScene(registerButton, "register-view.fxml");
    }
}
package com.example.cab222a.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.util.Objects;

/**
 * Controller for the initial stage (hello-view.fxml).
 */
public class HelloController {
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;

    @FXML
    public ImageView logo;

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

    @FXML
    public void initialize() {
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/logo750width.png")));
        logo.setImage(image);
    }
}
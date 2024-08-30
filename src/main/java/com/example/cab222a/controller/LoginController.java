package com.example.cab222a.controller;

import com.example.cab222a.HelloApplication;
import com.example.cab222a.common.SqliteConnection;
import com.example.cab222a.model.IUserDAO;
import com.example.cab222a.model.SqliteUserDAO;
import com.example.cab222a.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    private IUserDAO userDAO = new SqliteUserDAO();

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

            Stage stage = (Stage) loginButton.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
            stage.setScene(scene);
        } else {
            errorMessageLabel.setText("Incorrect email or password.");
        }
    }

    @FXML
    public void onReturnButtonClick() throws IOException {
        Stage stage = (Stage) returnButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);
    }
}
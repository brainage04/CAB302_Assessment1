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

public class RegisterController {
    private IUserDAO userDAO = new SqliteUserDAO();

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

        User user = new User(firstName, lastName, email, phone, password);

        userDAO.addItem(user);

        // we need to get the user's ID from the database, otherwise it will be read as 0 when it is not actually 0
        user = userDAO.getItem(email, password);

        if (user != null) {
            SqliteConnection.setCurrentUser(user);

            Stage stage = (Stage) registerButton.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
            stage.setScene(scene);
        } else {
            errorMessageLabel.setText("An error has occured during registration. Please try again.");
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
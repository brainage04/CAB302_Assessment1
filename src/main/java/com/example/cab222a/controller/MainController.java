package com.example.cab222a.controller;

import com.example.cab222a.HelloApplication;
import com.example.cab222a.common.SqliteConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    @FXML
    public Button resistTrainButton;
    @FXML
    public Button cardioTrainButton;
    @FXML
    public Button exerciseInfoButton;
    @FXML
    public Button heightButton;
    @FXML
    public Button weightButton;
    @FXML
    public Button bodyMassIndexButton;
    @FXML
    public Button bodyFatButton;
    @FXML
    public Button bloodPressureButton;
    @FXML
    public Button sleepButton;
    @FXML
    public Label welcomeBackLabel;
    @FXML
    public Label debugLabel;

    @FXML
    private Button logoutButton;

    @FXML
    public void onResistTrainButtonClick() throws IOException {
        changeScene(resistTrainButton, "resist-train-session-view.fxml");
    }

    @FXML
    public void onCardioTrainButtonClick() throws IOException {
        changeScene(resistTrainButton, "cardio-session-view.fxml");
    }

    @FXML
    public void onExerciseInformationButtonClick () throws IOException {
        changeScene(exerciseInfoButton, "exercise-info-view.fxml");
    }

    @FXML
    public void onHeightButtonClick() throws IOException {
        changeScene(resistTrainButton, "height-view.fxml");
    }

    @FXML
    public void onWeightButtonClick() throws IOException {
        changeScene(resistTrainButton, "weight-view.fxml");
    }

    @FXML
    public void onBodyMassIndexButtonClick() throws IOException {
        changeScene(resistTrainButton, "body-mass-index-view.fxml");
    }

    @FXML
    public void onBodyFatButtonClick() throws IOException {
        changeScene(resistTrainButton, "body-fat-view.fxml");
    }

    @FXML
    public void onBloodPressureButtonClick() throws IOException {
        changeScene(resistTrainButton, "blood-pressure-view.fxml");
    }

    @FXML
    public void onSleepButtonClick() throws IOException {
        changeScene(resistTrainButton, "sleep-view.fxml");
    }

    @FXML
    public void onLogoutButtonClick() throws IOException {
        SqliteConnection.logOut();

        changeScene(logoutButton, "hello-view.fxml");
    }

    @FXML
    public void initialize() {
        welcomeBackLabel.setText("Welcome back, " + SqliteConnection.getCurrentUser().getFirstName() + "!");
        debugLabel.setText(SqliteConnection.getCurrentUser().toString());
    }

    public static void changeScene(Button source, String sceneName) throws IOException {
        Stage stage = (Stage) source.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(sceneName));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);
    }

    public static TextField customTextField(String id) {
        TextField textField = new TextField();
        textField.setId(id);
        textField.setMaxWidth(Double.POSITIVE_INFINITY);
        return textField;
    }
}
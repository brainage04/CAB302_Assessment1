package com.example.cab222a.controller;

import com.example.cab222a.HelloApplication;
import com.example.cab222a.common.SqliteConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.io.IOException;
import java.util.Objects;

/**
 * Controller for the main stage (main-view.fxml).
 */
public class MainController {
    @FXML
    public Button resistTrainButton;
    @FXML
    public Button cardioTrainButton;
    @FXML
    public Button exerciseInfoButton;
    /**
     * Dynamic label which welcomes the user with their first name.
     */
    @FXML
    public Button metricButton;
    @FXML
    public Label welcomeBackLabel;
    /**
     * Label currently used to display information about the
     * current user to verify that the DAO is working as intended.
     */
    @FXML
    public Label debugLabel;
    public ImageView logo;

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
    public void onMetricButtonClick() throws IOException {
        changeScene(resistTrainButton, "metric-view.fxml");
    }

    @FXML
    public void onLogoutButtonClick() throws IOException {
        SqliteConnection.logOut();

        changeScene(logoutButton, "hello-view.fxml");
    }

    @FXML
    public void initialize() {
        welcomeBackLabel.setText("Welcome back, " + SqliteConnection.getCurrentUser().getFirstName() + "!");
        //debugLabel.setText(SqliteConnection.getCurrentUser().toString());
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/logo750width.png")));
        logo.setImage(image);
    }

    // todo: move methods below into a helper/utils class among with other various methods in the codebase

    /**
     * Helper method to change the scene of the current stage from one scene to another.
     * @param source The Button which was clicked to initialize this transition.
     * @param sceneName The destination scene's file name.
     * @throws IOException If the scene does not exist.
     */
    public static void changeScene(Button source, String sceneName) throws IOException {
        Stage stage = (Stage) source.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(sceneName));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);
    }

    /**
     * Helper method to create a custom TextField for controllers with DAO requirements.
     * @param id The JavaFX ID of the TextField.
     * @return The newly created TextField.
     */
    public static TextField customTextField(String id) {
        TextField textField = new TextField();
        textField.setId(id);
        textField.setMaxWidth(Double.POSITIVE_INFINITY);
        return textField;
    }

}
package com.example.cab222a;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class HelloApplication extends Application {
    public static final String TITLE = "DT's Fitness Tracker";
    public static final int WIDTH = 960;
    public static final int HEIGHT = 540;

    /**
     * Creates the database file if it does not already exist.
     * @return Returns true if the file already exists, false if it does not already exist.
     */
    public boolean createDb() {
        File file = new File("main.db");
        if (!file.exists()) {
            try {
                return file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("DB already created.");
        return false;
    }

    /**
     * Handles starting of the application.
     * @param stage The stage for the JavaFX application.
     * @throws IOException If the scene to be loaded does not exist.
     */
    @Override
    public void start(Stage stage) throws IOException {
        // create empty main.db file if it does not exist
        boolean createDbSuccess = createDb();
        System.out.println("DB created: " + createDbSuccess);

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);
        stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Starts the application.
     * @param args Arguments to be passed to the application.
     */
    public static void main(String[] args) {
        launch();
    }
}
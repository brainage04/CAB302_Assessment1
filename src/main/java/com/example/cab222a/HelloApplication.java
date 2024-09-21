package com.example.cab222a;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class HelloApplication extends Application {
    public static final String TITLE = "Senior Fitness Tracker";
    public static final int WIDTH = 640;
    public static final int HEIGHT = 360;

    public boolean createDb() {
        File file = new File("main.db");
        if (!file.exists()) {
            try {
                return file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("DB already created.");
        return false;
    }

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

    public static void main(String[] args) {
        launch();
    }
}
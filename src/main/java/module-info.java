module com.example.cab222a {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    exports com.example.cab222a;
    opens com.example.cab222a to javafx.fxml;
    exports com.example.cab222a.controller;
    opens com.example.cab222a.controller to javafx.fxml;
    exports com.example.cab222a.controller.core;
    opens com.example.cab222a.controller.core to javafx.fxml;
}
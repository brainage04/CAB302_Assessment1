module com.example.cab222a {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires bcrypt;

    opens com.example.cab222a.controller to javafx.fxml;
    opens com.example.cab222a.controller.core to javafx.fxml;
    opens com.example.cab222a.model.resist_train to javafx.base;

    exports com.example.cab222a;
    exports com.example.cab222a.controller;
}

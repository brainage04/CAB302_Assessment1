module com.example.cab222a {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires bcrypt;

    opens com.example.cab222a.controller to javafx.fxml;
    opens com.example.cab222a.controller.core to javafx.fxml;

    exports com.example.cab222a;
    exports com.example.cab222a.controller;
    exports com.example.cab222a.controller.core;
    exports com.example.cab222a.dao.core;
    exports com.example.cab222a.dao.resist_train;
    exports com.example.cab222a.dao.user;
    exports com.example.cab222a.model.core;
    exports com.example.cab222a.model.resist_train;
    exports com.example.cab222a.model.user;
}

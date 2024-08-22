module com.example.cab222a {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.cab222a to javafx.fxml;
    exports com.example.cab222a;
}
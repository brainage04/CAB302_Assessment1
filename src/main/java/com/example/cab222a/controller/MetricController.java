package com.example.cab222a.controller;

import com.example.cab222a.dao.resist_train.HealthMetricDAO;
import com.example.cab222a.model.resist_train.HealthMetric;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import java.sql.Date;
import java.util.List;

public class MetricController {

    @FXML
    private GridPane metricGridPane;

    @FXML
    private TableView<HealthMetric> metricTableView;

    @FXML
    private TableColumn<HealthMetric, Double> weightColumn;
    @FXML
    private TableColumn<HealthMetric, Double> bmiColumn;
    @FXML
    private TableColumn<HealthMetric, Double> bodyFatPercentageColumn;
    @FXML
    private TableColumn<HealthMetric, Double> hydrationLevelsColumn;
    @FXML
    private TableColumn<HealthMetric, Double> measurementColumn;
    @FXML
    private TableColumn<HealthMetric, Date> dateColumn;

    @FXML
    private TextField measurementTextField;
    @FXML
    private TextField weightTextField;
    @FXML
    private TextField bmiTextField;
    @FXML
    private TextField bodyFatPercentageTextField;
    @FXML
    private TextField hydrationLevelsTextField;

    @FXML
    private Button addMetricButton;

    @FXML
    private Button deleteMetricButton;

    @FXML
    private Button updateMetricButton;

    @FXML
    private Label statusLabel;

    @FXML
    public void initialize() {
        // Initialize Table Columns
        weightColumn.setCellValueFactory(new PropertyValueFactory<>("weight"));
        bmiColumn.setCellValueFactory(new PropertyValueFactory<>("bmi"));
        bodyFatPercentageColumn.setCellValueFactory(new PropertyValueFactory<>("bodyFatPercentage"));
        hydrationLevelsColumn.setCellValueFactory(new PropertyValueFactory<>("hydrationLevels"));
        measurementColumn.setCellValueFactory(new PropertyValueFactory<>("measurement"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));



        // Load Metrics
        loadMetricsFromDatabase();

        // Button Event Handlers
        addMetricButton.setOnAction(e -> addMetric());

        // updateMetricButton.setOnAction(e -> updateMetric());
        // deleteMetricButton.setOnAction(e -> deleteMetric());
    }

    private void loadMetricsFromDatabase() {
        List<HealthMetric> metrics = new HealthMetricDAO().getAllItems();
        System.out.println("Loaded metric: " + metrics);
        ObservableList<HealthMetric> observableMetrics = FXCollections.observableArrayList(metrics);
        metricTableView.setItems(observableMetrics);
    }

    private void addMetric() {
        try {
            double weight = Double.parseDouble(weightTextField.getText());
            double bmi = Double.parseDouble(bmiTextField.getText());
            double bodyFatPercentage = Double.parseDouble(bodyFatPercentageTextField.getText());
            double hydrationLevels = Double.parseDouble(hydrationLevelsTextField.getText());
            double measurement = Double.parseDouble(measurementTextField.getText());
            Date currentDate = new Date(System.currentTimeMillis());

            HealthMetric newMetric = new HealthMetric(getCurrentUserId(), weight, bmi, bodyFatPercentage, hydrationLevels, measurement, currentDate);

            new HealthMetricDAO().addItem(newMetric);
            statusLabel.setText("Metric added successfully!");

            loadMetricsFromDatabase();
        } catch (NumberFormatException e) {
            statusLabel.setText("Invalid input! Please enter valid numbers.");
        }
    }

    private int getCurrentUserId() {
        return 1;  // Replace with logic to get current user's ID
    }
}

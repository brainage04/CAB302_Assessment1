package com.example.cab222a.controller;

import com.example.cab222a.dao.resist_train.HealthMetricDAO;
import com.example.cab222a.model.resist_train.HealthMetric;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Date;
import java.util.List;

public class MetricController {

    @FXML
    private GridPane metricGridPane;

    @FXML
    private TableView<HealthMetric> metricTableView;

    @FXML
    private ComboBox<String> metricTypeComboBox;

    @FXML
    private TextField measurementTextField;

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
        metricTypeComboBox.getItems().addAll("Weight", "BMI", "Body Fat Percentage", "Hydration Levels");

        // Initialize TableView Columns
        TableColumn<HealthMetric, String> metricTypeColumn = new TableColumn<>("Metric Type");
        metricTypeColumn.setCellValueFactory(new PropertyValueFactory<>("metricType"));

        TableColumn<HealthMetric, Double> measurementColumn = new TableColumn<>("Measurement");
        measurementColumn.setCellValueFactory(new PropertyValueFactory<>("measurement"));

        TableColumn<HealthMetric, Date> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        metricTableView.getColumns().addAll(metricTypeColumn, measurementColumn, dateColumn);

        // Load existing data into TableView
        loadMetricsFromDatabase();

        // Set action handlers for buttons
        addMetricButton.setOnAction(e -> addMetric());
        updateMetricButton.setOnAction(e -> updateMetric());
        deleteMetricButton.setOnAction(e -> deleteMetric());
    }

    private void loadMetricsFromDatabase() {
        List<HealthMetric> metrics = new HealthMetricDAO().getAllItems();
        if (metrics.isEmpty()) {
            System.out.println("No metrics loaded from the database.");
        } else {
            System.out.println("Metrics loaded: " + metrics);
        }
        metricTableView.getItems().setAll(metrics);  // Update the TableView
    }

    private void addMetric() {
        try {
            String metricType = metricTypeComboBox.getValue();

            if (metricType == null || metricType.isEmpty()) {
                statusLabel.setText("Metric type cannot be empty!");
                return;
            }

            double measurement = Double.parseDouble(measurementTextField.getText());
            Date currentDate = new Date(System.currentTimeMillis());

            HealthMetric newMetric = new HealthMetric(metricType, getCurrentUserId(), measurement, currentDate);

            new HealthMetricDAO().addItem(newMetric);
            statusLabel.setText("Metric added successfully!");

            loadMetricsFromDatabase();
        } catch (NumberFormatException e) {
            statusLabel.setText("Invalid input! Please enter a valid number.");
        }
    }

    private void updateMetric() {
        HealthMetric selectedMetric = metricTableView.getSelectionModel().getSelectedItem();
        if (selectedMetric != null) {
            try {
                selectedMetric.setMeasurement(Double.parseDouble(measurementTextField.getText()));
                selectedMetric.setMetricType(metricTypeComboBox.getValue());

                new HealthMetricDAO().updateItem(selectedMetric);
                statusLabel.setText("Metric updated successfully!");

                loadMetricsFromDatabase();
            } catch (NumberFormatException e) {
                statusLabel.setText("Invalid input! Please enter a valid number.");
            }
        } else {
            statusLabel.setText("Please select a metric to update.");
        }
    }

    private void deleteMetric() {
        HealthMetric selectedMetric = metricTableView.getSelectionModel().getSelectedItem();
        if (selectedMetric != null) {
            new HealthMetricDAO().deleteItem(selectedMetric.getId());
            statusLabel.setText("Metric deleted successfully!");

            loadMetricsFromDatabase();
        } else {
            statusLabel.setText("Please select a metric to delete.");
        }
    }

    private int getCurrentUserId() {
        return 1;
    }
}

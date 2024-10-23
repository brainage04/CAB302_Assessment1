package com.example.cab222a.controller;

import com.example.cab222a.dao.resist_train.HealthMetricDAO;
import com.example.cab222a.model.resist_train.HealthMetric;
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

//        metricTypeComboBox.getItems().addAll("Weight", "BMI", "Body Fat Percentage", "Hydration Levels");



        TableColumn<HealthMetric, String> weightColumn = new TableColumn<>("Weight");
        weightColumn.setCellValueFactory(new PropertyValueFactory<>("Weight"));

        TableColumn<HealthMetric, Double> measurementColumn = new TableColumn<>("Measurement");
        measurementColumn.setCellValueFactory(new PropertyValueFactory<>("measurement"));

        TableColumn<HealthMetric, Date> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));


        metricTableView.getColumns().addAll(weightColumn, measurementColumn, dateColumn);


        loadMetricsFromDatabase();


        addMetricButton.setOnAction(e -> addMetric());
//        updateMetricButton.setOnAction(e -> updateMetric());
//        deleteMetricButton.setOnAction(e -> deleteMetric());
    }


    private void loadMetricsFromDatabase() {
        List<HealthMetric> metrics = new HealthMetricDAO().getAllItems();
        metricTableView.getItems().setAll(metrics);
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
            statusLabel.setText("Invalid input! Please enter a valid number.");
        }
    }


//    private void updateMetric() {
//        HealthMetric selectedMetric = metricTableView.getSelectionModel().getSelectedItem();
//        if (selectedMetric != null) {
//            try {
//                selectedMetric.setMeasurement(Double.parseDouble(measurementTextField.getText()));
//                selectedMetric.setMetricType(metricTypeComboBox.getValue());
//
//                new HealthMetricDAO().updateItem(selectedMetric);
//                statusLabel.setText("Metric updated successfully!");
//
//                loadMetricsFromDatabase();
//            } catch (NumberFormatException e) {
//                statusLabel.setText("Invalid input! Please enter a valid number.");
//            }
//        } else {
//            statusLabel.setText("Please select a metric to update.");
//        }
//    }

//    private void deleteMetric() {
//        HealthMetric selectedMetric = metricTableView.getSelectionModel().getSelectedItem();
//        if (selectedMetric != null) {
//            new HealthMetricDAO().deleteItem(selectedMetric.getId());
//            statusLabel.setText("Metric deleted successfully!");
//
//            loadMetricsFromDatabase();
//        } else {
//            statusLabel.setText("Please select a metric to delete.");
//        }
//    }


    private int getCurrentUserId() {
        return 1;
    }
}

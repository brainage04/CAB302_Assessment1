package com.example.cab222a.controller;

import com.example.cab222a.dao.resist_train.HealthMetricDAO;
import com.example.cab222a.model.resist_train.HealthMetric;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.sql.Date;
import java.util.List;

public class MetricController {

    @FXML
    private GridPane metricGridPane;  // 主布局容器

    @FXML
    private TableView<HealthMetric> metricTableView;  // 显示健康指标的表格

    @FXML
    private ComboBox<String> metricTypeComboBox;  // 指标类型选择框，例如体重、BMI等

    @FXML
    private TextField measurementTextField;  // 用户输入的具体数值

    @FXML
    private Button addMetricButton;  // 用于添加新指标的按钮

    @FXML
    private Button deleteMetricButton;  // 删除选中指标的按钮

    @FXML
    private Button updateMetricButton;  // 更新指标的按钮

    @FXML
    private Label statusLabel;  // 状态信息标签


    @FXML
    public void initialize() {
        // 初始化表格、下拉列表等
        metricTypeComboBox.getItems().addAll("Weight", "BMI", "Body Fat Percentage", "Hydration Levels");

        // 从数据库加载已有的健康指标，并填充到 TableView
        loadMetricsFromDatabase();

        // 设置按钮动作事件
        addMetricButton.setOnAction(e -> addMetric());
        updateMetricButton.setOnAction(e -> updateMetric());
        deleteMetricButton.setOnAction(e -> deleteMetric());
    }


    private void loadMetricsFromDatabase() {
        List<HealthMetric> metrics = new HealthMetricDAO().getAllItems();
        metricTableView.getItems().setAll(metrics);
    }


    private void addMetric() {
        try {
            String metricType = metricTypeComboBox.getValue();
            double measurement = Double.parseDouble(measurementTextField.getText());
            Date currentDate = new Date(System.currentTimeMillis());  // 获取当前日期

            HealthMetric newMetric = new HealthMetric(metricType, getCurrentUserId(), measurement, currentDate);

            // 保存到数据库
            new HealthMetricDAO().addItem(newMetric);
            statusLabel.setText("Metric added successfully!");

            // 重新加载数据
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
        return 1; // 假设当前用户的ID为1，你可以根据实际情况进行修改
    }
}

package com.example.cab222a.controller;

import com.example.cab222a.common.SqliteConnection;
import com.example.cab222a.controller.core.SqliteControllerFunctions;
import com.example.cab222a.dao.core.AbstractObjectDAO;
import com.example.cab222a.dao.resist_train.HealthMetricDAO;
import com.example.cab222a.model.resist_train.HealthMetric;
import com.example.cab222a.model.resist_train.HealthMetricType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class HealthMetricController extends SqliteControllerFunctions<HealthMetric> {
    @Override
    public AbstractObjectDAO<HealthMetric> initItemDAO() {
        return new HealthMetricDAO();
    }

    @Override
    public String getNextSceneName() {
        return "";
    }

    @Override
    public String getPreviousSceneName() {
        return "main-view.fxml";
    }

    @Override
    public HealthMetric generateDefaultItem() {
        return new HealthMetric("New Metric", HealthMetricType.HEIGHT, SqliteConnection.getCurrentUser().getId(), -1, new Date(System.currentTimeMillis()));
    }

    @FXML
    private TextField measurementField;
    @FXML
    private ComboBox<String> metricField;
    @FXML
    private ComboBox<String> filterField;

    @Override
    protected void selectItem(HealthMetric item) {
        super.selectItem(item);

        measurementField.setText(Double.toString(item.getMeasurement()));

        HealthMetricType healthMetricType = item.getMetricType();
        metricField.setValue(healthMetricType.getName());
    }

    public HealthMetricType getTypeByName(String name) {
        HealthMetricType[] types = HealthMetricType.values();

        for (HealthMetricType type : types) {
            if (Objects.equals(type.getName(), name)) {
                return type;
            }
        }

        return null;
    }

    @Override
    protected void onEditConfirm() {
        // Get the selected item from the list view
        HealthMetric selectedItem = getItemListView().getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            selectedItem.setName("%s - %s".formatted(metricField.getValue(), selectedItem.getCreated()));
            selectedItem.setMeasurement(Double.parseDouble(measurementField.getText()));
            selectedItem.setMetricType(getTypeByName(metricField.getValue()));

            getItemDAO().updateItem(selectedItem);
            syncItems();
        }
    }

    @Override
    protected void syncItems() {
        HealthMetricType selectedType = getTypeByName(filterField.getValue());

        // if option is not "All"
        if (selectedType != null) {
            HealthMetric currentItem = itemListView.getSelectionModel().getSelectedItem();
            itemListView.getItems().clear();
            List<HealthMetric> items = ((HealthMetricDAO) itemDAO).getAllItems(selectedType);
            boolean hasItem = !items.isEmpty();
            if (hasItem) {
                itemListView.getItems().addAll(items);

                // If the current item is still in the list, re-select it
                // Otherwise, select the last item in the list
                HealthMetric nextItem = items.getLast();
                if (currentItem != null) {
                    for (HealthMetric item : items) {
                        if (item.getId() == currentItem.getId()) {
                            nextItem = currentItem;
                            break;
                        }
                    }
                }
                System.out.println("Next item: " + nextItem);
                selectItem(nextItem);
            }
            // Show / hide based on whether there are items
            itemContainer.setVisible(hasItem);
        } else {
            super.syncItems();
        }
    }

    @FXML private GridPane filterBox;

    @FXML
    @Override
    public void initialize() {
        // populate item container

        Label itemLabel = new Label("Name:");
        setNameTextField(MainController.customTextField("nameTextField"));
        getNameTextField().setEditable(false);

        getGridPaneContainer().add(itemLabel, 0, 0);
        getGridPaneContainer().add(getNameTextField(), 1, 0);

        Label measurementLabel = new Label("Measurement:");
        measurementField = MainController.customTextField("measurementField");

        getGridPaneContainer().add(measurementLabel, 0, 1);
        getGridPaneContainer().add(measurementField, 1, 1);

        Label metricLabel = new Label("Metric:");

        List<String> metricNames = new ArrayList<>();
        List<HealthMetricType> metrics = new ArrayList<>(Arrays.asList(HealthMetricType.values()));
        for (HealthMetricType metric : metrics) {
            metricNames.add(metric.getName());
        }
        ObservableList<String> options = FXCollections.observableArrayList(
                metricNames
        );
        metricField = new ComboBox<>(options);
        metricField.setId("metricField");
        metricField.setMaxWidth(Double.POSITIVE_INFINITY);

        getGridPaneContainer().add(metricLabel, 0, 2);
        getGridPaneContainer().add(metricField, 1, 2);



        Label filterLabel = new Label("Filter by:");

        options.add("All");
        filterField = new ComboBox<>(options);
        filterField.setId("filterField");
        filterField.setMaxWidth(Double.POSITIVE_INFINITY);
        filterField.setValue("All");
        filterField.setOnAction(actionEvent -> syncItems());

        filterBox.add(filterLabel, 0, 3);
        filterBox.add(filterField, 1, 3);

        super.initialize();
    }
}

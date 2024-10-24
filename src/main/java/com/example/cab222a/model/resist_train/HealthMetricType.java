package com.example.cab222a.model.resist_train;

public enum HealthMetricType {
    HEIGHT("Height"),
    WEIGHT("Weight"),
    BODY_FAT_PERCENTAGE("Body Fat %"),
    OXYGEN("Oxygen"),
    BLOOD_PRESSURE("Blood Pressure"),
    SLEEP("Sleep");

    private final String name;

    HealthMetricType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}

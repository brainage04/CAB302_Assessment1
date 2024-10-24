package com.example.cab222a.model.resist_train;

import com.example.cab222a.model.core.NamedObject;

import java.sql.Date;

public class HealthMetric extends NamedObject {
    private HealthMetricType metricType;
    private int userID;
    private double measurement;
    private Date created;

    public HealthMetric(int id, String name, HealthMetricType metricType, int userID, double measurement, Date created) {
        super(id, name);
        this.metricType = metricType;
        this.userID = userID;
        this.measurement = measurement;
        this.created = created;
    }

    public HealthMetric(String name, HealthMetricType metricType, int userID, double measurement, Date created) {
        super(name);
        this.metricType = metricType;
        this.userID = userID;
        this.measurement = measurement;
        this.created = created;
    }

    // Getters and Setters
    public HealthMetricType getMetricType() {
        return metricType;
    }

    public void setMetricType(HealthMetricType metricType) {
        this.metricType = metricType;
    }

    public int getUserId() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public double getMeasurement() {
        return measurement;
    }

    public void setMeasurement(double measurement) {
        this.measurement = measurement;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created){
        this.created = created;
    }

    @Override
    public String toString() {
        return "HealthMetric{" +
                "id=" + getId() +
                ", name=" + getName() +
                ", userID=" + getUserId() +
                ", metricType=" + getMetricType() +
                ", measurement=" + getMeasurement() +
                ", date=" + getCreated() + "}";
    }

}

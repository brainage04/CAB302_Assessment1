package com.example.cab222a.model.resist_train;

import com.example.cab222a.model.core.NamedObject;

import java.sql.Date;

public class HealthMetric extends NamedObject {
    private int userID;
    private String metricType;
    private double measurement;
    private Date date;

    public HealthMetric(int id, int userID, String metricType, double measurement, Date date) {
        super(id, metricType);
        this.userID = userID;
        this.metricType = metricType;
        this.measurement = measurement;
        this.date = date;
    }

    public HealthMetric(String metricType, int userID, double measurement, Date date){
        super(metricType);
        this.userID = userID;
        this.metricType = metricType;
        this.measurement = measurement;
        this.date = date;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getMetricType() {
        return metricType;
    }

    public void setMetricType(String metricType) {
        this.metricType = metricType;
    }

    public double getMeasurement() {
        return measurement;
    }

    public void setMeasurement(double measurement) {
        this.measurement = measurement;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "HealthMetric{" +
                "id=" + getId() +
                ", metricType=" + getMetricType() +
                ", userID=" + getUserID() +
                ", measurement=" + getMeasurement() +
                ", date=" + getDate() + "}";
    }
}

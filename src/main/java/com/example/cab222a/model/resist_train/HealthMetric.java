package com.example.cab222a.model.resist_train;

import com.example.cab222a.model.core.NamedObject;

import java.sql.Date;

public class HealthMetric extends NamedObject {
    private int userID;
    private double measurement;
    private Date date;
    private double weight;
    private double bmi;
    private double bodyFatPercentage;
    private double hydrationLevels;


    public HealthMetric(int id, int userID, String metricType, double measurement, Date date) {
        super(id, metricType);
        this.userID = userID;
        this.measurement = measurement;
        this.date = date;
    }

//    public HealthMetric(String metricType, int userID, double measurement, Date date){
//        super(metricType);
//        this.userID = userID;
//        this.measurement = measurement;
//        this.date = date;
//    }


    public HealthMetric( int userID, double weight, double bmi, double bodyFatPercentage, double hydrationLevels,double measurement,Date date) {
        super(String.valueOf(userID));
        this.measurement = measurement;
        this.userID = userID;
        this.date = date;
        this.weight = weight;
        this.bmi = bmi;
        this.bodyFatPercentage = bodyFatPercentage;
        this.hydrationLevels = hydrationLevels;
    }
    // Getters and Setters
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public double getBmi() {
        return bmi;
    }

    public void setBmi(double bmi) {
        this.bmi = bmi;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getBodyFatPercentage() {
        return bodyFatPercentage;
    }

    public void setBodyFatPercentage(double bodyFatPercentage) {
        this.bodyFatPercentage = bodyFatPercentage;
    }

    public double getHydrationLevels() {
        return hydrationLevels;
    }

    public void setHydrationLevels(double hydrationLevels) {
        this.hydrationLevels = hydrationLevels;
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

    public void setDate(Date date){
        this.date = date;
    }

    @Override
    public String toString() {
        return "HealthMetric{" +
                "id=" + getId() +
                ", userID=" + getUserID() +
                ", weight=" + getWeight() +
                ", bmi=" + getBmi() +
                ", BodyFatPercentage=" + getBodyFatPercentage() +
                ", HydrationLevels=" + getHydrationLevels() +
                ", measurement=" + getMeasurement() +
                ", date=" + getDate() + "}";
    }

}

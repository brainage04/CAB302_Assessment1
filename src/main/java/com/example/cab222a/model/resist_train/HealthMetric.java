package com.example.cab222a.model.resist_train;

import com.example.cab222a.model.core.NamedObject;

import java.sql.Date;

/**
 * Represents the class and information about the Fitness and Health metrics of the user.
 * This includes the userID, metric type, measurement and date recorded.
 * Used to track various metrics such as Weight, BMI, Steps, etc.
 */
public class HealthMetric extends NamedObject {
    private int userID;
    private String metricType;
    private double measurement;
    private Date date;

    /**
     * Constructs a HealthMetric object with the specific id, userId, metricType, measurement and date.
     * @param id The ID of the metric record.
     * @param userID The ID of the user which this session belongs to.
     * @param metricType The type of health metric (e.g. Weight, Steps)
     * @param measurement THe value of the metric (e.g. 65kg, 10000 steps).
     * @param date The date when the metric was recorded.
     */
    public HealthMetric(int id, int userID, String metricType, double measurement, Date date) {
        super(id, metricType);
        this.userID = userID;
        this.measurement = measurement;
        this.date = date;
    }

    /**
     * Constructs a HealthMetric object with the specific metricType, userId, measurement and date.
     * @param metricType The type of health metric (e.g. Weight, Steps)
     * @param userID The ID of the user which this session belongs to.
     * @param measurement THe value of the metric (e.g. 65kg, 10000 steps).
     * @param date The date when the metric was recorded.
     */
    public HealthMetric(String metricType, int userID, double measurement, Date date){
        super(metricType);
        this.userID = userID;
        this.measurement = measurement;
        this.date = date;
    }

    /**
     * Constructs a HealthMetric object with the specific userId, metricType, measurement and date.
     * @param userID The ID of the user which this session belongs to.
     * @param metricType The type of health metric (e.g. Weight, Steps)
     * @param measurement THe value of the metric (e.g. 65kg, 10000 steps).
     * @param date The date when the metric was recorded.
     */
    public HealthMetric(int userID, String metricType, double measurement, Date date) {
        super(metricType);
        this.userID = userID;
        this.measurement = measurement;
        this.date = date;
    }

    /**
     *  Returns the ID of the user associated with the metric record.
     * @return  UserId as an integer
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Sets the user ID for the metric record.
     * @param userID The ID of the user this metric will be associated to.
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * Returns the type of health metric
     * @return The metric type as a string.
     */
    public String getMetricType() {
        return metricType;
    }

    /**
     * Sets the type of health metric
     * @param metricType The metric type.
     */
    public void setMetricType(String metricType) {
        this.metricType = metricType;
    }

    /**
     * Returns the measurement of the metric type.
     * @return The measurement as a double.
     */
    public double getMeasurement() {
        return measurement;
    }

    /**
     * Sets the measurement value for the metric record.
     * @param measurement The measurement value of the metric type.
     */
    public void setMeasurement(double measurement) {
        this.measurement = measurement;
    }

    /**
     * Returns the date when this metric was recorded
     * @return The date as a Date object.
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets the date when this health metric was recorded.
     * @param date The new date value.
     */
    public void setDate(Date date){
        this.date = date;
    }

    /**
     * String representation of the HealthMetric object
     * @return A string describing the HealthMetric object.
     */
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

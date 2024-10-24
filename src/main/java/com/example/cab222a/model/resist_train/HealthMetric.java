package com.example.cab222a.model.resist_train;

import com.example.cab222a.model.core.NamedObject;

import java.sql.Date;

/**
 * Represents the class and information about the Fitness and Health metrics of the user.
 * This includes the userID, metric type, measurement and date recorded.
 * Used to track various metrics such as Weight, BMI, Steps, etc.
 */
public class HealthMetric extends NamedObject {
    private HealthMetricType metricType;
    private int userId;
    private double measurement;
    private Date created;

    /**
     * Constructs a HealthMetric object with the specific id, userId, metricType, measurement and date.
     * @param id The ID of the metric record.
     * @param userId The ID of the user which this session belongs to.
     * @param metricType The type of health metric (e.g. Weight, Steps)
     * @param measurement THe value of the metric (e.g. 65kg, 10000 steps).
     * @param created The date when the metric was created.
     */
    public HealthMetric(int id, String name, HealthMetricType metricType, int userId, double measurement, Date created) {
        super(id, name);
        this.metricType = metricType;
        this.userId = userId;
        this.measurement = measurement;
        this.created = created;
    }

    /**
     * Constructs a HealthMetric object with the specific metricType, userId, measurement and date.
     * @param metricType The type of health metric (e.g. Weight, Steps)
     * @param userId The ID of the user which this session belongs to.
     * @param measurement THe value of the metric (e.g. 65kg, 10000 steps).
     * @param created The date when the metric was created.
     */
    public HealthMetric(String name, HealthMetricType metricType, int userId, double measurement, Date created) {
        super(name);
        this.metricType = metricType;
        this.userId = userId;
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

    /**
     *
     * @return The ID of the user associated with the record.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the user ID associated with the record.
     * @param userId The ID of the user this metric will be associated with.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets the measurement.
     * @return The measurement as a double.
     */
    public double getMeasurement() {
        return measurement;
    }

    /**
     * Sets the measurement.
     * @param measurement The measurement as a double.
     */
    public void setMeasurement(double measurement) {
        this.measurement = measurement;
    }

    /**
     * Returns the date when this metric was created.
     * @return The date as a Date object.
     */
    public Date getCreated() {
        return created;
    }

    /**
     * Sets the date when this health metric was created.
     * @param created The new date value.
     */
    public void setCreated(Date created){
        this.created = created;
    }

    /**
     * String representation of the HealthMetric object
     * @return A string describing the HealthMetric object.
     */
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

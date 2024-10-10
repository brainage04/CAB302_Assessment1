package com.example.cab222a.model.resist_train;

public class ExerciseTemplate {
    private String name;
    private String type; // e.g., Strength, Cardio
    private int sets;
    private int reps;
    private double weight;
    private String notes;

    // Constructor
    public ExerciseTemplate(String name, String type, int sets, int reps, double weight, String notes) {
        this.name = name;
        this.type = type;
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
        this.notes = notes;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    // Override toString to display exercise name in ComboBox
    @Override
    public String toString() {
        return name; // Adjust this if you'd like a more descriptive display, e.g., `name + " (" + type + ")"`
    }
}

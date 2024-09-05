package com.example.cab222a.model.resist_train;

public class ResistTrainSet {
    private int id;
    private int weight;
    private int repetitions;
    private int restSeconds;
    private int exerciseId;

    public ResistTrainSet(int id, int weight, int repetitions, int restSeconds, int exerciseId) {
        this.id = id;
        this.weight = weight;
        this.repetitions = repetitions;
        this.restSeconds = restSeconds;
        this.exerciseId = exerciseId;
    }

    public ResistTrainSet(int weight, int repetitions, int restSeconds, int exerciseId) {
        this.weight = weight;
        this.repetitions = repetitions;
        this.restSeconds = restSeconds;
        this.exerciseId = exerciseId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(int repetitions) {
        this.repetitions = repetitions;
    }

    public int getRestSeconds() {
        return restSeconds;
    }

    public void setRestSeconds(int restSeconds) {
        this.restSeconds = restSeconds;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }
}

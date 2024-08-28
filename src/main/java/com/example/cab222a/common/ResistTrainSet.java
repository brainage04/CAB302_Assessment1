package com.example.cab222a.common;

public class ResistTrainSet {
    private int weight;
    private int repetitions;
    private int restSeconds;

    public ResistTrainSet(int weight, int repetitions, int restSeconds) {
        this.weight = weight;
        this.repetitions = repetitions;
        this.restSeconds = restSeconds;
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
}

package com.example.cab222a.model.resist_train;

import com.example.cab222a.model.core.NamedObject;

public class ResistTrainSet extends NamedObject {
    private int weight;
    private int repetitions;
    private int restSeconds;
    private int exerciseId;

    public ResistTrainSet(int id, String name, int weight, int repetitions, int restSeconds, int exerciseId) {
        super(id, name);
        this.weight = weight;
        this.repetitions = repetitions;
        this.restSeconds = restSeconds;
        this.exerciseId = exerciseId;
    }

    public ResistTrainSet(String name, int weight, int repetitions, int restSeconds, int exerciseId) {
        super(name);
        this.weight = weight;
        this.repetitions = repetitions;
        this.restSeconds = restSeconds;
        this.exerciseId = exerciseId;
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

    @Override
    public String toString() {
        return "ResistTrainSet{" +
                "id=" + getId() +
                ", name=" + getName() +
                ", weight=" + getWeight() +
                ", repetitions=" + getRepetitions() +
                ", restSeconds=" + getRestSeconds() +
                ", exerciseId=" + getExerciseId() +
                '}';
    }
}

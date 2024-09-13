package com.example.cab222a.model.resist_train;

import com.example.cab222a.model.core.NamedObject;

public class ResistTrainSet extends NamedObject {
    private int exerciseId;
    private int weight;
    private int reps;
    private int rest;
    private int repsInReserve;

    public ResistTrainSet(int id, String name, int exerciseId, int weight, int reps, int rest, int repsInReserve) {
        super(id, name);
        this.exerciseId = exerciseId;
        this.weight = weight;
        this.reps = reps;
        this.rest = rest;
        this.repsInReserve = repsInReserve;
    }

    public ResistTrainSet(String name, int exerciseId, int weight, int reps, int rest, int repsInReserve) {
        super(name);
        this.exerciseId = exerciseId;
        this.weight = weight;
        this.reps = reps;
        this.rest = rest;
        this.repsInReserve = repsInReserve;

    }

    public int getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public int getRest() {
        return rest;
    }

    public void setRest(int rest) {
        this.rest = rest;
    }

    public int getRepsInReserve() {
        return repsInReserve;
    }

    public void setRepsInReserve(int repsInReserve) {
        this.repsInReserve = repsInReserve;
    }

    @Override
    public String toString() {
        return "ResistTrainSet{" +
                "id=" + getId() +
                ", name=" + getName() +
                ", exerciseId=" + getExerciseId() +
                ", weight=" + getWeight() +
                ", reps=" + getReps() +
                ", rest=" + getRest() +
                ", repsInReserve=" + getRepsInReserve() +
                '}';
    }
}

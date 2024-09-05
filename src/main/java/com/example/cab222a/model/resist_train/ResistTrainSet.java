package com.example.cab222a.model.resist_train;

public class ResistTrainSet {
    private int id;
    private String name;
    private int weight;
    private int repetitions;
    private int restSeconds;
    private int exerciseId;

    public ResistTrainSet(int id, String name, int weight, int repetitions, int restSeconds, int exerciseId) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.repetitions = repetitions;
        this.restSeconds = restSeconds;
        this.exerciseId = exerciseId;
    }

    public ResistTrainSet(String name, int weight, int repetitions, int restSeconds, int exerciseId) {
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
                "id=" + id +
                ", weight=" + weight +
                ", repetitions=" + repetitions +
                ", restSeconds=" + restSeconds +
                ", exerciseId=" + exerciseId +
                '}';
    }
}

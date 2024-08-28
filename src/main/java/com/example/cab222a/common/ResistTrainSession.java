package com.example.cab222a.common;

import java.util.List;

public class ResistTrainSession {
    private int id;
    private String name;
    private List<ResistTrainExercise> exercises;

    public ResistTrainSession(String name) {
        this.name = name;
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

    public List<ResistTrainExercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<ResistTrainExercise> exercises) {
        this.exercises = exercises;
    }
}

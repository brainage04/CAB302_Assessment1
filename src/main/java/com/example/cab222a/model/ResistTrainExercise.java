package com.example.cab222a.model;

import java.util.List;

public class ResistTrainExercise {
    private String name;
    private List<ResistTrainSet> sets;

    public ResistTrainExercise(String name, List<ResistTrainSet> sets) {
        this.name = name;
        this.sets = sets;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ResistTrainSet> getSets() {
        return sets;
    }

    public void setSets(List<ResistTrainSet> sets) {
        this.sets = sets;
    }
}

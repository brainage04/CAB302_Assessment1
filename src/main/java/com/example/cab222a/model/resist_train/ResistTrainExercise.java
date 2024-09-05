package com.example.cab222a.model.resist_train;

public class ResistTrainExercise {
    private int id;
    private String name;
    private int sessionId;

    public ResistTrainExercise(int id, String name, int sessionId) {
        this.id = id;
        this.name = name;
        this.sessionId = sessionId;
    }

    public ResistTrainExercise(String name, int sessionId) {
        this.name = name;
        this.sessionId = sessionId;
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

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }
}

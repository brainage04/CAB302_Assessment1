package com.example.cab222a.model.resist_train;

import com.example.cab222a.model.core.NamedObject;

public class ResistTrainExercise extends NamedObject {
    private int sessionId;
    private int exerciseInfoId;

    public ResistTrainExercise(int id, String name, int sessionId, int exerciseInfoId) {
        super(id, name);
        this.sessionId = sessionId;
        this.exerciseInfoId = exerciseInfoId;
    }

    public ResistTrainExercise(String name, int sessionId, int exerciseInfoId) {
        super(name);
        this.sessionId = sessionId;
        this.exerciseInfoId = exerciseInfoId;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public int getExerciseInfoId() {
        return exerciseInfoId;
    }

    public void setExerciseInfoId(int exerciseInfoId) {
        this.exerciseInfoId = exerciseInfoId;
    }

    @Override
    public String toString() {
        return "ResistTrainExercise{" +
                "id=" + getId() +
                ", name=" + getName() +
                ", sessionId=" + getSessionId() +
                ", exerciseInfoId=" + getExerciseInfoId() +
                '}';
    }
}

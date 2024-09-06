package com.example.cab222a.model.resist_train;

import com.example.cab222a.model.core.NamedObject;

public class ResistTrainExercise extends NamedObject {
    private int sessionId;

    public ResistTrainExercise(int id, String name, int sessionId) {
        super(id, name);
        this.sessionId = sessionId;
    }

    public ResistTrainExercise(String name, int sessionId) {
        super(name);
        this.sessionId = sessionId;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public String toString() {
        return "ResistTrainExercise{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", sessionId=" + getSessionId() +
                '}';
    }
}

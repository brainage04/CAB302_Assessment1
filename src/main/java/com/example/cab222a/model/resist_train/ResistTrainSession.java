package com.example.cab222a.model.resist_train;

import com.example.cab222a.model.core.NamedObject;

public class ResistTrainSession extends NamedObject {
    private int userId;

    public ResistTrainSession(int id, String name, int userId) {
        super(id, name);
        this.userId = userId;
    }

    public ResistTrainSession(String name, int userId) {
        super(name);
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "ResistTrainSession{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", userId=" + getUserId() +
                '}';
    }
}

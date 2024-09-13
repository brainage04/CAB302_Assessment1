package com.example.cab222a.model.resist_train;

import com.example.cab222a.model.core.NamedObject;

import java.sql.Date;

public class ResistTrainSession extends NamedObject {
    private int userId;
    private Date created;

    public ResistTrainSession(int id, String name, int userId, Date created) {
        super(id, name);
        this.userId = userId;
        this.created = created;
    }

    public ResistTrainSession(String name, int userId, Date created) {
        super(name);
        this.userId = userId;
        this.created = created;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "ResistTrainSession{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", userId=" + getUserId() +
                ", created=" + getCreated() +
                '}';
    }
}

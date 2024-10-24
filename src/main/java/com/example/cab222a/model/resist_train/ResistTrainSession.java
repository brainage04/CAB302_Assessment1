package com.example.cab222a.model.resist_train;

import com.example.cab222a.model.core.NamedObject;

import java.sql.Date;

/**
 * Data class for resistance training sessions. Used in Data Access Objects (DAOs)
 * and Model View Controllers (MVCs) for this application.
 */
public class ResistTrainSession extends NamedObject {
    private int userId;
    private Date created;

    /**
     * Constructor with ID (for when entries have been assigned an ID in the database).
     * @param id The ID of this session.
     * @param name The name of this session.
     * @param userId The ID of the user which this session belongs to.
     * @param created The date when this session was created.
     */
    public ResistTrainSession(int id, String name, int userId, Date created) {
        super(id, name);
        this.userId = userId;
        this.created = created;
    }

    /**
     * Constructor with ID (for when entries have not been assigned an ID in the database yet).
     * @param name The name of this session.
     * @param userId The ID of the user which this session belongs to.
     * @param created The date when this session was created.
     */
    public ResistTrainSession(String name, int userId, Date created) {
        super(name);
        this.userId = userId;
        this.created = created;
    }

    /**
     *
     * @return The ID of the user which this session belongs to.
     */
    public int getUserId() {
        return userId;
    }

    /**
     *
     * @param userId The ID of the user which this session belongs to.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     *
     * @return The date when this session was created.
     */
    public Date getCreated() {
        return created;
    }

    /**
     *
     * @param created The date when this session was created.
     */
    public void setCreated(Date created) {
        this.created = created;
    }

    /**
     * Concatenates exercise information into a human-readable format.
     * @return The String containing the said user information.
     */
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

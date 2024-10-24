package com.example.cab222a.model.core;

/**
 * Class for objects which use IDs (virtually all Data Access Objects
 * (DAOs) require these)
 */
public class IdentifiedObject {
    private int id;

    /**
     * Constructor with ID (for when the ID has been retrieved from the database)
     * @param id The ID of the object.
     */
    public IdentifiedObject(int id) {
        this.id = id;
    }

    /**
     * Constructor without ID (for when the object is being inserted into the database)
     */
    public IdentifiedObject() {

    }

    /**
     * Gets the ID of the object.
     * @return The ID of the object.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the object.
     * @param id The ID being set.
     */
    public void setId(int id) {
        this.id = id;
    }
}

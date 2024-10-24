package com.example.cab222a.model.core;

/**
 * Class for named objects used in Data Access Objects (DAOs)
 * and Model View Controllers (MVCs) for this application.
 */
public class NamedObject extends IdentifiedObject {
    private String name;

    /**
     * Constructor with ID (for when the ID has been retrieved from the database)
     * @param id The ID of the object.
     * @param name The name of the object.
     */
    public NamedObject(int id, String name) {
        super(id);
        this.name = name;
    }

    /**
     * Constructor without ID (for when the object is being inserted into the database)
     * @param name The name of the object.
     */
    public NamedObject(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

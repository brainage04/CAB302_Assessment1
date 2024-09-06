package com.example.cab222a.model.core;

public class NamedObject extends IdentifiedObject {
    private String name;

    public NamedObject(int id, String name) {
        super(id);
        this.name = name;
    }

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

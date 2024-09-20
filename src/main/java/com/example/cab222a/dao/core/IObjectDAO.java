package com.example.cab222a.dao.core;

import com.example.cab222a.model.core.IdentifiedObject;

import java.util.List;

/**
 * Generic interface for a Data Access Object that handles the
 * CRUD operations for generic classes.
 */
public interface IObjectDAO<T extends IdentifiedObject> {
    /**
     * Adds a new item to the database.
     *
     * @param item The item to add.
     * @return The ID of the item added.
     */
    int addItem(T item);

    /**
     * Gets an existing item from the database.
     *
     * @param id The ID of the item to retrieve.
     * @return The item with the specified ID.
     */
    T getItem(int id);

    /**
     * Gets all existing items from the database.
     *
     * @return A list of all items.
     */
    List<T> getAllItems();

    /**
     * Updates an existing item in the database.
     *
     * @param item The item to update.
     */
    void updateItem(T item);

    /**
     * Deletes an existing item in the database.
     *
     * @param id The ID of the item to delete.
     */
    void deleteItem(int id);

    /**
     * Resets the table.
     */
    void resetTable();
}

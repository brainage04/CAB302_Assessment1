package com.example.cab222a.model.core;

import java.util.List;

/**
 * Generic interface for a Data Access Object that handles the
 * CRUD operations for generic classes within the database.
 */
public interface IObjectDAO<T> {
    /**
     * Adds a new item to the database.
     * @param item The item to add.
     */
    void addItem(T item);
    /**
     * Updates an existing item in the database.
     * @param item The item to update.
     */
    void updateItem(T item);
    /**
     * Deletes an item from the database.
     * @param item The item to delete.
     */
    void deleteItem(T item);
    /**
     * Retrieves an item from the database.
     * @param id The id of the item to retrieve.
     * @return The item with the given id, or null if not found.
     */
    T getItem(int id);
    /**
     * Retrieves all items from the database.
     * @return A list of all items in the database.
     */
    List<T> getAllItems();
}
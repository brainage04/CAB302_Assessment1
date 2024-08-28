package com.example.cab222a.common;

import java.util.List;

/**
 * Interface for the ResistTrainSession Data Access Object that handles
 * the CRUD operations for the ResistTrainSession class with the database.
 */
public interface IResistTrainSessionDAO {
    /**
     * Adds a new item to the database.
     * @param item The item to add.
     */
    void addItem(ResistTrainSession item);
    /**
     * Updates an existing item in the database.
     * @param item The item to update.
     */
    void updateItem(ResistTrainSession item);
    /**
     * Deletes an item from the database.
     * @param item The item to delete.
     */
    void deleteItem(ResistTrainSession item);
    /**
     * Retrieves an item from the database.
     * @param id The id of the item to retrieve.
     * @return The item with the given id, or null if not found.
     */
    ResistTrainSession getItem(int id);
    /**
     * Retrieves all items from the database.
     * @return A list of all items in the database.
     */
    List<ResistTrainSession> getAllItems();
}
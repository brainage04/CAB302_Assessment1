package com.example.cab222a.model.user;

import java.util.List;

/**
 * Interface for the User Data Access Object that handles
 * the CRUD operations for the User class with the database.
 */
public interface IUserDAO {
    /**
     * Adds a new item to the database.
     * @param item The item to add.
     */
    void addItem(User item);
    /**
     * Updates an existing item in the database.
     * @param item The item to update.
     */
    void updateItem(User item);
    /**
     * Deletes an item from the database.
     * @param item The item to delete.
     */
    void deleteItem(User item);
    /**
     * Retrieves an item from the database.
     * @param id The id of the item to retrieve.
     * @return The item with the given id, or null if not found.
     */
    User getItem(int id);
    /**
     * Retrieves a user from the database (used when logging in).
     * @param email The email of the user to retrieve.
     * @param password The password of the user to retrieve.
     * @return The user with the given email and password, or null if not found.
     */
    User getItem(String email, String password);
    /**
     * Retrieves all items from the database.
     * @return A list of all items in the database.
     */
    List<User> getAllItems();
}
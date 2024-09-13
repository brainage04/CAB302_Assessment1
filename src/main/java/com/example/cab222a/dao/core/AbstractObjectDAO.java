package com.example.cab222a.dao.core;

import com.example.cab222a.common.SqliteConnection;
import com.example.cab222a.model.core.IdentifiedObject;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Generic interface for a Data Access Object that handles the
 * CRUD operations for generic classes within the database.
 */
public abstract class AbstractObjectDAO<T extends IdentifiedObject> {
    public AbstractObjectDAO() {
        System.out.println("Creating DAO for " + tableName());
        createTable();
    }

    protected abstract String tableName();
    protected abstract String createTableVariables();
    protected abstract PreparedStatement addItemStatement(T item) throws SQLException;
    protected abstract PreparedStatement updateItemStatement(T item) throws SQLException;
    protected PreparedStatement deleteItemStatement(int id) throws SQLException {
        PreparedStatement statement = SqliteConnection.getInstance().prepareStatement("DELETE FROM " + tableName() + " WHERE id = ?");
        statement.setInt(1, id);
        return statement;
    }
    protected PreparedStatement getItemStatement(int id) throws SQLException {
        PreparedStatement statement = SqliteConnection.getInstance().prepareStatement("SELECT * FROM " + tableName() + " WHERE id = ?");
        statement.setInt(1, id);
        return statement;
    }
    protected abstract PreparedStatement getAllItemsStatement() throws SQLException;

    public void createTable() {
        try {
            Statement statement = SqliteConnection.getInstance().createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS " + tableName() + " (" + createTableVariables() + ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a new item to the database.
     *
     * @param item The item to add.
     */
    public void addItem(T item) {
        try {
            addItemStatement(item).executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates an existing item in the database.
     *
     * @param item The item to add.
     */
    public void updateItem(T item) {
        try {
            updateItemStatement(item).executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes an item from the database.
     *
     * @param id The ID of the item to add.
     */
    public void deleteItem(int id) {
        try {
            deleteItemStatement(id).executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves an item from the database.
     *
     * @param id The id of the item to retrieve.
     * @return The item with the given id, or null if not found.
     */
    public abstract T getItem(int id);

    /**
     * Retrieves all items from the database.
     *
     * @return A list of all items in the database.
     */
    public abstract List<T> getAllItems();
}
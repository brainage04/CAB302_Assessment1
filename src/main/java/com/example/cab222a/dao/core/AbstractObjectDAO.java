package com.example.cab222a.dao.core;

import com.example.cab222a.common.SqliteConnection;
import com.example.cab222a.model.core.IdentifiedObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Abstract class for a DAO that handles the CRUD
 * operations for generic classes within the database.
 */
public abstract class AbstractObjectDAO<T extends IdentifiedObject> implements IObjectDAO<T> {
    /**
     * Constructor which creates the table for the DAO
     * if it does not already exist.
     */
    public AbstractObjectDAO() {
        createTable();
    }

    /**
     *
     * @return The name of the table for the DAO.
     */
    public abstract String tableName();
    /**
     *
     * @return A String containing the variables for the DAO's table
     * in the database and their properties.
     */
    protected abstract String createTableVariables();
    /**
     * Prepares an SQL statement to add an item to the database table.
     * Relies on the value of parent elements (e.g. ResistTrainExercise relying on ID of current ResistTrainSession)
     * @param item Item to be added to the database.
     * @return A prepared statement ready to be executed.
     * @throws SQLException If the PreparedStatement contains invalid syntax.
     */
    protected abstract PreparedStatement addItemStatement(T item) throws SQLException;
    /**
     * Prepares an SQL statement to add a copied item to the database table.
     * Does not rely on the value of parent elements (e.g. ResistTrainExercise relying on ID of current ResistTrainSession)
     * @param item Item to be added to the database.
     * @return A prepared statement ready to be executed.
     * @throws SQLException If the PreparedStatement contains invalid syntax.
     */
    protected abstract PreparedStatement addCopiedItemStatement(T item) throws SQLException;
    /**
     * Prepares an SQL statement to update an item in the database table based on its primary keys.
     * @param item The item to update.
     * @return A prepared statement ready to be executed.
     * @throws SQLException If the PreparedStatement contains invalid syntax.
     */
    protected abstract PreparedStatement updateItemStatement(T item) throws SQLException;
    /**
     * Prepares an SQL statement to delete an item in the database table based on its ID.
     * @param id The ID of the item to delete.
     * @return A prepared statement ready to be executed.
     * @throws SQLException If the PreparedStatement contains invalid syntax.
     */
    protected PreparedStatement deleteItemStatement(int id) throws SQLException {
        PreparedStatement statement = SqliteConnection.getInstance().prepareStatement("DELETE FROM " + tableName() + " WHERE id = ?");
        statement.setInt(1, id);
        return statement;
    }
    /**
     * Prepares an SQL statement to retrieve an item from the database table based on its ID.
     * @param id The ID of the item to retrieve from the database.
     * @return A prepared statement ready to be executed.
     * @throws SQLException If the PreparedStatement contains invalid syntax.
     */
    protected PreparedStatement getItemStatement(int id) throws SQLException {
        PreparedStatement statement = SqliteConnection.getInstance().prepareStatement("SELECT * FROM " + tableName() + " WHERE id = ?");
        statement.setInt(1, id);
        return statement;
    }
    /**
     * Prepares an SQL statement to retrieve all items from the database table.
     * @return A prepared statement ready to be executed.
     * @throws SQLException If the PreparedStatement contains invalid syntax.
     */
    protected abstract PreparedStatement getAllItemsStatement() throws SQLException;

    public int addItem(T item) {
        System.out.println("Adding item to table " + tableName() + ": \n" + item.toString());
        try (PreparedStatement statement = addItemStatement(item)) {
            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                ResultSet set = statement.getGeneratedKeys();

                set.next();

                int id = set.getInt(1);
                System.out.println("Item added. New item ID: " + id);
                return id;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public int addCopiedItem(T item) {
        System.out.println("Adding item to table " + tableName() + ": \n" + item.toString());
        try (PreparedStatement statement = addCopiedItemStatement(item)) {
            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                ResultSet set = statement.getGeneratedKeys();

                set.next();

                int id = set.getInt(1);
                System.out.println("Item added. New item ID: " + id);
                return id;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public abstract T getItem(int id);

    public abstract List<T> getAllItems();

    public void updateItem(T item) {
        try (PreparedStatement statement = updateItemStatement(item)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteItem(int id) {
        try (PreparedStatement statement = deleteItemStatement(id)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes the table for this DAO from the database.
     */
    public void dropTable() {
        try (Statement statement = SqliteConnection.getInstance().createStatement()) {
            statement.execute("DROP TABLE " + tableName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates the table for this DAO in the database
     * if it does not already exist.
     */
    public void createTable() {
        try (Statement statement = SqliteConnection.getInstance().createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS " + tableName() + " (" + createTableVariables() + ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void resetTable() {
        dropTable();
        createTable();
    }
}
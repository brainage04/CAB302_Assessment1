package com.example.cab222a.dao.core;

import com.example.cab222a.common.SqliteConnection;
import com.example.cab222a.model.core.IdentifiedObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Abstract class for a Data Access Object that handles the
 * CRUD operations for generic classes within the database.
 */
public abstract class AbstractObjectDAO<T extends IdentifiedObject> implements IObjectDAO<T> {
    public AbstractObjectDAO() {
        createTable();
    }

    public abstract String tableName();
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

    public int addItem(T item) {
        System.out.println("Adding item to table " + tableName() + ": \n" + item.toString());
        try (PreparedStatement statement = addItemStatement(item)) {
            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                ResultSet set = statement.getGeneratedKeys();

                if (set.next()) {
                    int id = set.getInt(1);
                    System.out.println("Item added. New item ID: " + id);
                    return id;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public abstract T getItem(int id);

    public abstract List<T> getAllItems();

    public void updateItem(T item) {
        try {
            updateItemStatement(item).executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteItem(int id) {
        try {
            deleteItemStatement(id).executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropTable() {
        try (Statement statement = SqliteConnection.getInstance().createStatement()) {
            statement.execute("DROP TABLE " + tableName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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
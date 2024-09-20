package com.example.cab222a.dao.core;

import com.example.cab222a.common.SqliteConnection;
import com.example.cab222a.model.core.IdentifiedObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Generic interface for a Data Access Object that handles the
 * CRUD operations for generic classes within the database.
 */
public abstract class AbstractObjectDAO<T extends IdentifiedObject> implements IObjectDAO<T> {
    public AbstractObjectDAO() {
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

    public int addItem(T item) {
        try (PreparedStatement statement = addItemStatement(item)) {
            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                ResultSet set = statement.getGeneratedKeys();

                if (set.next()) {
                    return set.getInt(1);
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
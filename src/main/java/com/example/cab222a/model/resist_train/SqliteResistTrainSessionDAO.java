package com.example.cab222a.model.resist_train;

import com.example.cab222a.common.SqliteConnection;
import com.example.cab222a.model.core.IObjectDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqliteResistTrainSessionDAO extends IObjectDAO<ResistTrainSession> {
    @Override
    protected String tableName() {
        return "resist_train_sessions";
    }

    @Override
    protected String createTableQuery() {
        return "CREATE TABLE IF NOT EXISTS " + tableName() + " ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "name VARCHAR NOT NULL,"
                + "user_id INTEGER NOT NULL,"
                + "FOREIGN KEY (user_id) REFERENCES users(id)"
                + ")";
    }

    @Override
    protected PreparedStatement addItemStatement(ResistTrainSession item) throws SQLException {
        PreparedStatement statement = SqliteConnection.getInstance().prepareStatement("INSERT INTO " + tableName() + " (name, user_id) VALUES (?, ?)");
        statement.setString(1, item.getName());
        statement.setInt(2, SqliteConnection.getCurrentUser().getId());
        return statement;
    }

    @Override
    protected PreparedStatement updateItemStatement(ResistTrainSession item) throws SQLException {
        PreparedStatement statement = SqliteConnection.getInstance().prepareStatement("UPDATE " + tableName() + " SET name = ? WHERE id = ?");
        statement.setString(1, item.getName());
        statement.setInt(2, item.getId());
        return statement;
    }

    @Override
    protected PreparedStatement getAllItemsStatement() throws SQLException {
        PreparedStatement statement = SqliteConnection.getInstance().prepareStatement("SELECT * FROM " + tableName() + " WHERE user_id = ?");
        statement.setInt(1, SqliteConnection.getCurrentUser().getId());
        return statement;
    }

    @Override
    public ResistTrainSession getItem(int id) {
        try {
            ResultSet set = getItemStatement(id).executeQuery();

            if (set.next()) {
                String name = set.getString("name");
                int userId = set.getInt("user_id");

                return new ResistTrainSession(id, name, userId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<ResistTrainSession> getAllItems() {
        List<ResistTrainSession> items = new ArrayList<>();

        try {
            ResultSet set = getAllItemsStatement().executeQuery();

            while (set.next()) {
                int id = set.getInt("id");
                String name = set.getString("name");
                int userId = set.getInt("user_id");

                items.add(
                        new ResistTrainSession(id, name, userId)
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }
}
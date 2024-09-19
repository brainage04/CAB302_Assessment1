package com.example.cab222a.dao.resist_train;

import com.example.cab222a.common.SqliteConnection;
import com.example.cab222a.dao.core.AbstractObjectDAO;
import com.example.cab222a.model.resist_train.ResistTrainSession;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ResistTrainSessionDAO extends AbstractObjectDAO<ResistTrainSession> {
    @Override
    protected String tableName() {
        return "resistTrainSessions";
    }

    @Override
    protected String createTableVariables() {
        return "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name VARCHAR NOT NULL, "
                + "userId INTEGER NOT NULL, "
                + "created DATETIME NOT NULL, "
                + "FOREIGN KEY (userId) REFERENCES users(id)";
    }

    @Override
    protected PreparedStatement addItemStatement(ResistTrainSession item) throws SQLException {
        PreparedStatement statement = SqliteConnection.getInstance().prepareStatement("INSERT INTO " + tableName() + " (name, userId, created) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, item.getName());
        statement.setInt(2, SqliteConnection.getCurrentUser().getId());
        statement.setDate(3, item.getCreated());
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
        PreparedStatement statement = SqliteConnection.getInstance().prepareStatement("SELECT * FROM " + tableName() + " WHERE userId = ?");
        statement.setInt(1, SqliteConnection.getCurrentUser().getId());
        return statement;
    }

    @Override
    public ResistTrainSession getItem(int id) {
        try (ResultSet set = getItemStatement(id).executeQuery()) {
            if (set.next()) {
                String name = set.getString("name");
                int userId = set.getInt("userId");
                Date created = set.getDate("created");

                return new ResistTrainSession(id, name, userId, created);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<ResistTrainSession> getAllItems() {
        List<ResistTrainSession> items = new ArrayList<>();

        try (ResultSet set = getAllItemsStatement().executeQuery()) {
            while (set.next()) {
                int id = set.getInt("id");
                String name = set.getString("name");
                int userId = set.getInt("userId");
                Date created = set.getDate("created");

                items.add(
                        new ResistTrainSession(id, name, userId, created)
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }
}
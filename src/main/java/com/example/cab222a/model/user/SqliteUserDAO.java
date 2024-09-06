package com.example.cab222a.model.user;

import com.example.cab222a.common.SqliteConnection;
import com.example.cab222a.model.core.IObjectDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqliteUserDAO extends IObjectDAO<User> {
    @Override
    protected String tableName() {
        return "users";
    }

    @Override
    protected String createTableQuery() {
        return "CREATE TABLE IF NOT EXISTS " + tableName() + " ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "firstName VARCHAR NOT NULL,"
                + "lastName VARCHAR NOT NULL,"
                + "email VARCHAR NOT NULL UNIQUE,"
                + "phone VARCHAR NOT NULL,"
                + "password VARCHAR NOT NULL"
                + ")";
    }

    protected PreparedStatement addItemStatement(User item) throws SQLException {
        PreparedStatement statement = SqliteConnection.getInstance().prepareStatement("INSERT INTO " + tableName() + " (firstName, lastName, email, phone, password) VALUES (?, ?, ?, ?, ?)");
        statement.setString(1, item.getFirstName());
        statement.setString(2, item.getLastName());
        statement.setString(3, item.getEmail());
        statement.setString(4, item.getPhone());
        statement.setString(5, item.getPassword());
        return statement;
    }

    protected PreparedStatement updateItemStatement(User item) throws SQLException {
        PreparedStatement statement = SqliteConnection.getInstance().prepareStatement("UPDATE " + tableName() + " SET firstName = ?, lastName = ?, email = ?, phone = ?, password = ? WHERE id = ?");
        statement.setString(1, item.getFirstName());
        statement.setString(2, item.getLastName());
        statement.setString(3, item.getEmail());
        statement.setString(4, item.getPhone());
        statement.setString(5, item.getPassword());
        statement.setInt(6, item.getId());
        return statement;
    }

    @Override
    protected PreparedStatement getAllItemsStatement() throws SQLException {
        return SqliteConnection.getInstance().prepareStatement("SELECT * FROM " + tableName());
    }

    @Override
    public User getItem(int id) {
        try {
            ResultSet set = getItemStatement(id).executeQuery();

            if (set.next()) {
                String firstName = set.getString("firstName");
                String lastName = set.getString("lastName");
                String email = set.getString("email");
                String phone = set.getString("phone");
                String password = set.getString("password");

                return new User(id, firstName, lastName, email, phone, password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public User getItem(String email, String password) {
        try {
            PreparedStatement statement = SqliteConnection.getInstance().prepareStatement("SELECT * FROM " + tableName() + " WHERE email = ? AND password = ?");
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet set = statement.executeQuery();

            if (set.next()) {
                int id = set.getInt("id");
                String firstName = set.getString("firstName");
                String lastName = set.getString("lastName");
                String phone = set.getString("phone");

                return new User(id, firstName, lastName, email, phone, password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<User> getAllItems() {
        List<User> users = new ArrayList<>();

        try {
            Statement statement = SqliteConnection.getInstance().createStatement();
            String query = "SELECT * FROM " + tableName();
            ResultSet set = statement.executeQuery(query);

            while (set.next()) {
                int id = set.getInt("id");
                String firstName = set.getString("firstName");
                String lastName = set.getString("lastName");
                String email = set.getString("email");
                String phone = set.getString("phone");
                String password = set.getString("password");
                users.add(
                        new User(id, firstName, lastName, email, phone, password)
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }
}
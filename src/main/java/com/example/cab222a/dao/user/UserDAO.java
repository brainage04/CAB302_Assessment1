package com.example.cab222a.dao.user;

import com.example.cab222a.common.SqliteConnection;
import com.example.cab222a.dao.core.AbstractObjectDAO;
import com.example.cab222a.model.user.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends AbstractObjectDAO<User> {
    @Override
    protected String tableName() {
        return "users";
    }

    @Override
    protected String createTableVariables() {
        return "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "created DATETIME NOT NULL, "
                + "firstName VARCHAR NOT NULL, "
                + "lastName VARCHAR NOT NULL, "
                + "email VARCHAR NOT NULL UNIQUE, "
                + "phone VARCHAR NOT NULL, "
                + "password VARCHAR NOT NULL";
    }

    protected PreparedStatement addItemStatement(User item) throws SQLException {
        PreparedStatement statement = SqliteConnection.getInstance().prepareStatement("INSERT INTO " + tableName() + " (created, firstName, lastName, email, phone, password) VALUES (?, ?, ?, ?, ?, ?)");
        statement.setDate(1, item.getCreated());
        statement.setString(2, item.getFirstName());
        statement.setString(3, item.getLastName());
        statement.setString(4, item.getEmail());
        statement.setString(5, item.getPhone());
        statement.setString(6, item.getPassword());
        return statement;
    }

    protected PreparedStatement updateItemStatement(User item) throws SQLException {
        PreparedStatement statement = SqliteConnection.getInstance().prepareStatement("UPDATE " + tableName() + " SET created = ?, firstName = ?, lastName = ?, email = ?, phone = ?, password = ? WHERE id = ?");
        statement.setDate(1, item.getCreated());
        statement.setString(2, item.getFirstName());
        statement.setString(3, item.getLastName());
        statement.setString(4, item.getEmail());
        statement.setString(5, item.getPhone());
        statement.setString(6, item.getPassword());
        statement.setInt(7, item.getId());
        return statement;
    }

    @Override
    protected PreparedStatement getAllItemsStatement() throws SQLException {
        return SqliteConnection.getInstance().prepareStatement("SELECT * FROM " + tableName());
    }

    @Override
    public User addAndGetItem(User item) {
        try {
            ResultSet set = addItemStatement(item).executeQuery();
            if (set.next()) {
                int id = set.getInt("id");
                Date created = set.getDate("created");
                String firstName = set.getString("firstName");
                String lastName = set.getString("lastName");
                String email = set.getString("email");
                String phone = set.getString("phone");
                String password = set.getString("password");

                return new User(id, created, firstName, lastName, email, phone, password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public User getItem(int id) {
        try {
            ResultSet set = getItemStatement(id).executeQuery();

            if (set.next()) {
                Date created = set.getDate("created");
                String firstName = set.getString("firstName");
                String lastName = set.getString("lastName");
                String email = set.getString("email");
                String phone = set.getString("phone");
                String password = set.getString("password");

                return new User(id, created, firstName, lastName, email, phone, password);
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
                Date created = set.getDate("created");
                String firstName = set.getString("firstName");
                String lastName = set.getString("lastName");
                String phone = set.getString("phone");

                return new User(id, created, firstName, lastName, email, phone, password);
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
                Date created = set.getDate("created");
                String firstName = set.getString("firstName");
                String lastName = set.getString("lastName");
                String email = set.getString("email");
                String phone = set.getString("phone");
                String password = set.getString("password");
                users.add(
                        new User(id, created, firstName, lastName, email, phone, password)
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }
}
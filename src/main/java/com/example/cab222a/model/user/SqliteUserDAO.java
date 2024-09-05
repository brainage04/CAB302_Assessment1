package com.example.cab222a.model.user;

import com.example.cab222a.common.SqliteConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqliteUserDAO implements IUserDAO {
    private Connection connection = SqliteConnection.getInstance();

    public SqliteUserDAO() {
        createTable();
    }

    private void createTable() {
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS users ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "firstName VARCHAR NOT NULL,"
                    + "lastName VARCHAR NOT NULL,"
                    + "email VARCHAR NOT NULL UNIQUE,"
                    + "phone VARCHAR NOT NULL,"
                    + "password VARCHAR NOT NULL"
                    + ")";
            statement.execute(query);
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    @Override
    public void addItem(User item) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO users (firstName, lastName, email, phone, password) VALUES (?, ?, ?, ?, ?)");
            statement.setString(1, item.getFirstName());
            statement.setString(2, item.getLastName());
            statement.setString(3, item.getEmail());
            statement.setString(4, item.getPhone());
            statement.setString(5, item.getPassword());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    @Override
    public void updateItem(User item) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE users SET firstName = ?, lastName = ?, email = ?, phone = ?, password = ? WHERE id = ?");
            statement.setString(1, item.getFirstName());
            statement.setString(2, item.getLastName());
            statement.setString(3, item.getEmail());
            statement.setString(4, item.getPhone());
            statement.setString(5, item.getPassword());
            statement.setInt(6, item.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    @Override
    public void deleteItem(User item) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE id = ?");
            statement.setInt(1, item.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    @Override
    public User getItem(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE id = ?");
            statement.setInt(1, id);
            ResultSet set = statement.executeQuery();

            if (set.next()) {
                String firstName = set.getString("firstName");
                String lastName = set.getString("lastName");
                String email = set.getString("email");
                String phone = set.getString("phone");
                String password = set.getString("password");

                return new User(id, firstName, lastName, email, phone, password);
            }
        } catch (SQLException e) {
            System.err.println(e);
        }

        return null;
    }

    @Override
    public User getItem(String email, String password) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE email = ? AND password = ?");
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
            System.err.println(e);
        }

        return null;
    }

    @Override
    public List<User> getAllItems() {
        List<User> users = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM users";
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
            System.err.println(e);
        }

        return users;
    }
}
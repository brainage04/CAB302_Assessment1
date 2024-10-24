package com.example.cab222a.dao.user;

import com.example.cab222a.common.SqliteConnection;
import com.example.cab222a.dao.core.AbstractObjectDAO;
import com.example.cab222a.model.user.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import at.favre.lib.crypto.bcrypt.BCrypt;

/**
 * DAO for the User class that handles the CRUD
 * operations within the database.
 */
public class UserDAO extends AbstractObjectDAO<User> {
    @Override
    public String tableName() {
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
        PreparedStatement statement = SqliteConnection.getInstance().prepareStatement("INSERT INTO " + tableName() + " (created, firstName, lastName, email, phone, password) VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

        String hashedPassword = BCrypt.withDefaults().hashToString(12, item.getPassword().toCharArray());

        statement.setDate(1, item.getCreated());
        statement.setString(2, item.getFirstName());
        statement.setString(3, item.getLastName());
        statement.setString(4, item.getEmail());
        statement.setString(5, item.getPhone());
        statement.setString(6, hashedPassword);
        return statement;
    }

    protected PreparedStatement updateItemStatement(User item) throws SQLException {
        PreparedStatement statement = SqliteConnection.getInstance().prepareStatement("UPDATE " + tableName() + " SET created = ?, firstName = ?, lastName = ?, email = ?, phone = ?, password = ? WHERE id = ?");
        String hashedPassword = BCrypt.withDefaults().hashToString(12, item.getPassword().toCharArray());


        statement.setDate(1, item.getCreated());
        statement.setString(2, item.getFirstName());
        statement.setString(3, item.getLastName());
        statement.setString(4, item.getEmail());
        statement.setString(5, item.getPhone());
        statement.setString(6, hashedPassword);
        statement.setInt(7, item.getId());
        return statement;
    }

    @Override
    protected PreparedStatement getAllItemsStatement() throws SQLException {
        return SqliteConnection.getInstance().prepareStatement("SELECT * FROM " + tableName());
    }

    @Override
    public User getItem(int id) {
        try (ResultSet set = getItemStatement(id).executeQuery()) {
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
        try (PreparedStatement statement = SqliteConnection.getInstance().prepareStatement("SELECT * FROM " + tableName() + " WHERE email = ?")) {
            statement.setString(1, email);
            //statement.setString(2, password);
            ResultSet set = statement.executeQuery();

            if (set.next()) {
                String passwordHash = set.getString("password");
                BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), passwordHash);
                if(result.verified){
                    int id = set.getInt("id");
                    Date created = set.getDate("created");
                    String firstName = set.getString("firstName");
                    String lastName = set.getString("lastName");
                    String phone = set.getString("phone");
                    
                    return new User(id, created, firstName, lastName, email, phone, passwordHash);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<User> getAllItems() {
        List<User> users = new ArrayList<>();

        try (Statement statement = SqliteConnection.getInstance().createStatement()) {
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

    public boolean emailExists(String email) {
        String query = "SELECT COUNT(*) FROM users WHERE email = ?";
        try (PreparedStatement statement = SqliteConnection.getInstance().prepareStatement(query)){
            statement.setString(1, email);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                return set.getInt(1) > 0;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
package com.example.cab222a.dao.resist_train;

import com.example.cab222a.common.SqliteConnection;
import com.example.cab222a.dao.core.AbstractObjectDAO;
import com.example.cab222a.model.resist_train.HealthMetric;
import com.example.cab222a.model.resist_train.HealthMetricType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class HealthMetricDAO extends AbstractObjectDAO<HealthMetric> {

    @Override
    public String tableName() {
        return "healthMetric";
    }

    @Override
    protected String createTableVariables() {
        return "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name VARCHAR NOT NULL, " +
                "userId INTEGER NOT NULL, " +
                "metricType INTEGER NOT NULL, " +
                "measurement DOUBLE NOT NULL, " +
                "created DATE NOT NULL, " +
                "FOREIGN KEY (userId) REFERENCES users(id)";
    }

    @Override
    protected PreparedStatement addItemStatement(HealthMetric item) throws SQLException {
        PreparedStatement statement = SqliteConnection.getInstance().prepareStatement(
                "INSERT INTO " + tableName() + " (name, metricType, userId, measurement, created) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, item.getName());
        statement.setInt(2, item.getMetricType().ordinal());
        statement.setInt(3, SqliteConnection.getCurrentUser().getId());
        statement.setDouble(4, item.getMeasurement());
        statement.setDate(5, item.getCreated());
        return statement;
    }

    @Override
    protected PreparedStatement updateItemStatement(HealthMetric item) throws SQLException {
        PreparedStatement statement = SqliteConnection.getInstance().prepareStatement(
                "UPDATE " + tableName() + " SET name = ?, metricType = ?, userId = ?, measurement = ?, created = ? WHERE id = ?", Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, item.getName());
        statement.setInt(2, item.getMetricType().ordinal());
        statement.setInt(3, SqliteConnection.getCurrentUser().getId());
        statement.setDouble(4, item.getMeasurement());
        statement.setDate(5, item.getCreated());
        statement.setInt(6, item.getId());
        return statement;
    }

    @Override
    protected PreparedStatement getAllItemsStatement() throws SQLException {
        PreparedStatement statement = SqliteConnection.getInstance().prepareStatement("SELECT * FROM " + tableName() + " WHERE userId = ?");
        statement.setInt(1, SqliteConnection.getCurrentUser().getId());
        return statement;
    }

    protected PreparedStatement getAllItemsStatement(HealthMetricType type) throws SQLException {
        PreparedStatement statement = SqliteConnection.getInstance().prepareStatement("SELECT * FROM " + tableName() + " WHERE userId = ? AND metricType = ?");
        statement.setInt(1, SqliteConnection.getCurrentUser().getId());
        statement.setInt(2, type.ordinal());
        return statement;
    }

    @Override
    public HealthMetric getItem(int id) {
        try (ResultSet set = getHealthMetricItemStatement(id).executeQuery()) {
            if (set.next()) {
                String name = set.getString("name");
                int userId = set.getInt("userId");
                int metricTypeOrdinal = set.getInt("metricType");
                double measurement = set.getDouble("measurement");
                Date created = set.getDate("created");

                return new HealthMetric(id, name, HealthMetricType.values()[metricTypeOrdinal], userId, measurement, created);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<HealthMetric> getAllItems() {
        List<HealthMetric> items = new ArrayList<>();

        try (ResultSet set = getAllItemsStatement().executeQuery()) {
            while (set.next()) {
                int id = set.getInt("id");
                String name = set.getString("name");
                int userId = set.getInt("userId");
                int metricTypeOrdinal = set.getInt("metricType");
                double measurement = set.getDouble("measurement");
                Date created = set.getDate("created");

                items.add(
                        new HealthMetric(id, name, HealthMetricType.values()[metricTypeOrdinal], userId, measurement, created)
                );

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }

    public List<HealthMetric> getAllItems(HealthMetricType type) {
        List<HealthMetric> items = new ArrayList<>();

        try (ResultSet set = getAllItemsStatement(type).executeQuery()) {
            while (set.next()) {
                int id = set.getInt("id");
                String name = set.getString("name");
                int userId = set.getInt("userId");
                int metricTypeOrdinal = set.getInt("metricType");
                double measurement = set.getDouble("measurement");
                Date created = set.getDate("created");

                items.add(
                        new HealthMetric(id, name, HealthMetricType.values()[metricTypeOrdinal], userId, measurement, created)
                );

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }
}

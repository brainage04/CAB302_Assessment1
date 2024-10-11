package com.example.cab222a.dao.resist_train;

import com.example.cab222a.common.SqliteConnection;
import com.example.cab222a.dao.core.AbstractObjectDAO;
import com.example.cab222a.model.resist_train.HealthMetric;

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
                "userID INTEGER NOT NULL, " +
                "metricType VARCHAR NOT NULL, " +
                "measurement DOUBLE NOT NULL, " +
                "date DATE NOT NULL, " +
                "FOREIGN KEY (userId) REFERENCES users(id)";
    }

    @Override
    protected PreparedStatement addItemStatement(HealthMetric item) throws SQLException {
        String sql = "INSERT INTO healthMetric (userID, metricType, measurement, date) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = SqliteConnection.getInstance().prepareStatement(sql);
        statement.setInt(1, item.getUserID());
        statement.setString(2, item.getMetricType());
        statement.setDouble(3, item.getMeasurement());
        statement.setDate(4, item.getDate());
        return statement;
    }

    @Override
    protected PreparedStatement updateItemStatement(HealthMetric item) throws SQLException {
        String sql = "UPDATE healthMetric SET metricType = ?, measurement = ?, date = ? WHERE id = ?";
        PreparedStatement statement = SqliteConnection.getInstance().prepareStatement(sql);
        statement.setString(1, item.getMetricType());
        statement.setDouble(2, item.getMeasurement());
        statement.setDate(3, item.getDate());
        statement.setInt(4, item.getId());
        return statement;
    }

    @Override
    protected PreparedStatement getAllItemsStatement() throws SQLException {
        String sql = "SELECT * FROM healthMetric WHERE userID = ?";
        PreparedStatement statement = SqliteConnection.getInstance().prepareStatement(sql);
        statement.setInt(1, SqliteConnection.getCurrentUser().getId());
        return statement;
    }

    @Override
    public HealthMetric getItem(int id) {
        try (ResultSet set = getItemStatement(id).executeQuery()) {
            if (set.next()) {
                int userId = set.getInt("userId");
                String metricType = set.getString("metricType");
                double measurement = set.getDouble("measurement");
                Date date = set.getDate("date");

                return new HealthMetric(id, userId, metricType, measurement, date);
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
                int userId = set.getInt("userId");
                String metricType = set.getString("metricType");
                double measurement = set.getDouble("measurement");
                Date date = set.getDate("date");

                items.add(new HealthMetric(id, userId, metricType, measurement, date));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }
}

package com.example.mysql.rcp.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.mysql.cj.jdbc.Driver;

public class MySqlConnectionService {
    public ConnectionTestResult test(ConnectionSettings settings) throws SQLException {
        Properties properties = new Properties();
        properties.setProperty("user", settings.user().strip());
        properties.setProperty("password", settings.password());

        Driver driver = new Driver();
        try (Connection connection = driver.connect(settings.toJdbcUrl(), properties)) {
            if (connection == null) {
                throw new SQLException("MySQL driver did not accept URL: " + settings.displayUrl());
            }
            return new ConnectionTestResult(readVersion(connection), connection.getCatalog(), settings.displayUrl());
        }
    }

    private String readVersion(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT VERSION()")) {
            if (resultSet.next()) {
                return resultSet.getString(1);
            }
            return "Unknown";
        }
    }
}

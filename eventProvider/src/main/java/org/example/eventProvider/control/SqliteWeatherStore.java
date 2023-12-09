package org.example.eventProvider.control;


import org.example.eventProvider.model.Weather;
import java.sql.*;

public class SqliteWeatherStore {
    private Connection connection;
    public SqliteWeatherStore() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:./weather.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void insertWeatherData(Weather weather, String cityName) {
        createTableForCity(cityName);
        String checkIfExistsSQL = "SELECT * FROM " + getTableName(cityName) + " WHERE latitude = ? AND longitude = ?";
        try (PreparedStatement checkIfExistsStatement = connection.prepareStatement(checkIfExistsSQL)) {
            checkIfExistsStatement.setDouble(1, weather.getLocation().getLatitude());
            checkIfExistsStatement.setDouble(2, weather.getLocation().getLongitude());
            ResultSet resultSet = checkIfExistsStatement.executeQuery();
            if (resultSet.next()) {
                if (dataChanged(resultSet, weather)) {
                    insertNewWeatherData(weather, cityName);
                } else {
                    updateWeatherData(weather, cityName);
                }
            } else {
                insertNewWeatherData(weather, cityName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private boolean dataChanged(ResultSet resultSet, Weather weather) throws SQLException {
        return resultSet.getDouble("temperature") != weather.getTemp() ||
                resultSet.getInt("humidity") != weather.getHumidity() ||
                resultSet.getInt("clouds") != weather.getClouds() ||
                resultSet.getDouble("wind_speed") != weather.getWindSpeed();
    }
    private void insertNewWeatherData(Weather weather, String cityName) {
        String insertSQL = "INSERT INTO " + getTableName(cityName) +
                " (temperature, humidity, clouds, wind_speed, latitude, longitude) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setDouble(1, weather.getTemp());
            preparedStatement.setInt(2, weather.getHumidity());
            preparedStatement.setInt(3, weather.getClouds());
            preparedStatement.setDouble(4, weather.getWindSpeed());
            preparedStatement.setDouble(5, weather.getLocation().getLatitude());
            preparedStatement.setDouble(6, weather.getLocation().getLongitude());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void updateWeatherData(Weather weather, String cityName) {
        String updateSQL = "UPDATE " + getTableName(cityName) +
                " SET temperature = ?, humidity = ?, clouds = ?, wind_speed = ? WHERE latitude = ? AND longitude = ?";
        try (PreparedStatement updateStatement = connection.prepareStatement(updateSQL)) {
            updateStatement.setDouble(1, weather.getTemp());
            updateStatement.setInt(2, weather.getHumidity());
            updateStatement.setInt(3, weather.getClouds());
            updateStatement.setDouble(4, weather.getWindSpeed());
            updateStatement.setDouble(5, weather.getLocation().getLatitude());
            updateStatement.setDouble(6, weather.getLocation().getLongitude());
           updateStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void createTableForCity(String cityName) {
        String tableName = getTableName(cityName);
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet tables = metaData.getTables(null, null, tableName, null);
            if (!tables.next()) {
                String createTableSQL = "CREATE TABLE " + tableName + " (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "temperature REAL," +
                        "humidity INTEGER," +
                        "clouds INTEGER," +
                        "wind_speed REAL," +
                        "latitude REAL," +
                        "longitude REAL)";
                connection.createStatement().execute(createTableSQL);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private String getTableName(String cityName) {
        return "weather_data_" + cityName.replaceAll("[^a-zA-Z0-9]", "_");
    }
}

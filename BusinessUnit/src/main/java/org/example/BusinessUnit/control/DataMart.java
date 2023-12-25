package org.example.BusinessUnit.control;
import org.example.BusinessUnit.model.WeatherEvent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataMart {

    private static final String DATABASE_URL = "jdbc:sqlite:/path/to/your/database.db"; // Cambia esto según tu configuración

    public void consumeAndStoreWeatherData(String topic, WeatherEvent weatherData) {
        // Aquí implementa la lógica para consumir eventos del tópico y almacenarlos en el datamart
        // Utiliza el objeto weatherData para acceder a los datos del evento

        // Ejemplo de almacenamiento en SQLite
        try (Connection connection = DriverManager.getConnection(DATABASE_URL)) {
            // Crea la tabla si no existe
            createTableIfNotExists(connection);

            // Inserta los datos meteorológicos en la base de datos
            insertWeatherData(connection, weatherData);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void queryDataLake() {
        // Implementa la lógica para consultar directamente el datalake (por ejemplo, SQLite)
        // y proporciona la información necesaria a la unidad de negocio

        try (Connection connection = DriverManager.getConnection(DATABASE_URL)) {
            // Implementa la lógica de consulta y proporciona la información necesaria

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTableIfNotExists(Connection connection) throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS WeatherData ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "predictionTime VARCHAR(255)," // Agrega más campos según tus necesidades
                + "temperature DOUBLE,"
                + "humidity INTEGER,"
                + "clouds INTEGER,"
                + "windSpeed DOUBLE,"
                + "cityName VARCHAR(255),"
                + "island VARCHAR(255),";

        try (PreparedStatement preparedStatement = connection.prepareStatement(createTableSQL)) {
            preparedStatement.executeUpdate();
        }
    }

    private void insertWeatherData(Connection connection, WeatherEvent weatherData) throws SQLException {
        String insertSQL = "INSERT INTO WeatherData (predictionTime, temperature, humidity, clouds, windSpeed, cityName, island) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setString(1, weatherData.getPredictionTime());
            preparedStatement.setDouble(2, weatherData.getTemp());
            preparedStatement.setInt(3, weatherData.getHumidity());
            preparedStatement.setInt(4, weatherData.getClouds());
            preparedStatement.setDouble(5, weatherData.getWindSpeed());
            preparedStatement.setString(6, weatherData.getCityName());
            preparedStatement.setString(7, weatherData.getIsland());

            preparedStatement.executeUpdate();
        }
    }
}



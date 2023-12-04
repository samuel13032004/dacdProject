package org.example.eventProvider.control;

public class Main {
    public static void main(String[] args) {
       //try {
       //    new org.sqlite.JDBC();
       //} catch (Exception e) {
       //    e.printStackTrace();
       //}
        OpenWeatherMapProvider apiClient = new OpenWeatherMapProvider();
        String apiKey = apiClient.getApiKey();

        // Configurar la WeatherStore y el dbPath
        //WeatherStore weatherStore = new SqliteWeatherStore();

     //// Iniciar las consultas periódicas
     //WeatherControl weatherControl = new WeatherControl(apiKey, apiClient.getUrls(), weatherStore);
     //apiClient.startPeriodicQueries(weatherControl);

        WeatherStore weatherStore = new JMSWeatherStore();
        apiClient.setWeatherStore(weatherStore);

        //String dbPath = "jdbc:sqlite:weather.db";
        apiClient.setWeatherStore(weatherStore);
        //apiClient.setDbPath(dbPath);

        // Iniciar las consultas periódicas
        WeatherControl weatherControl = new WeatherControl(apiKey, apiClient.getUrls(), weatherStore);
        apiClient.startPeriodicQueries(weatherControl);

    }
}



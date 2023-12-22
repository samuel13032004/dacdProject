package org.example.WeatherPredictionProvider.control;

public class Main {
    public static void main(String[] args) {
        OpenWeatherMapProvider apiClient = new OpenWeatherMapProvider();
        String apiKey = apiClient.getApiKey();
        WeatherStore weatherStore = new JMSWeatherStore();
        apiClient.setWeatherStore(weatherStore);
        WeatherControl weatherControl = new WeatherControl(apiKey, weatherStore);
        apiClient.startPeriodicQueries(weatherControl);
    }
}

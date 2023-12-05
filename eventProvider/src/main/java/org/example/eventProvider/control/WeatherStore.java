package org.example.eventProvider.control;

import org.example.eventProvider.model.Weather;

public interface WeatherStore {
    //void insertWeatherData(Weather weather, String cityName);
     void publishWeatherEvent(Weather weather);
     //void publishWeatherEvent(String eventJson);
    //Weather getWeather(Weather weather);
    Weather getWeather(String eventJson);
}

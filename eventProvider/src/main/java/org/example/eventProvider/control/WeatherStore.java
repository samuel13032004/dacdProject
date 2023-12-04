package org.example.eventProvider.control;

import org.example.eventProvider.model.Weather;

public interface WeatherStore {
    //void insertWeatherData(Weather weather, String cityName);
     void publishWeatherEvent(String eventJson);
}

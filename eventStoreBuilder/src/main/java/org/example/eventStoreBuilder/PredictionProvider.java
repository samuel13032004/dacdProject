package org.example.eventStoreBuilder;

import org.example.eventProvider.model.Weather;

public interface PredictionProvider {
    //void publishWeatherPrediction(Weather weather);
    void publishWeatherPrediction(Weather weather);
    Weather convertJsonToWeather(String json);
}

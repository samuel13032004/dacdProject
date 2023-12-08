package org.example.eventStoreBuilder;

import org.example.eventProvider.model.Weather;

import java.util.List;

public interface PredictionProvider {
    //void publishWeatherPrediction(Weather weather);
    void publishWeatherPrediction(List<Weather> weather);
    //Weather convertJsonToWeather(String json);
}

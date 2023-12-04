package org.example.eventStoreBuilder;

import org.example.eventProvider.model.Weather;

public interface PredictionProvider {
    void publishWeatherPrediction(Weather weather);
}

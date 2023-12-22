package org.example.WeatherPredictionProvider.control;

import org.example.WeatherPredictionProvider.model.Weather;
import java.util.List;
public interface WeatherStore {
    void save(List<Weather> weatherList);
}

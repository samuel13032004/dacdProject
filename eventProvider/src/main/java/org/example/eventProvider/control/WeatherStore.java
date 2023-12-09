package org.example.eventProvider.control;

import org.example.eventProvider.model.Weather;
import java.util.List;
public interface WeatherStore {
    void save(List<Weather> weatherList);
}

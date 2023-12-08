package org.example.eventStoreBuilder;

import org.example.eventProvider.control.WeatherStore;
import org.example.eventProvider.model.Weather;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PredictionProviderImplementation implements PredictionProvider {

    private WeatherStore weatherStore;

    public PredictionProviderImplementation(WeatherStore weatherStore) {
        this.weatherStore = weatherStore;
    }

    @Override
    public void publishWeatherPrediction(List<Weather> weather) {
        // En lugar de obtener un único evento, obten todos los eventos almacenados
        // en el JMSWeatherStore y publícalos en el broker uno por uno.
        weatherStore.save(weather);

       //for (Weather storedWeather : weatherStore.getAllWeatherEvents()) {
       //    System.out.println("Publicando evento almacenado: " + storedWeather);
       //    weatherStore.publishWeatherEvent(storedWeather);
       //}
    }

}

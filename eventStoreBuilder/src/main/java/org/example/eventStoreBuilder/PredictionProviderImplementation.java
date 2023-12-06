package org.example.eventStoreBuilder;

import org.example.eventProvider.control.WeatherStore;
import org.example.eventProvider.model.Weather;

public class PredictionProviderImplementation implements PredictionProvider {

    private WeatherStore weatherStore;

    public PredictionProviderImplementation(WeatherStore weatherStore) {
        this.weatherStore = weatherStore;
    }

    @Override
    public void publishWeatherPrediction(Weather weather) {
        // En lugar de obtener un único evento, obten todos los eventos almacenados
        // en el JMSWeatherStore y publícalos en el broker uno por uno.
        for (Weather storedWeather : weatherStore.getAllWeatherEvents()) {
            System.out.println("Publicando evento almacenado: " + storedWeather);
            weatherStore.publishWeatherEvent(storedWeather);
        }
    }

    // Otros métodos de la interfaz PredictionProvider (si los hay)
}

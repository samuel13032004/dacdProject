package org.example.eventStoreBuilder;

import org.example.eventProvider.control.WeatherProvider;
import org.example.eventProvider.control.WeatherStore;
import org.example.eventProvider.model.Weather;
import javax.jms.ConnectionFactory;


public class PredictionProviderImplementation implements PredictionProvider {

    private ConnectionFactory connectionFactory;
    private WeatherStore weatherStore;

    public PredictionProviderImplementation(ConnectionFactory connectionFactory, WeatherStore weatherStore) {
        this.connectionFactory = connectionFactory;
        this.weatherStore = weatherStore;
    }
    @Override
    public void publishWeatherPrediction(Weather weather) {
        WeatherPredictionEvent predictionEvent = new WeatherPredictionEvent(weather);
        String eventJson = predictionEvent.toJson();

        // Publicar en JMSWeatherStore
        weatherStore.publishWeatherEvent(eventJson);

        // Puedes agregar lógica adicional aquí si es necesario
    }
}

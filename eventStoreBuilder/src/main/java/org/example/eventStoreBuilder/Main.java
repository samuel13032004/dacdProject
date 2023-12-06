package org.example.eventStoreBuilder;

import org.example.eventProvider.control.JMSWeatherStore;
import org.example.eventProvider.control.WeatherControl;
import org.example.eventProvider.control.WeatherStore;
import org.example.eventProvider.model.Weather;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Crear instancias de las clases necesarias
        WeatherStore weatherStore = new JMSWeatherStore();
        PredictionProvider predictionProvider = new PredictionProviderImplementation(weatherStore);
        EventStoreBuilder eventStoreBuilder = new EventStoreBuilder();

        // Iniciar suscripción al broker
        eventStoreBuilder.startSubscription();

        // Obtener todos los eventos y escribirlos en el archivo
        List<Weather> allEvents = weatherStore.getAllWeatherEvents();

        if (allEvents.isEmpty()) {
            System.out.println("No hay eventos para procesar.");
        } else {
            for (Weather event : allEvents) {
                // Puedes procesar cada evento según tus necesidades
                // En este ejemplo, simplemente se imprime en la consola
                System.out.println("Evento obtenido del WeatherStore: " + event);

                // Publicar la predicción del clima
                predictionProvider.publishWeatherPrediction(event);
                String eventJson = weatherStore.convertWeatherToJson(event);
                eventStoreBuilder.publishWeatherEvent(eventJson);

                // Esperar un momento para permitir que la suscripción se establezca y se reciban los mensajes
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // Esperar un poco más antes de detener la suscripción
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
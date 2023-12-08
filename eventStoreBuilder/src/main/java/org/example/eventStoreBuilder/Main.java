package org.example.eventStoreBuilder;

import org.example.eventProvider.control.JMSWeatherStore;
import org.example.eventProvider.control.WeatherControl;
import org.example.eventProvider.control.WeatherStore;
import org.example.eventProvider.model.Weather;

import java.util.List;

public class Main{
    public static void main(String[] args) {
    // Crear instancias de las clases necesarias
        // Ejemplo de cómo podría hacerse
        WeatherStore weatherStore = new JMSWeatherStore();

        PredictionProvider predictionProvider = new PredictionProviderImplementation(weatherStore);
        EventStore eventStoreBuilder = new EventStoreBuilder();
        // Iniciar suscripción al broker
        eventStoreBuilder.startSubscription();

  // // Esperar a que la suscripción se establezca (puedes ajustar este tiempo según tus necesidades)
  // try {
  //     Thread.sleep(5000); // Esperar 5 segundos, por ejemplo
  // } catch (InterruptedException e) {
  //     e.printStackTrace();
  // }

  // // Obtener todos los eventos y procesarlos
  // List<Weather> allEvents = weatherStore.getAllWeatherEvents();
  // List<String> allEvents2 = weatherStore.getWeatherJson();
  // System.out.println("Número de eventos: " + allEvents.size());
  // System.out.println("Número de eventos: " + allEvents2.size());
  // if (allEvents.isEmpty()) {
  //     System.out.println("No hay eventos para procesar.");
  //    // for (Weather event1 : allEvents) {
  //    //     weatherStore.publishWeatherEvent(event1);
  //    //     predictionProvider.publishWeatherPrediction(event1);
  //    // }
  // } else {
  //     for (Weather event : allEvents) {
  //         System.out.println("Evento obtenido del WeatherStore: " + event);

  //         // Publicar la predicción del clima
  //         predictionProvider.publishWeatherPrediction(event);

  //         // Convertir a JSON y publicar en el WeatherStore
  //         String eventJson = weatherStore.convertWeatherToJson(event);
  //         eventStoreBuilder.publishWeatherEvent(eventJson);

  //         // Puedes eliminar la espera entre eventos si no es necesaria
  //         try {
  //             Thread.sleep(2000);
  //         } catch (InterruptedException e) {
  //             e.printStackTrace();
  //         }
  //     }
  // }
  //     // Simular la escritura de un evento
  //     String eventJson = "{\"temp\":25.0,\"humidity\":60,\"clouds\":20,\"windSpeed\":5.0,\"latitude\":40.7128,\"longitude\":-74.0060,\"timestamp\":1638962400000,\"source\":\"prediction-provider\",\"predictionTimestamp\":1638966000000}";
  //     eventStoreBuilder.publishWeatherEvent(eventJson);
  // // Esperar un poco más antes de detener la suscripción
  // try {
  //     Thread.sleep(2000);
  // } catch (InterruptedException e) {
  //     e.printStackTrace();
  // }

  // // Detener la suscripción al broker
  // eventStoreBuilder.stopSubscription();
  // }
    }
}

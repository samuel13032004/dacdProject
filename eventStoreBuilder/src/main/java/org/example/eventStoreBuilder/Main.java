package org.example.eventStoreBuilder;

import org.example.eventProvider.control.JMSWeatherStore;
import org.example.eventProvider.control.WeatherStore;
import org.example.eventProvider.model.Weather;

import java.util.Date;
import java.util.List;

public class Main {
        public static void main(String[] args) {

            // Crear una instancia de EventStoreBuilder
   //     EventStore eventStore = new EventStoreBuilder();

   //     // Iniciar la suscripción al broker para recibir eventos
   //     eventStore.startSubscription();

   //     // Simular la publicación de eventos (puedes obtener los datos de tu proveedor aquí)
   //     PredictionProvider weatherProvider = new PredictionProviderImplementation(eventStore) {
   //         @Override
   //         public void publishWeatherPrediction(Weather weather) {

   //         }
   //     }; // Reemplaza esto con tu implementación real
   //     for (int i = 0; i < 5; i++) {
   //         // Obtener datos del proveedor (reemplaza esto con tu lógica real)
   //         //Weather weatherData = weatherProvider.getWeatherData();

   //         // Crear una instancia de PredictionProviderImplementation
   //         PredictionProviderImplementation predictionProvider = new PredictionProviderImplementation(eventStore);

   //         // Publicar la predicción del clima
   //         //predictionProvider.publishWeatherPrediction(weatherData);


   //      // Crear instancias de las clases necesarias
   //      WeatherStore weatherStore = new JMSWeatherStore();
   //      PredictionProvider predictionProvider = new PredictionProviderImplementation(weatherStore);
   //      EventStore eventStore = new EventStoreBuilder();

   //      eventStore.startSubscription();

   //      // Publicar datos al broker
   //      //predictionProvider.publishWeatherPrediction();
   //      Weather weather = obtenerDatosDelClima(); // Debes implementar este método según tu lógica

   //      // Esperar un momento para permitir que la suscripción se establezca y se reciban los mensajes
   //      try {
   //          Thread.sleep(2000);
   //      } catch (InterruptedException e) {
   //          e.printStackTrace();
   //      }

   //      // Escribir eventos en el archivo
   //      writeEventsToFile(eventStore);

   //      // Cerrar la conexión con el broker
   //      //weatherStore.close();
   //  }

   // // private static void publishData(Weather weather) {
   // //     // Crear un objeto Weather con datos de ejemplo
   // //     // Publicar la predicción del tiempo
   // //     predictionProvider.publishWeatherPrediction(weather);
   // // }

   //  private static void writeEventsToFile(EventStore eventStore) {
   //      // Esperar un momento para permitir que la suscripción se establezca y se reciban los mensajes
   //      try {
   //          Thread.sleep(2000);
   //      } catch (InterruptedException e) {
   //          e.printStackTrace();
   //      }

   //      // Escribir eventos en el archivo
   //      for (String eventJson : ((EventStoreBuilder) eventStore).getEventList()) {
   //          eventStore.publishWeatherEvent(eventJson);
   //      }
   //  }
   //ivate static WeatherStore obtenerDatosDelClima() {
   //  // Implementa la lógica para obtener los datos reales del clima
   //  // Puedes llamar a servicios externos, bases de datos, etc.
   //  // Devuelve un objeto Weather con los datos reales del clima
   //  WeatherStore weatherStore = new JMSWeatherStore();
   //  Weather weather = null;
   //  weatherStore.getWeather(new Weather(weather.getTemp(),weather.getHumidity(), weather.getClouds(), weather.getWindSpeed() , weather.getTs() ,weather.getSs() , weather.getPredictionTime());
   //  weather.addLocation(weather.getLocation().getLatitude(),weather.getLocation().getLongitude(), weather.getLocation().getCityName());
   //  return weatherStore;
           JMSWeatherStore jmsWeatherStore = new JMSWeatherStore();

           // Crear instancias de publicador y suscriptor
           PredictionProviderImplementation predictionProvider = new PredictionProviderImplementation(jmsWeatherStore);
           //predictionProvider.convertJsonToWeather(jmsWeatherStore.toString());
           EventStoreBuilder eventStoreBuilder = new EventStoreBuilder();
           // Iniciar suscripción al broker
            eventStoreBuilder.startSubscription();

           // Extraer eventos de JMSWeatherStore y publicarlos en el broker
           List<String> eventList = eventStoreBuilder.getEventList();
           InternalEventStore internalEventStore = new InternalEventStore();

           for (String eventJson : eventList) {
              // Convertir el JSON a un objeto Weather
              Weather weather = predictionProvider.convertJsonToWeather(jmsWeatherStore.toString());;

              // Publicar la predicción del tiempo
              predictionProvider.publishWeatherPrediction(weather);

              // Escribir el evento en un archivo
              internalEventStore.writeEventToFile(eventJson);

           }
        }
    }




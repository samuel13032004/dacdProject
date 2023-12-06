package org.example.eventProvider.control;

import com.google.gson.Gson;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.example.eventProvider.model.Weather;

import javax.jms.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

public class JMSWeatherStore implements WeatherStore {

    private ConnectionFactory connectionFactory;
    private Connection connection;
    private Session session;
    private MessageProducer producer;
    private List<Weather> weatherEvents;

    private static final String BROKER_URL = "tcp://localhost:61616"; // Cambia según tu configuración
    private static final String TOPIC_NAME = "prediction.weather";
    private static final String API_USERNAME = "usuario";
    private static final String API_PASSWORD = "prueba";

    public JMSWeatherStore() {
        try {
            this.connectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
            this.connection = connectionFactory.createConnection();
            this.connection.start();
            this.session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            this.producer = session.createProducer(session.createTopic(TOPIC_NAME));
            this.weatherEvents = new ArrayList<>();

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Weather> getAllWeatherEvents() {
        return new ArrayList<>(weatherEvents);
    }

    @Override
    public void publishWeatherEvent(Weather weather) {
        try {
            String eventJson = convertWeatherToJson(weather);
            TextMessage message = session.createTextMessage(eventJson);
            setAuthToken(message);
            producer.send(message);
            weatherEvents.add(weather); // Agregar el evento a la lista local
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

 //@Override
 //public List<Weather> obtenerEventosDelProveedor() {
 //    // Esta lógica depende de cómo obtienes eventos en tu aplicación
 //    // Aquí, se asume que los eventos se obtienen directamente del JMSWeatherStore
 //    // Puedes ajustar esto según tu implementación específica

 //    List<Weather> filteredEvents = new ArrayList<>(weatherEvents);
 //    // Puedes agregar lógica adicional para filtrar eventos según sea necesario
 //    // Por ejemplo, puedes filtrar eventos por ciudad, fecha, etc.
 //    return filteredEvents;
 //}

    @Override
    public String convertWeatherToJson(Weather weather) {
        // Implementa la lógica de conversión de Weather a JSON aquí
        // Puedes utilizar librerías como Gson para facilitar esta conversión
        // Por ahora, devuelvo un JSON de ejemplo
        return "{\"temp\":" + weather.getTemp() + ",\"humidity\":" + weather.getHumidity() +
                ",\"clouds\":" + weather.getClouds() + ",\"windSpeed\":" + weather.getWindSpeed() +
                ",\"latitude\":" + weather.getLocation().getLatitude() + ",\"longitude\":" +
                weather.getLocation().getLongitude() + ",\"timestamp\":" + weather.getTs().getTime() +
                ",\"source\":\"" + weather.getSs() + "\",\"predictionTimestamp\":" +
                weather.getPredictionTime().getTime() + "}";
    }

    private void setAuthToken(Message message) throws JMSException {
        // Simulando la generación del token
        String credentials = API_USERNAME + ":" + API_PASSWORD;
        String base64Credentials = Base64.getEncoder().encodeToString(credentials.getBytes());
        message.setStringProperty("Authorization", "Basic " + base64Credentials);
    }

    @Override
    public Weather convertJsonToWeather(String json) {
        // Implementa la lógica para convertir JSON a un objeto Weather
        // Puedes usar librerías como Gson para facilitar esta conversión
        // Ejemplo básico:
        Gson gson = new Gson();
        return gson.fromJson(json, Weather.class);
    }

    //@Override
   // public Weather getWeather(Weather weather) {
   //     Weather weather1 = new Weather(weather.getTemp(),weather.getHumidity(), weather.getClouds(), weather.getWindSpeed() , weather.getTs() ,weather.getSs() , weather.getPredictionTime());
   //     weather.addLocation(weather.getLocation().getLatitude(),weather.getLocation().getLongitude(), weather.getLocation().getCityName());
   //     return weather1;
   // }
  // @Override
  // public Weather getWeather(String eventJson) {
  //     // Método para convertir JSON a Weather
  //     return convertJsonToWeather(eventJson);
  // }
  //@Override
  //public Weather getWeather() {
  //    try {
  //        // Crear un consumidor para recibir mensajes
  //        MessageConsumer consumer = session.createConsumer(session.createTopic(TOPIC_NAME));

  //        // Esperar y recibir un mensaje
  //        Message message = consumer.receive();

  //        // Verificar si el mensaje es un ObjectMessage
  //        if (message instanceof ObjectMessage) {
  //            ObjectMessage objectMessage = (ObjectMessage) message;

  //            // Extraer y devolver el objeto Weather
  //            return (Weather) objectMessage.getObject();
  //        }
  //    } catch (JMSException e) {
  //        e.printStackTrace();
  //    }
  //    return null;
  //}


    //Override
      // public Weather getWeather() {
      //     try {
      //         // Crear un consumidor para recibir mensajes
      //         MessageConsumer consumer = session.createConsumer(session.createTopic(TOPIC_NAME));

      //         // Esperar y recibir un mensaje
      //         Message message = consumer.receive();

      //         // Verificar si el mensaje es un TextMessage
      //         if (message instanceof TextMessage) {
      //             TextMessage textMessage = (TextMessage) message;

      //             // Convertir el contenido del mensaje JSON a un objeto Weather
      //             String json = textMessage.getText();
      //             Gson gson = new Gson();
      //             return gson.fromJson(json, Weather.class);
      //         }
      //     } catch (JMSException | JsonSyntaxException e) {
      //         e.printStackTrace();
      //     }
      //     return null;
      // }
      // private Weather convertJsonToWeather(String json) {
      //     Gson gson = new Gson();
      //     return gson.fromJson(json, Weather.class);
      // }

      // @Override
      // public String getWeatherAsString() {
      //     try {
      //         // Crear un consumidor para recibir mensajes
      //         MessageConsumer consumer = session.createConsumer(session.createTopic(TOPIC_NAME));
//
      //         // Esperar y recibir un mensaje
      //         Message message = consumer.receive();
//
      //         // Verificar si el mensaje es un TextMessage
      //         if (message instanceof TextMessage) {
      //             TextMessage textMessage = (TextMessage) message;
//
      //             // Devolver el contenido del mensaje como una cadena
      //             return textMessage.getText();
      //         }
      //     } catch (JMSException e) {
      //         e.printStackTrace();
      //     }
      //     return null;
      // }
      //@Override
      //public void publishWeatherEvent(String eventJson) {
      //    try {
      //        TextMessage message = session.createTextMessage(eventJson);
      //        producer.send(message);
      //    } catch (JMSException e) {
      //        e.printStackTrace();
      //    }
      // }


      // public void close() {

      //     try {

      //         if (producer != null) {

      //             producer.close();

      //         }

      //         if (session != null) {

      //             session.close();

      //         }

      //         if (connection != null) {

      //             connection.close();

      //         }

      //     } catch (JMSException e) {

      //         e.printStackTrace();

      //     }

      // }

}

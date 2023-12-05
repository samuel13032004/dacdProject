package org.example.eventProvider.control;

import com.google.gson.Gson;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.example.eventProvider.model.Weather;
import javax.jms.*;
import java.util.Base64;

public class JMSWeatherStore implements WeatherStore {
    private ConnectionFactory connectionFactory;
    private Connection connection;
    private Session session;
    private MessageProducer producer;
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
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void publishWeatherEvent(Weather weather) {
        try {
            String eventJson = convertWeatherToJson(weather);
            TextMessage message = session.createTextMessage(eventJson);
            System.out.println(eventJson);
            // Establecer el token en la solicitud
            setAuthToken(message);

            producer.send(message);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
    private void setAuthToken(Message message) throws JMSException {
        // Simulando la generación del token
        String credentials = API_USERNAME + ":" + API_PASSWORD;
        String base64Credentials = Base64.getEncoder().encodeToString(credentials.getBytes());
        message.setStringProperty("Authorization", "Basic " + base64Credentials);
    }
    private String convertWeatherToJson(Weather weather) {
        // Implementa la lógica de conversión de Weather a JSON aquí
        // Puedes utilizar librerías como Gson para facilitar esta conversión
        // Por ahora, devuelvo un JSON de ejemplo
        return "{\"temp\":" + weather.getTemp() + ",\"humidity\":" + weather.getHumidity() + ",\"clouds\":" + weather.getClouds() +
                ",\"windSpeed\":" + weather.getWindSpeed() + ",\"latitude\":" + weather.getLocation().getLatitude() +
                ",\"longitude\":" + weather.getLocation().getLongitude() + ",\"timestamp\":" + weather.getTs().getTime() +
                ",\"source\":\"" + weather.getSs() + "\",\"predictionTimestamp\":" + weather.getPredictionTime().getTime() + "}";
    }
    //@Override
   // public Weather getWeather(Weather weather) {
   //     Weather weather1 = new Weather(weather.getTemp(),weather.getHumidity(), weather.getClouds(), weather.getWindSpeed() , weather.getTs() ,weather.getSs() , weather.getPredictionTime());
   //     weather.addLocation(weather.getLocation().getLatitude(),weather.getLocation().getLongitude(), weather.getLocation().getCityName());
   //     return weather1;
   // }
    @Override
    public Weather getWeather(String eventJson) {
        // Método para convertir JSON a Weather
        return convertJsonToWeather(eventJson);
    }
    private Weather convertJsonToWeather(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Weather.class);
    }
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

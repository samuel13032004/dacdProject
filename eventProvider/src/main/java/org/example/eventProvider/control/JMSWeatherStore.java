package org.example.eventProvider.control;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.example.eventProvider.model.Weather;
import javax.jms.*;

public class JMSWeatherStore implements WeatherStore {
    private ConnectionFactory connectionFactory;
    private Connection connection;
    private Session session;
    private MessageProducer producer;

    private static final String BROKER_URL = "tcp://localhost:61616"; // Cambia según tu configuración
    private static final String TOPIC_NAME = "prediction.weather";

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
            producer.send(message);
        } catch (JMSException e) {
            e.printStackTrace();
        }
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

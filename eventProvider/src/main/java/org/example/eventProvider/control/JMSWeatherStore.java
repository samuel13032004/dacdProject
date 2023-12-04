package org.example.eventProvider.control;

import org.apache.activemq.ActiveMQConnectionFactory;

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
    public void publishWeatherEvent(String eventJson) {
        try {
            TextMessage message = session.createTextMessage(eventJson);
            producer.send(message);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }


    public void close() {
        try {
            if (producer != null) {
                producer.close();
            }
            if (session != null) {
                session.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}

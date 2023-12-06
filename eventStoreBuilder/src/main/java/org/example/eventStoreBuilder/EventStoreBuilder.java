package org.example.eventStoreBuilder;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.example.eventProvider.control.JMSWeatherStore;
import org.example.eventProvider.control.WeatherStore;
import org.example.eventProvider.model.Weather;

import javax.jms.*;
import java.util.Objects;

public class EventStoreBuilder implements EventStore {

    private static final String EVENT_TOPIC = "prediction.weather.topic";
    private MessageConsumer messageConsumer;
    private Connection connection;
    private InternalEventStore internalEventStore;

    public EventStoreBuilder() {
        this.internalEventStore = new InternalEventStore();
    }

    @Override
    public void publishWeatherEvent(String eventJson) {
        // No es necesario mantener una lista de eventos aquí si ya se manejan en PredictionProviderImplementation
        System.out.println("Evento recibido del broker: " + eventJson);
        // Puedes llamar directamente al método que maneja los eventos en PredictionProviderImplementation
        // por ejemplo, predictionProvider.publishWeatherPrediction(eventJson);
        internalEventStore.writeEventToFile(eventJson);
    }

    @Override
    public void startSubscription() {
        System.out.println("Iniciando suscripción al broker...");
        try {
            // Crear una conexión JMS
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
            connection = connectionFactory.createConnection();
            connection.start();
            // Crear una sesión JMS
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            // Obtener el destino (topic) desde el contexto JNDI o directamente
            Topic topic = session.createTopic(EVENT_TOPIC);
            // Crear un consumidor para el topic
            messageConsumer = session.createConsumer(topic);
            // Establecer un MessageListener para procesar los mensajes entrantes
            messageConsumer.setMessageListener(message -> handleMessage((TextMessage) message));
            System.out.println("Suscripción al broker iniciada.");
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stopSubscription() {
        try {
            if (Objects.nonNull(connection)) {
                connection.close();
            }
            System.out.println("Suscripción al broker detenida.");
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    private void handleMessage(TextMessage message) {
        try {
            String eventJson = message.getText();
            // Llamar al método que maneja los eventos en PredictionProviderImplementation
            // por ejemplo, predictionProvider.publishWeatherPrediction(eventJson);
            WeatherStore weatherStore = new JMSWeatherStore();
            Weather weather = weatherStore.convertJsonToWeather(eventJson);
            internalEventStore.writeEventToFile(String.valueOf(weather));
            //internalEventStore.writeEventToFile(eventJson);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}

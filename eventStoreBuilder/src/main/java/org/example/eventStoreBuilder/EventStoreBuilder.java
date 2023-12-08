package org.example.eventStoreBuilder;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.example.eventProvider.control.JMSWeatherStore;
import org.example.eventProvider.control.WeatherStore;
import org.example.eventProvider.model.Weather;

import javax.jms.*;
import java.util.Objects;

public class EventStoreBuilder implements EventStore {

    private static final String EVENT_TOPIC = "prediction.weather";
    private MessageConsumer messageConsumer;
    private Connection connection;
    private InternalEventStore internalEventStore;
    private WeatherStore weatherStore;

    public EventStoreBuilder() {
        this.internalEventStore = new InternalEventStore();
        this.weatherStore = new JMSWeatherStore(); // Utilizar JMSWeatherStore como implementación de WeatherStore
    }

    @Override
    public void publishWeatherEvent(String eventJson) {
        System.out.println("Evento recibido del broker: " + eventJson);
        internalEventStore.writeEventToFile(eventJson);
    }

    @Override
    public void startSubscription() {
        System.out.println("Iniciando suscripción al broker...");
        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
            connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Topic topic = session.createTopic(EVENT_TOPIC);
            messageConsumer = session.createConsumer(topic);
            messageConsumer.setMessageListener(message -> handleMessage((TextMessage) message));
            System.out.println("Suscripción al broker iniciada.");
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    // Método para detener la suscripción
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
            System.out.println("Mensaje recibido del broker: " + eventJson);

            // Utilizar JMSWeatherStore para guardar el evento
            //Weather weather = weatherStore.prepare(eventJson);
            internalEventStore.writeEventToFile(eventJson);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}

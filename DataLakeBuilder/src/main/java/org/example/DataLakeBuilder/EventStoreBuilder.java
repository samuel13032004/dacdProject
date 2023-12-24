package org.example.DataLakeBuilder;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import static org.apache.activemq.ActiveMQConnection.DEFAULT_BROKER_URL;

public class EventStoreBuilder implements EventStore {
    private static final String WEATHER_TOPIC = "prediction.Weather";
    private static final String HOTEL_TOPIC = "prediction.Hotel";
    private MessageConsumer weatherMessageConsumer;
    private MessageConsumer hotelMessageConsumer;
    private Connection connection;
    private InternalEventStore internalEventStore;

    public EventStoreBuilder() {
        this.internalEventStore = new InternalEventStore();
    }

    @Override
    public void startSubscription() {
        System.out.println("Starting broker subscription...");

        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(DEFAULT_BROKER_URL);
            connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Subscribe to the Weather topic
            Topic weatherTopic = session.createTopic(WEATHER_TOPIC);
            weatherMessageConsumer = session.createConsumer(weatherTopic);
            weatherMessageConsumer.setMessageListener(message -> handleMessage((TextMessage) message, "Weather"));

            // Subscribe to the Hotel topic
            Topic hotelTopic = session.createTopic(HOTEL_TOPIC);
            hotelMessageConsumer = session.createConsumer(hotelTopic);
            hotelMessageConsumer.setMessageListener(message -> handleHotelMessage((TextMessage) message, "Hotel"));

            System.out.println("Broker subscription initiated.");
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    private void handleMessage(TextMessage message, String eventType) {
        processMessage(message, eventType);
    }

    private void handleHotelMessage(TextMessage message, String eventType) {
        processMessage(message, eventType);
    }

    private void processMessage(TextMessage message, String eventType) {
        try {
            String eventJson = message.getText();
            System.out.println("Message received from " + eventType + " topic: " + eventJson);
            internalEventStore.writeEventToFile(eventJson, eventType, "prediction-provider");
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}

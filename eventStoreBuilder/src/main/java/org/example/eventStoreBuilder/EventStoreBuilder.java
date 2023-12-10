package org.example.eventStoreBuilder;

import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.*;
import static org.apache.activemq.ActiveMQConnection.DEFAULT_BROKER_URL;

public class EventStoreBuilder implements EventStore {
    private static final String EVENT_TOPIC = "prediction.Weather";
    private MessageConsumer messageConsumer;
    private Connection connection;
    private InternalEventStore internalEventStore;

    public EventStoreBuilder() {
        this.internalEventStore = new InternalEventStore();
    }
    @Override
    public void startSubscription() {
        System.out.println("Iniciando suscripción al broker...");
        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(DEFAULT_BROKER_URL);
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
    private void handleMessage(TextMessage message) {
        try {
            String eventJson = message.getText();
            System.out.println("Mensaje recibido del broker: " + eventJson);
            internalEventStore.writeEventToFile(eventJson);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}

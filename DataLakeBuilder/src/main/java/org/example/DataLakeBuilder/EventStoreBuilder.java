package org.example.DataLakeBuilder;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EventStoreBuilder implements EventStore{
    Date currentDate = new Date();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    String formattedDate = dateFormat.format(currentDate);
    @Override
    public void startSubscription(String args) {
        try {
            System.out.println("Starting broker subscription...");
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
            Connection connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Topic weatherTopic = session.createTopic("prediction.Weather");
            MessageConsumer weatherConsumer = session.createConsumer(weatherTopic);
            weatherConsumer.setMessageListener(message -> {
                if (message instanceof TextMessage) {
                    try {
                        String text = ((TextMessage) message).getText();
                        saveToEventStore(args+"/eventstore/prediction.Weather/prediction-provider/" + formattedDate + ".events", text);
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            });
            Topic hotelTopic = session.createTopic("prediction.Hotel");
            MessageConsumer hotelConsumer = session.createConsumer(hotelTopic);
            hotelConsumer.setMessageListener(message -> {
                if (message instanceof TextMessage) {
                    try {
                        String text = ((TextMessage) message).getText();
                        saveToEventStore(args+"/eventstore/prediction.Hotel/prediction-provider/" + formattedDate + ".events", text);
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            });
            System.out.println("Waiting for events...");
            System.out.println("Broker subscription initiated.");
            Thread.sleep(Long.MAX_VALUE);
        } catch (JMSException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    private static void saveToEventStore(String filePath, String event) {
        try {
            Files.createDirectories(Paths.get(filePath).getParent());
            try (FileWriter writer = new FileWriter(filePath, true)) {
                writer.write(event.replaceAll("\\s+", "") + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package org.example.eventStoreBuilder;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventStoreBuilder implements EventStore {
    private static final String EVENT_STORE_DIRECTORY = "eventstore/prediction.weather/";
    private static final String EVENT_TOPIC = "prediction.weather.topic";
    private InternalEventStore internalEventStore;
    private List<String> eventList;
    private MessageConsumer messageConsumer;
    public EventStoreBuilder() {
        this.eventList = new ArrayList<>();
        this.internalEventStore = new InternalEventStore();
    }
    @Override
    public void publishWeatherEvent(String eventJson) {
        internalEventStore.writeEventToFile(eventJson);
        eventList.add(eventJson);
        System.out.println(eventList);
    }
    @Override
    public void startSubscription() {
        System.out.println("Iniciando suscripción al broker...");
        try {
            // Crear una conexión JMS
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
            Connection connection = connectionFactory.createConnection();
            connection.start();
            // Crear una sesión JMS
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            // Obtener el destino (topic) desde el contexto JNDI o directamente
            Topic topic = session.createTopic(EVENT_TOPIC);
            // Crear un consumidor para el topic
            messageConsumer = session.createConsumer(topic);
            // Establecer un MessageListener para procesar los mensajes entrantes
            messageConsumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    if (message instanceof TextMessage) {
                        try {
                            String eventJson = ((TextMessage) message).getText();
                            // Procesar el mensaje, por ejemplo, escribir en un archivo o realizar alguna acción
                            System.out.println("Mensaje recibido del broker: " + eventJson);
                        } catch (JMSException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            System.out.println("Suscripción al broker iniciada.");
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
    public List<String> getEventList() {
        return eventList;
    }
//  public class InternalEventStore {
 //      private static final String EVENT_STORE_DIRECTORY = "eventstore/prediction.Weather/";

 //      public void writeEventToFile(String eventJson) {
 //          String currentDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
 //          String directoryPath = EVENT_STORE_DIRECTORY + currentDate;
 //          String filePath = directoryPath + ".events";

 //          File directory = new File(directoryPath);
 //          if (!directory.exists()) {
 //              directory.mkdirs();
 //          }

 //          try (FileWriter writer = new FileWriter(filePath, true)) {
 //              writer.write(eventJson + "\n");
 //              System.out.println("Evento escrito en el archivo: " + filePath);
 //              System.out.println("Consulta cuando extraes datos de JMSWeatherStore: " + eventJson);
 //          } catch (IOException e) {
 //              e.printStackTrace();
 //          }
 //      }
 //  }
}

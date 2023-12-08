package org.example.eventProvider.control;


import javax.jms.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.example.eventProvider.model.Weather;

import java.io.IOException;
import java.time.Instant;
import java.util.List;


public class JMSWeatherStore implements WeatherStore{
    //URL of the JMS server. DEFAULT_BROKER_URL will just mean that JMS server is on localhost
    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;

    // default broker URL is : tcp://localhost:61616"
    private static String subject = "prediction.weather"; // Queue Name.You can create any/many queue names as per your requirement.

    public void save(List<Weather> weatherList) {
        // Getting JMS connection from the server and starting it
        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
            Connection connection = connectionFactory.createConnection();
            connection.start();

            //Creating a non transactional session to send/receive JMS message.
            Session session = connection.createSession(false,
                    Session.AUTO_ACKNOWLEDGE);

            //Destination represents here our queue 'JCG_QUEUE' on the JMS server.
            //The queue will be created automatically on the server.
            Topic topic = session.createTopic(subject);

            // MessageProducer is used for sending messages to the queue.
            MessageProducer producer = session.createProducer(topic);

            // We will send a small text message saying 'Hello World!!!'
            for (Weather weather : weatherList) {
                String eventJson = prepareGson().toJson(weather);
                TextMessage message = session.createTextMessage(eventJson);
                producer.send(message);

            }

            // Here we are sending our message!
            connection.close();
        }catch (JMSException e){
            throw new RuntimeException(e);
        }
    }

    private static Gson prepareGson() {
        return new GsonBuilder().setPrettyPrinting().registerTypeAdapter(Instant.class, new TypeAdapter<Instant>() {
            @Override
            public void write(JsonWriter out, Instant value) throws IOException {
                out.value(value.toString());
            }

            @Override
            public Instant read(JsonReader in) throws IOException {
                return Instant.parse(in.nextString());
            }
        }).create();
    }


}
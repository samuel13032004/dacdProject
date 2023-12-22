package org.example.HotelPredictionProvider.control;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.List;
import javax.jms.*;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.example.HotelPredictionProvider.model.Details;


public class JMSHotelStore {
    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;
    private static String subject = "prediction.Hotel";

    public void save(List<Details> hotelDetailsList) {
        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
            Connection connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false,
                    Session.AUTO_ACKNOWLEDGE);
            Topic topic = session.createTopic(subject);
            MessageProducer producer = session.createProducer(topic);
            for (Details hotelDetails : hotelDetailsList) {
                String eventJson = prepareGson().toJson(hotelDetails);
                TextMessage message = session.createTextMessage(eventJson);
                producer.send(message);
            }
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

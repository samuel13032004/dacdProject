package org.example.WeatherPredictionProvider.control;

import javax.jms.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.example.WeatherPredictionProvider.model.Weather;
import java.io.IOException;
import java.time.Instant;
import java.util.List;

public class JMSWeatherStore implements WeatherStore{
    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;
    private static String subject = "prediction.Weather";

    public void save(List<Weather> weatherList) {
        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
            Connection connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false,
                    Session.AUTO_ACKNOWLEDGE);
            Topic topic = session.createTopic(subject);
            MessageProducer producer = session.createProducer(topic);
            for (Weather weather : weatherList) {
                String eventJson = prepareGson().toJson(weather);
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

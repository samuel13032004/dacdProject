package org.example.BusinessUnit.control;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class EventSubscriber {
    private static final String WEATHER_TOPIC = "prediction.Weather";
    private static final String HOTEL_TOPIC = "prediction.Hotel";
    private Connection connection;
    private Gson gson;

    public EventSubscriber() {
        this.gson = new Gson();
    }

    public void startSubscription() {
        System.out.println("Starting broker subscription...");

        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
            connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Subscribe to the Weather topic
            Topic weatherTopic = session.createTopic(WEATHER_TOPIC);
            MessageConsumer weatherMessageConsumer = session.createConsumer(weatherTopic);
            weatherMessageConsumer.setMessageListener(message -> processMessage(message, "Weather"));

            // Subscribe to the Hotel topic
            Topic hotelTopic = session.createTopic(HOTEL_TOPIC);
            MessageConsumer hotelMessageConsumer = session.createConsumer(hotelTopic);
            hotelMessageConsumer.setMessageListener(message -> processMessage(message, "Hotel"));

            System.out.println("Broker subscription initiated.");
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
    private void processMessage(Message message, String eventType) {
        try {
            if (message instanceof TextMessage) {
                String eventJson = ((TextMessage) message).getText();
                System.out.println("Message received from " + eventType + " topic: " + eventJson);

                try {
                    // Deserialize to JSON object
                    JsonObject jsonObject = gson.fromJson(eventJson, JsonObject.class);
                    System.out.println("Extract data based on the event type");

                    // Extract data based on the event type
                    if ("Weather".equals(eventType)) {
                        System.out.println("Extract data based on the event Weather");
                        extractWeatherData(jsonObject);
                    } else if ("Hotel".equals(eventType)) {
                        System.out.println("Extract data based on the event Hotel");
                        extractHotelData(jsonObject);
                    } else {
                        System.out.println("Unknown event type: " + eventType);
                    }
                } catch (Exception e) {
                    System.err.println("Error processing JSON: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        } catch (JMSException e) {
            System.err.println("Error processing message: " + e.getMessage());
            e.printStackTrace();
        }
    }
   private void extractWeatherData(JsonObject jsonObject) {
       try {
           // Extract weather data
           double temperature = jsonObject.get("temp").getAsDouble();
           double humidity = jsonObject.get("humidity").getAsDouble();

           JsonObject locationObject = jsonObject.getAsJsonObject("location");
           double latitude = locationObject.get("latitude").getAsDouble();
           double longitude = locationObject.get("longitude").getAsDouble();
           String cityName = locationObject.get("cityName").getAsString();
           String island = locationObject.get("island").getAsString();

           System.out.println("------------------------------------------------------------------");
           // Do something with the weather data
           System.out.println("Temperature: " + temperature);
           System.out.println("Humidity: " + humidity);
           System.out.println("Latitude: " + latitude);
           System.out.println("Longitude: " + longitude);
           System.out.println("City Name: " + cityName);
           System.out.println("Island: " + island);
       } catch (Exception e) {
           e.printStackTrace();
       }
   }

    private void extractHotelData(JsonObject jsonObject) {
        try {
            // Extract hotel data
            String hotelName = jsonObject.getAsJsonObject("hotelLocation").get("hotelName").getAsString();
            JsonArray averagePriceDays = jsonObject.getAsJsonArray("averagePriceDay");

            System.out.println("------------------------------------------------------------------");
            // Do something with the hotel data
            System.out.println("Hotel Name: " + hotelName);
            System.out.println("Average Price Days:");
            for (JsonElement element : averagePriceDays) {
                System.out.println("  " + element.getAsString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

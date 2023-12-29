package org.example.BusinessUnit.control;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.example.BusinessUnit.model.HotelEvent;
import org.example.BusinessUnit.model.WeatherEvent;

import javax.jms.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class EventSubscriber {
    private static final String WEATHER_TOPIC = "prediction.Weather";
    private static final String HOTEL_TOPIC = "prediction.Hotel";
    private static final int MAX_WEATHER_EVENTS = 280;
    private static final int MAX_HOTEL_EVENTS = 15;
    private Connection connection;
    private Gson gson;
    private ArrayList<HotelEvent> hotelEvents;
    private ArrayList<WeatherEvent>weatherEvents;
    public EventSubscriber() {
        this.gson = new Gson();
        this.hotelEvents = new ArrayList<>();
        this.weatherEvents = new ArrayList<>();
    }

    public void startSubscription(CountDownLatch latch) {
        System.out.println("Starting broker subscription...");
        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
            connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Topic weatherTopic = session.createTopic(WEATHER_TOPIC);
            MessageConsumer weatherMessageConsumer = session.createConsumer(weatherTopic);
            weatherMessageConsumer.setMessageListener(message -> processMessage(message, "Weather"));
            latch.countDown();
            Topic hotelTopic = session.createTopic(HOTEL_TOPIC);
            MessageConsumer hotelMessageConsumer = session.createConsumer(hotelTopic);
            hotelMessageConsumer.setMessageListener(message -> processMessage(message, "Hotel"));
            latch.countDown();
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
                  JsonObject jsonObject = gson.fromJson(eventJson, JsonObject.class);
                  System.out.println("Extract data based on the event type");
                  if ("Weather".equals(eventType)) {
                      System.out.println("Extract data based on the event Weather");
                      WeatherEvent weatherEvent = extractWeatherData(jsonObject);
                      updateWeatherEventsList(weatherEvent);
                  } else if ("Hotel".equals(eventType)) {
                      System.out.println("Extract data based on the event Hotel");
                      HotelEvent hotelEvent = extractHotelData(jsonObject);
                      updateHotelEventsList(hotelEvent);

                  } else {
                      System.out.println("Unknown event type: " + eventType);
                  }
                  System.out.println(weatherEvents.size());
                  System.out.println(hotelEvents.size());
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
   private WeatherEvent extractWeatherData(JsonObject jsonObject) {
       try {
           JsonObject location = jsonObject.getAsJsonObject("location");
           String island = location.get("island").getAsString();
           String cityName = location.get("cityName").getAsString();

           double temperature = jsonObject.get("temp").getAsDouble();
           int humidity = jsonObject.get("humidity").getAsInt();
           int clouds = jsonObject.get("clouds").getAsInt();
           double windSpeed = jsonObject.get("windSpeed").getAsDouble();
           String predictionTime = jsonObject.get("predictionTime").getAsString();

           WeatherEvent weatherEvent = new WeatherEvent(
                   island,
                   cityName,
                   predictionTime,
                   temperature,
                   humidity,
                   clouds,
                   windSpeed,
                   new Date()
           );
           return weatherEvent;
       } catch (Exception e) {
           e.printStackTrace();
           return null;
       }
   }
    private HotelEvent extractHotelData(JsonObject jsonObject) {
        try {
            JsonObject hotelLocation = jsonObject.getAsJsonObject("hotelLocation");
            String island = hotelLocation.get("island").getAsString();
            String city = hotelLocation.get("city").getAsString();
            String hotelName = hotelLocation.get("hotelName").getAsString();
            String checkout = hotelLocation.get("checkout").getAsString();

            JsonArray averagePriceDays = jsonObject.getAsJsonArray("averagePriceDay");
            JsonArray cheapPriceDays = jsonObject.getAsJsonArray("cheapPriceDay");
            JsonArray highPriceDays = jsonObject.getAsJsonArray("highPriceDay");

            return new HotelEvent(
                    island,
                    city,
                    hotelName,
                    checkout,
                    parseStringList(averagePriceDays),
                    parseStringList(cheapPriceDays),
                    parseStringList(highPriceDays)
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    private static List<String> parseStringList(JsonArray jsonArray) {
        List<String> stringList = new ArrayList<>();
        for (JsonElement element : jsonArray) {
            stringList.add(element.getAsString());
        }
        return stringList;
    }
    private void updateWeatherEventsList(WeatherEvent weatherEvent) {
        if (weatherEvents.size() >= MAX_WEATHER_EVENTS) {
            weatherEvents.remove(0);
        }
        weatherEvents.add(weatherEvent);
        TSVWriter.writeWeatherEventToTSV(weatherEvent, "DataMart/WeatherEventStore/weather_events.tsv");
    }
    private void updateHotelEventsList(HotelEvent hotelEvent) {
        if (hotelEvents.size() >= MAX_HOTEL_EVENTS) {
            hotelEvents.remove(0);
        }
        hotelEvents.add(hotelEvent);
        TSVWriter.writeHotelEventToTSV(hotelEvent, "DataMart/HotelEventStore/hotel_events.tsv");
    }
    public ArrayList<HotelEvent> getHotelEvents() {
        return hotelEvents;
    }
    public ArrayList<WeatherEvent> getWeatherEvents() {
        return weatherEvents;
    }
}

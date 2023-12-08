package org.example.eventProvider.control;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.example.eventProvider.model.Weather;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimerTask;

public class WeatherControl extends TimerTask {
    private final String apiKey;
    private final String[] urls;
    private final WeatherStore weatherStore;

    public WeatherControl(String apiKey, String[] urls, WeatherStore weatherStore) {
        this.apiKey = apiKey;
        this.urls = urls;
        this.weatherStore = weatherStore;
    }

    @Override
    public void run() {
        System.out.println("Nueva Consulta");
        System.out.println("-------------------------------------------------");
        ArrayList<Weather> weatherList = new ArrayList<>();

        for (String url : urls) {
            try {
                URL apiUrl = new URL(url);  // Append the API key to the URL
                //URL apiUrl = new URL(url+apiKey);  // Append the API key to the URL
                HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
                connection.setRequestMethod("GET");
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                connection.disconnect();

                // Parse the JSON array "list"
                JsonObject jsonResponse = JsonParser.parseString(response.toString()).getAsJsonObject();
                JsonArray jsonListArray = jsonResponse.getAsJsonArray("list");

                for (JsonElement jsonElement : jsonListArray) {
                    JsonObject jsonObject = jsonElement.getAsJsonObject();

                    double temp = jsonObject.getAsJsonObject("main").get("temp").getAsDouble();
                    int humidity = jsonObject.getAsJsonObject("main").get("humidity").getAsInt();
                    int clouds = jsonObject.getAsJsonObject("clouds").get("all").getAsInt();
                    double windSpeed = jsonObject.getAsJsonObject("wind").get("speed").getAsDouble();
                   //JsonObject cityObject = jsonObject.getAsJsonObject("city");
                   //double latitude = cityObject.getAsJsonObject("coord").get("lat").getAsDouble();
                   //double longitude = cityObject.getAsJsonObject("coord").get("lon").getAsDouble();
                    double latitude = jsonObject.getAsJsonObject("coord").get("lat").getAsDouble();
                    double longitude = jsonObject.getAsJsonObject("coord").get("lon").getAsDouble();
                    String cityName = jsonObject.get("name").getAsString();

                    double tempNew = (Math.round((temp - 273) * 1000.0) / 1000.0);

                    // Obtain the date and time of the prediction
                    String predictionTimestamp = jsonObject.get("dt_txt").getAsString();

                    // Create Weather object
                    Date timestamp = new Date();
                    String source = "prediction-provider";
                    Weather weather = new Weather(tempNew, humidity, clouds, windSpeed, timestamp, source, predictionTimestamp);
                    weather.addLocation(latitude, longitude, cityName);
                    weatherList.add(weather);

                    // Print information
                    System.out.println("Nombre de la ciudad: " + cityName);
                    System.out.println("Temperatura: " + tempNew + " ºC");
                    System.out.println("Humedad: " + humidity + "%");
                    System.out.println("Nubes: " + clouds + "%");
                    System.out.println("Velocidad del viento: " + windSpeed + " m/s");
                    System.out.println("Latitud: " + latitude);
                    System.out.println("Longitud: " + longitude);
                    System.out.println("Fecha de la predicción: " + predictionTimestamp);
                    System.out.println("-------------------------------------------------");

                }

                // Save the weather list after processing all elements
                weatherStore.save(weatherList);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

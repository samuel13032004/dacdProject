package org.example.eventProvider.control;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.example.eventProvider.model.Location;
import org.example.eventProvider.model.Weather;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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
    private final Location[] locations = {new Location(28.1204, -15.5268, "Arucas"),
            new Location(28.9625, -13.5500, "Arrecife"),
            new Location(28.7355, -13.8646, "Corralejo"),
            new Location(28.0950, -17.1135, "San Sebastián de La Gomera"),
            new Location(28.6573, -17.9183, "Llanos de Aridane"),
            new Location(27.8078, -17.9187, "Valverde"),
            new Location(28.1044, -17.3436, "Santa Cruz de Tenerife"),
            new Location(28.0921, -15.5415, "Firgas")
    };
    @Override
    public void run() {
        System.out.println("Nueva Consulta");
        ArrayList<Weather> weatherList = new ArrayList<>();
        for (Location location : locations) {
            try {
                String apiUrl = "https://api.openweathermap.org/data/2.5/forecast?lat=" +
                        location.getLatitude() +
                        "&lon=" +
                        location.getLongitude() +
                        "&appid=" +
                        apiKey;

                URL ApiUrl = new URL(apiUrl);
                HttpURLConnection connection = (HttpURLConnection) ApiUrl.openConnection();
                connection.setRequestMethod("GET");
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                connection.disconnect();

                JsonObject jsonResponse = JsonParser.parseString(response.toString()).getAsJsonObject();
                JsonArray jsonListArray = jsonResponse.getAsJsonArray("list");

                for (JsonElement jsonElement : jsonListArray) {
                    JsonObject jsonObject = jsonElement.getAsJsonObject();

                    double temp = jsonObject.getAsJsonObject("main").get("temp").getAsDouble();
                    int humidity = jsonObject.getAsJsonObject("main").get("humidity").getAsInt();
                    int clouds = jsonObject.getAsJsonObject("clouds").get("all").getAsInt();
                    double windSpeed = jsonObject.getAsJsonObject("wind").get("speed").getAsDouble();
                    double tempNew = (Math.round((temp - 273) * 1000.0) / 1000.0);
                    String predictionTimestamp = jsonObject.get("dt_txt").getAsString();

                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    String cityName = location.getCityName();
                    Date timestamp = new Date();
                    String source = "prediction-provider";
                    Weather weather = new Weather(tempNew, humidity, clouds, windSpeed, timestamp, source, predictionTimestamp);
                    weather.addLocation(latitude,longitude,cityName);
                    weatherList.add(weather);
                }
                weatherStore.save(weatherList);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

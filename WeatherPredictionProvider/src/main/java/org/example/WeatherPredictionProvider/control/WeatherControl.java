package org.example.WeatherPredictionProvider.control;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.example.WeatherPredictionProvider.model.Location;
import org.example.WeatherPredictionProvider.model.Weather;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimerTask;

import static java.lang.Math.round;

public class WeatherControl extends TimerTask {
    private final String apiKey;
    private final WeatherStore weatherStore;

    public WeatherControl(String apiKey, WeatherStore weatherStore) {
        this.apiKey = apiKey;
        this.weatherStore = weatherStore;
    }
    private final Location[] locations = {
            new Location(27.7518, -15.5865, "Playa del Inglés", "Gran Canaria"),
            new Location(28.9625, -13.5500, "Arrecife", "Lanzarote"),
            new Location(28.7355, -13.8646, "Corralejo", "Fuerteventura"),
            new Location(28.0950, -17.1135, "San Sebastián de La Gomera","La Gomera"),
            new Location(28.6573, -17.9183, "Llanos de Aridane","La Palma"),
            new Location(27.8078, -17.9187, "Valverde","El Hierro"),
            new Location(28.1044, -17.3436, "Santa Cruz de Tenerife","Tenerife"),
    };
    @Override
    public void run() {
        System.out.println("New Consultation");
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
                weatherList.clear();

                for (JsonElement jsonElement : jsonListArray) {
                    JsonObject jsonObject = jsonElement.getAsJsonObject();

                    double temp = jsonObject.getAsJsonObject("main").get("temp").getAsDouble();
                    int humidity = jsonObject.getAsJsonObject("main").get("humidity").getAsInt();
                    int clouds = jsonObject.getAsJsonObject("clouds").get("all").getAsInt();
                    double windSpeed = jsonObject.getAsJsonObject("wind").get("speed").getAsDouble();
                    double tempNew = (round((temp - 273) * 1000.0) / 1000.0);
                    String predictionTimestamp = jsonObject.get("dt_txt").getAsString();
                    double windSpeedMetersPerSecond =(windSpeed * 1000) / 3600;
                    windSpeedMetersPerSecond = Math.round(windSpeedMetersPerSecond * 100.0) / 100.0;

                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    String cityName = location.getCityName();
                    String island = location.getIsland();
                    Date timestamp = new Date();
                    String source = "prediction-provider";
                    Weather weather = new Weather(tempNew, humidity, clouds, windSpeedMetersPerSecond, timestamp, source, predictionTimestamp);
                    weather.addLocation(latitude,longitude,cityName,island);
                    weatherList.add(weather);
                }
                weatherStore.save(weatherList);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

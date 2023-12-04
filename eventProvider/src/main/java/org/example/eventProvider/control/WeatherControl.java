package org.example.eventProvider.control;

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.example.eventProvider.model.Weather;


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
        for (String url : urls) {
            try {
                URL apiUrl = new URL(url);
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
                JsonObject jsonObject = JsonParser.parseString(response.toString()).getAsJsonObject();


                double temp = jsonObject.getAsJsonObject("main").get("temp").getAsDouble();
                int humidity = jsonObject.getAsJsonObject("main").get("humidity").getAsInt();
                int clouds = jsonObject.getAsJsonObject("clouds").get("all").getAsInt();
                double windSpeed = jsonObject.getAsJsonObject("wind").get("speed").getAsDouble();
                double latitude = jsonObject.getAsJsonObject("coord").get("lat").getAsDouble();
                double longitude = jsonObject.getAsJsonObject("coord").get("lon").getAsDouble();
                String cityName = jsonObject.get("name").getAsString();

                double tempNew = Math.round(temp * 1000.0) / 1000.0;

                // Modificación aquí: Crear un objeto Weather con los atributos adicionales
                Date timestamp = new Date();  // Puedes ajustar esto según tus necesidades
                String source = "prediction-provider";
                Date predictionTimestamp = new Date();  // Puedes ajustar esto según tus necesidades

                //Weather weather = new Weather(tempNew, humidity, clouds, windSpeed, new Location(latitude, longitude, cityName), timestamp, source, predictionTimestamp);
                Weather weather = new Weather(tempNew, humidity, clouds, windSpeed, timestamp, source, predictionTimestamp);
                weather.addLocation(latitude, longitude, cityName);
                //   System.out.println("Nombre de la ciudad: " + cityName);
                //   System.out.println("Temperatura: " + tempNew + " ºC");
                //   System.out.println("Humedad: " + humidity + "%");
                //   System.out.println("Nubes: " + clouds + "%");
                //   System.out.println("Velocidad del viento: " + windSpeed + " m/s");
                //   System.out.println("Latitud: " + latitude);
                //   System.out.println("Longitud: " + longitude);
                //   System.out.println("-------------------------------------------------");

                weatherStore.insertWeatherData(weather, cityName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

package org.example.WeatherPredictionProvider.model;

import java.util.Date;

public class Weather {
    private final double temp;
    private final int humidity;
    private final int clouds;
    private final double windSpeed;
    private Location location;
   private final Date ts;
   private final String ss;
   private final String predictionTime;
    public Weather(double temp, int humidity, int clouds, double windSpeed, Date ts, String ss, String predictionTime) {
        this.temp = temp;
        this.humidity = humidity;
        this.clouds = clouds;
        this.windSpeed = windSpeed;
        this.ts = ts;
        this.ss = ss;
        this.predictionTime = predictionTime;
    }
    public double getTemp() {
        return temp;
    }
    public int getHumidity() {
        return humidity;
    }
    public int getClouds() {
        return clouds;
    }
    public double getWindSpeed() {
        return windSpeed;
    }
    public Location getLocation() {
        return location;
    }
    public void addLocation(double latitude, double longitude, String cityName) {
        location = new Location(latitude,longitude, cityName);
    }
}

package org.example.BusinessUnit.model;

import java.util.Date;

public class WeatherEvent {
    private final String island;
    private final String cityName;
    private final String predictionTime;
    private final double temp;
    private final int humidity;
    private final int clouds;
    private final double windSpeed;
    private final Date ts;

    public WeatherEvent(String island, String cityName, String predictionTime, double temp, int humidity, int clouds, double windSpeed, Date ts){

        this.island = island;
        this.cityName = cityName;
        this.predictionTime = predictionTime;
        this.temp = temp;
        this.humidity = humidity;
        this.clouds = clouds;
        this.windSpeed = windSpeed;
        this.ts = ts;
    }

    public String getIsland() {
        return island;
    }

    public String getCityName() {
        return cityName;
    }

    public String getPredictionTime() {
        return predictionTime;
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

    public Date getTs() {
        return ts;
    }
}

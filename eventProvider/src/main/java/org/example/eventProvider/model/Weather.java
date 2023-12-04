package org.example.eventProvider.model;

import java.util.Date;

public class Weather {
    private final double temp;
    private final int humidity;
    private final int clouds;
    private final double windSpeed;
    private Location location;
   //private final Date ts;  // Timestamp en UTC de la toma de la predicción
   //private final String ss;  // Fuente que produce el dato (prediction-provider)
   //private final Date predictionTime;  // Timestamp en UTC de la predicción

    public Weather(double temp, int humidity, int clouds, double windSpeed, Date ts, String ss, Date predictionTime) {
        //public Weather(double temp, int humidity, int clouds, double windSpeed, Location location, Date ts, String ss, Date predictionTime) {
        this.temp = temp;
        this.humidity = humidity;
        this.clouds = clouds;
        this.windSpeed = windSpeed;
        //this.location = location;
        //this.ts = ts;
        //this.ss = ss;
        //this.predictionTime = predictionTime;
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

   //public Date getTs() {
   //    return ts;
   //}

   //public String getSs() {
   //    return ss;
   //}

   //public Date getPredictionTime() {
   //    return predictionTime;
   //}

    public void addLocation(double latitude, double longitude, String cityName) {
        location = new Location(latitude,longitude, cityName);
    }
}

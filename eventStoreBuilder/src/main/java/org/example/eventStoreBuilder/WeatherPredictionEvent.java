package org.example.eventStoreBuilder;

import org.example.eventProvider.model.Weather;

import java.util.Date;

public class WeatherPredictionEvent {
    private String ss;
    private Date ts;
    private Date predictionTime;
    private double latitude;
    private double longitude;

    public WeatherPredictionEvent(Weather weather) {
        this.ss = "prediction-provider";
        this.ts = new Date();
        this.predictionTime = weather.getPredictionTime();
        this.latitude = weather.getLocation().getLatitude();
        this.longitude = weather.getLocation().getLongitude();
    }

    public String toJson() {
        // Implementar la lógica de serialización a JSON aquí
        return "{\"ss\":\"" + ss + "\",\"ts\":" + ts.getTime() + ",\"predictionTime\":" + predictionTime.getTime() + ",\"latitude\":" + latitude + ",\"longitude\":" + longitude + "}";
    }
}



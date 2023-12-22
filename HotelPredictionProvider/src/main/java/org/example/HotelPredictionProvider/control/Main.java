package org.example.HotelPredictionProvider.control;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        HotelControl hotelControl = new HotelControl();
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(hotelControl::run, 0, 12, TimeUnit.HOURS);
    }
}
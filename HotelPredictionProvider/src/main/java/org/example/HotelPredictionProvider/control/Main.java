package org.example.HotelPredictionProvider.control;

public class Main {
    public static void main(String[] args) {
        HotelControl hotelControl = new HotelControl();
        HotelProvider hotelProvider = new Scheduler();
        hotelProvider.scheduleHotelControl(hotelControl);
    }
}

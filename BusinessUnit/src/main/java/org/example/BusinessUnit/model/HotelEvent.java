package org.example.BusinessUnit.model;

import java.util.List;

public class HotelEvent {
    private final String island;
    private final String city;
    private final String hotelName;
    private final String checkout;
    private final List<String> averagePriceDay;
    private final List<String> cheapPriceDay;
    private final List<String> highPriceDay;
    public HotelEvent(String island, String city, String hotelName, String checkout, List<String> averagePriceDay, List<String> cheapPriceDay, List<String> highPriceDay){
        this.island = island;
        this.city = city;
        this.hotelName = hotelName;
        this.checkout = checkout;
        this.averagePriceDay = averagePriceDay;
        this.cheapPriceDay = cheapPriceDay;
        this.highPriceDay = highPriceDay;
    }
    public String getIsland() {
        return island;
    }
    public String getCity() {
        return city;
    }
    public String getHotelName() {
        return hotelName;
    }
    public String getCheckout() {
        return checkout;
    }
    public List<String> getAveragePriceDay() {
        return averagePriceDay;
    }
    public List<String> getCheapPriceDay() {
        return cheapPriceDay;
    }
    public List<String> getHighPriceDay() {
        return highPriceDay;
    }
}

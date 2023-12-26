package org.example.HotelPredictionProvider.model;

import java.util.Date;
import java.util.List;

public class Details {
    private final List<String> averagePriceDay;
    private final List<String> cheapPriceDay;
    private final List<String> highPriceDay;
    private final Date ts;
    private final String ss;
    private Location hotelLocation;

    public Details(List<String> averagePriceDay, List<String> cheapPriceDay, List<String> highPriceDay, Date ts, String ss){
        this.averagePriceDay = averagePriceDay;
        this.cheapPriceDay = cheapPriceDay;
        this.highPriceDay = highPriceDay;
        this.ts = ts;
        this.ss = ss;
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
    public Date getTs() {
        return ts;
    }
    public String getSs() {
        return ss;
    }
    public void addHotelLocation(String hotelKey, String checkout,String hotelName, String city, String island) {
        hotelLocation = new Location(hotelKey,checkout,hotelName,city,island);
    }
    public Location getHotelLocation() {
        return hotelLocation;
    }
}

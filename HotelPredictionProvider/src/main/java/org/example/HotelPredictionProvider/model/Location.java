package org.example.HotelPredictionProvider.model;

public class Location {
    private final String hotelKey;
    private final String hotelName;
    private final String checkout;
    private final String city;
    private final String island;

    public Location(String hotelKey, String checkout,String hotelName, String city, String island){

        this.hotelKey = hotelKey;
        this.hotelName = hotelName;
        this.checkout = checkout;
        this.city = city;
        this.island = island;
    }

    public String getHotelKey() {
        return hotelKey;
    }


    public String getCheckout() {
        return checkout;
    }
    public String getHotelName() {
        return hotelName;
    }

    public String getCity() {
        return city;
    }

    public String getIsland() {
        return island;
    }
}

package org.example.WeatherPredictionProvider.model;

public class Location {
     private double latitude;
     private double longitude;
     private String cityName;
    private final String island;

    public Location(double latitude, double longitude, String cityName, String island) {
         this.latitude = latitude;
         this.longitude = longitude;
         this.cityName = cityName;
        this.island = island;
    }
      public double getLatitude() {
          return latitude;
      }
      public double getLongitude() {
          return longitude;
      }
      public String getCityName() {
         return cityName;
     }

    public String getIsland() {
        return island;
    }
}

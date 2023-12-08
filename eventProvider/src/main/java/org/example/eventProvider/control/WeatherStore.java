package org.example.eventProvider.control;

import org.example.eventProvider.model.Weather;

import java.util.List;

public interface WeatherStore {
    //void insertWeatherData(Weather weather, String cityName);
    //void publishWeatherEvent(Weather weather);
     //void publishWeatherEvent(String eventJson);
     //List<Weather> getAllWeatherEvents();
     //List<Weather> getAllWeatherEvents();
     //List<String> getWeatherJson();
    void save(List<Weather> weatherList);

   //String convertWeatherToJson(Weather weather);
   //Weather convertJsonToWeather(String json);

    //@Override
   // public Weather getWeather(Weather weather) {
   //     Weather weather1 = new Weather(weather.getTemp(),weather.getHumidity(), weather.getClouds(), weather.getWindSpeed() , weather.getTs() ,weather.getSs() , weather.getPredictionTime());
   //     weather.addLocation(weather.getLocation().getLatitude(),weather.getLocation().getLongitude(), weather.getLocation().getCityName());
   //     return weather1;
   // }
  // @Override
  // public Weather getWeather(String eventJson) {
  //     // Método para convertir JSON a Weather
  //     return convertJsonToWeather(eventJson);
  // }
    //Weather getWeather();
    //Weather getWeather();
    //String getWeatherAsString();
}

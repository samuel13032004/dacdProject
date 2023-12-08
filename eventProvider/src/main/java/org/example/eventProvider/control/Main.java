package org.example.eventProvider.control;

import org.example.eventProvider.model.Weather;

import java.util.List;

public class Main {
    public static void main(String[] args) {
       //try {
       //    new org.sqlite.JDBC();
       //} catch (Exception e) {
       //    e.printStackTrace();
       //}
        OpenWeatherMapProvider apiClient = new OpenWeatherMapProvider();
        String apiKey = apiClient.getApiKey();


        // Configurar la WeatherStore y el dbPath
        //WeatherStore weatherStore = new SqliteWeatherStore();

        //// Iniciar las consultas periódicas
        //WeatherControl weatherControl = new WeatherControl(apiKey, apiClient.getUrls(), weatherStore);
        //apiClient.startPeriodicQueries(weatherControl);

        WeatherStore weatherStore = new JMSWeatherStore();
        //WeatherStore weatherStore1 = new JMSWeatherStore();
        //apiClient.setWeatherStore(weatherStore);
        //String dbPath = "jdbc:sqlite:weather.db";
        apiClient.setWeatherStore(weatherStore);
        //apiClient.setDbPath(dbPath);
       // System.out.println(weatherStore1.getWeather(String.valueOf(weatherStore1)));

        // Iniciar las consultas periódicas
        WeatherControl weatherControl = new WeatherControl(apiKey, apiClient.getUrls(), weatherStore);
        apiClient.startPeriodicQueries(weatherControl);

        //COMPROBACION
  //      System.out.println("####################################");
  //      List<Weather> weathers = weatherStore.getAllWeatherEvents();
  //      System.out.println(weathers.size());
  //      for (Weather name: weathers){
  //          System.out.println("nubes "+(name.getClouds())+" en "+name.getLocation().getCityName());
  //          weatherStore.publishWeatherEvent(name);
  //          System.out.println("añadido");
  //      }
  //      System.out.println(weathers.size());
//
  //      System.out.println(weatherStore.getWeatherJson());
    }
}

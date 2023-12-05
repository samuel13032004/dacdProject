package org.example.eventStoreBuilder;

import com.google.gson.Gson;
import org.example.eventProvider.control.WeatherStore;
import org.example.eventProvider.model.Weather;

public class PredictionProviderImplementation implements PredictionProvider {
    private WeatherStore weatherStore;
    public PredictionProviderImplementation(WeatherStore weatherStore) {
        this.weatherStore = weatherStore;
    }
    @Override
    public void publishWeatherPrediction(Weather weather) {
        // Convertir el objeto Weather a JSON (aquí asumimos una representación simple)
        //String eventJson = convertWeatherToJson(weather);
        // Publicar en el WeatherStore (por ejemplo, JMSWeatherStore)
        weatherStore.publishWeatherEvent(weather);
        // Puedes agregar lógica adicional aquí si es necesario
    }
    //private String convertWeatherToJson(Weather weather) {
    //    StringBuilder jsonBuilder = new StringBuilder();
    //    jsonBuilder.append("{");
    //    jsonBuilder.append("\"temp\":").append(weather.getTemp()).append(",");
    //    jsonBuilder.append("\"humidity\":").append(weather.getHumidity()).append(",");
    //    jsonBuilder.append("\"clouds\":").append(weather.getClouds()).append(",");
    //    jsonBuilder.append("\"windSpeed\":").append(weather.getWindSpeed()).append(",");
    //    jsonBuilder.append("\"latitude\":").append(weather.getLocation().getLatitude()).append(",");
    //    jsonBuilder.append("\"longitude\":").append(weather.getLocation().getLongitude()).append(",");
    //    jsonBuilder.append("\"timestamp\":").append(weather.getTs().getTime()).append(",");
    //    jsonBuilder.append("\"source\":\"").append(weather.getSs()).append("\",");
    //    jsonBuilder.append("\"predictionTimestamp\":").append(weather.getPredictionTime().getTime());
    //    jsonBuilder.append("}");
    //    return jsonBuilder.toString();
    //}
   //private String convertWeatherToJson(Weather weather) {
   //    // Implementa la lógica de conversión de Weather a JSON aquí
   //    // Puedes utilizar librerías como Gson para facilitar esta conversión
   //    // Por ahora, devuelvo un JSON de ejemplo
   //    return "{\"temp\":" + weather.getTemp() + ",\"humidity\":" + weather.getHumidity() + ",\"clouds\":" + weather.getClouds() +
   //            ",\"windSpeed\":" + weather.getWindSpeed() + ",\"latitude\":" + weather.getLocation().getLatitude() +
   //            ",\"longitude\":" + weather.getLocation().getLongitude() + ",\"timestamp\":" + weather.getTs().getTime() +
   //            ",\"source\":\"" + weather.getSs() + "\",\"predictionTimestamp\":" + weather.getPredictionTime().getTime() + "}";
   //}
    @Override
   //private Weather convertJsonToWeather(String json) {
   public Weather convertJsonToWeather(String json) {
       Gson gson = new Gson();
       return gson.fromJson(json, Weather.class);
   }
}

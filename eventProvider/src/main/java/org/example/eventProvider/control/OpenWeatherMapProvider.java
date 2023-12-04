package org.example.eventProvider.control;

import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class OpenWeatherMapProvider implements WeatherProvider {
    private final String ApiKey = System.getenv("APIKEY");
    //private static double[] locations = {new Location("28.1204", -15.5268,"Arucas"), }
   // private static  Location[] locations = {
   //         new Location(28.1204, -15.5268, "Arucas"),
   //         new Location(28.9625, -13.5500, "Ciudad2"),
   //         new Location(28.7355, -13.8646, "Ciudad3"),
   //         new Location(28.0950, -17.1135, "Ciudad4"),
   //         new Location(28.6573, -17.9183, "Ciudad5"),
   //         new Location(27.8078, -17.9187, "Ciudad6"),
   //         new Location(28.1044, -17.3436, "Ciudad7"),
   //         new Location(28.0921, -15.5415, "Ciudad8")
   // };
    private final String[] urls = {
            "https://api.openweathermap.org/data/2.5/weather?lat=28.1204&lon=-15.5268&appid=" + ApiKey,
            "https://api.openweathermap.org/data/2.5/weather?lat=28.9625&lon=-13.5500&appid=" + ApiKey,
            "https://api.openweathermap.org/data/2.5/weather?lat=28.7355&lon=-13.8646&appid=" + ApiKey,
            "https://api.openweathermap.org/data/2.5/weather?lat=28.0950&lon=-17.1135&appid=" + ApiKey,
            "https://api.openweathermap.org/data/2.5/weather?lat=28.6573&lon=-17.9183&appid=" + ApiKey,
            "https://api.openweathermap.org/data/2.5/weather?lat=27.8078&lon=-17.9187&appid=" + ApiKey,
            "https://api.openweathermap.org/data/2.5/weather?lat=28.1044&lon=-17.3436&appid=" + ApiKey,
            "https://api.openweathermap.org/data/2.5/weather?lat=28.0921&lon=-15.5415&appid=" + ApiKey
    };
    private WeatherStore weatherStore;
    private String dbPath;
    public String[] getUrls() {
        return urls;
    }
    public String getApiKey() {
        return ApiKey;
    }
    public void setWeatherStore(WeatherStore weatherStore) {
        this.weatherStore = weatherStore;
    }
    public void setDbPath(String dbPath) {
        this.dbPath = dbPath;
    }
    @Override
    public void startPeriodicQueries(WeatherControl weatherControl) {
        new WeatherControl(ApiKey, urls, weatherStore).run();

        // Asegúrate de que weatherStore y dbPath estén configurados antes de llamar a startPeriodicQueries
        if (weatherStore == null || dbPath == null) {
            throw new IllegalStateException("weatherStore and dbPath must be set before starting periodic queries.");
        }

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        long nowInMillis = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        long targetTimeInMillis = calendar.getTimeInMillis();

        if (nowInMillis >= targetTimeInMillis) {
            targetTimeInMillis += 24 * 60 * 60 * 1000;
        }

        long delay = targetTimeInMillis - nowInMillis;

        scheduler.scheduleAtFixedRate(() -> {
            weatherControl.run();
        }, delay, 6 * 60 * 60 * 1000, TimeUnit.MILLISECONDS);

        scheduler.schedule(() -> {
            scheduler.shutdown();
        }, 5 * 24, TimeUnit.HOURS);
    }
}

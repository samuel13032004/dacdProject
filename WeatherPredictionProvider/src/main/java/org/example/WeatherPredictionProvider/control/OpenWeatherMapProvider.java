package org.example.WeatherPredictionProvider.control;

import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class OpenWeatherMapProvider implements WeatherProvider {
    private final String ApiKey = System.getenv("APIKEY");
    private WeatherStore weatherStore;
    public String getApiKey() {
        return ApiKey;
    }
    public void setWeatherStore(WeatherStore weatherStore) {
        this.weatherStore = weatherStore;
    }
    @Override
    public void startPeriodicQueries(WeatherControl weatherControl) {
        if (weatherStore == null) {
            throw new IllegalStateException("weatherStore must be set before starting periodic queries.");
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
            targetTimeInMillis += TimeUnit.DAYS.toMillis(1);
        }
        long delay = targetTimeInMillis - nowInMillis;
        long period = TimeUnit.HOURS.toMillis(6);
        scheduler.scheduleAtFixedRate(weatherControl, delay, period, TimeUnit.MILLISECONDS);
    }
}

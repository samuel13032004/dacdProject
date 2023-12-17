package org.example.eventProvider.control;

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
        new WeatherControl(getApiKey(),weatherStore).run();
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

package org.example.HotelPredictionProvider.control;

import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Scheduler implements HotelProvider {
    @Override
    public void scheduleHotelControl(HotelControl hotelControl) {
        new HotelControl().run();
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
        long period = TimeUnit.HOURS.toMillis(12);
        scheduler.scheduleAtFixedRate(hotelControl, delay, period, TimeUnit.MILLISECONDS);
    }
}

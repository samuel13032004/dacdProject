package org.example.HotelPredictionProvider.control;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        HotelControl hotelControl = new HotelControl();
        //hotelControl.run();
        // Crear un ScheduledExecutorService con un hilo de ejecución
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        // Iniciar la tarea periódica que se ejecutará cada 10 minutos
        // Ejecutar la lógica de tu método run() aquí
        scheduler.scheduleAtFixedRate(hotelControl::run, 0, 10, TimeUnit.MINUTES);
    }
}
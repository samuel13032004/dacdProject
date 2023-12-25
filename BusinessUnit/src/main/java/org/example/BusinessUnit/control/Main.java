package org.example.BusinessUnit.control;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        // Crea una instancia de EventSubscriber
        EventSubscriber eventSubscriber = new EventSubscriber();
        // Crea un ScheduledExecutorService para ejecutar la suscripción cada 12 horas
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(eventSubscriber::startSubscription, 0, 12, TimeUnit.HOURS);
        // Espera 15 segundos
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        BusinessUnitCLI businessUnitCLI = new BusinessUnitCLI(eventSubscriber);
        businessUnitCLI.start();
    }

}

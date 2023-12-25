package org.example.BusinessUnit.control;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        // Crea una instancia de la interfaz EventSubscriptionManager
        EventSubscriber eventSubscriptionManager = new EventSubscriber();
        // Crea un ScheduledExecutorService para ejecutar la suscripción cada 12 horas
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(eventSubscriptionManager::startSubscription, 0, 12, TimeUnit.HOURS);
        // Espera 20 segundos
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Crea una instancia de la interfaz CommandLineInterface
        CommandLineInterface commandLineInterface = new BusinessUnitCLI(eventSubscriptionManager);
        commandLineInterface.start();
    }
}

package org.example.BusinessUnit.control;

import java.util.concurrent.CountDownLatch;
public class Main {
    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(2);
        EventSubscriber eventSubscriptionManager = new EventSubscriber();
        eventSubscriptionManager.startSubscription(latch);
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        CommandLineInterface commandLineInterface = new BusinessUnitCLI(eventSubscriptionManager);
        commandLineInterface.start();
    }
}

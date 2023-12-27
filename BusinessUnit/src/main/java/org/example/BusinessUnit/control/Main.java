package org.example.BusinessUnit.control;

public class Main {
    public static void main(String[] args) {
        EventSubscriber eventSubscriptionManager = new EventSubscriber();
        eventSubscriptionManager.startSubscription();
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        CommandLineInterface commandLineInterface = new BusinessUnitCLI(eventSubscriptionManager);
        commandLineInterface.start();
    }
}

package org.example.DatalakeBuilder;

public class Main{
    public static void main(String[] args) {
        EventStore eventStoreBuilder = new EventStoreBuilder();
        eventStoreBuilder.startSubscription();
    }
}

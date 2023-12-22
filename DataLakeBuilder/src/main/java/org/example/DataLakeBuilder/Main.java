package org.example.DataLakeBuilder;

public class Main{
    public static void main(String[] args) {
        EventStore eventStoreBuilder = new EventStoreBuilder();
        eventStoreBuilder.startSubscription();
    }
}

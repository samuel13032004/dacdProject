package org.example.eventStoreBuilder;

public interface EventStore {
    void publishWeatherEvent(String eventJson);
    void startSubscription();
    void stopSubscription();
}

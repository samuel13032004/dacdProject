package org.example.eventStoreBuilder;

public class Main {
    public static void main(String[] args) {

        //System.out.println("Hello world!");
        // Crear una instancia de EventStoreBuilder
        EventStore eventStore = new EventStoreBuilder();

        // Iniciar la suscripción al broker para recibir eventos
        eventStore.startSubscription();

        // Simular la publicación de eventos
        for (int i = 0; i < 5; i++) {
            String eventJson = buildEventJson(); // Aquí debes implementar la lógica para construir el JSON del evento
            eventStore.publishWeatherEvent(eventJson);
        }
    }

    private static String buildEventJson() {
        // Implementar la lógica para construir el JSON del evento según tus necesidades
        return "{\"event\": \"sampleEvent\"}";
    }
}
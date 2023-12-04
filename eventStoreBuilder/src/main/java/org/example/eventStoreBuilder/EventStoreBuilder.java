package org.example.eventStoreBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EventStoreBuilder implements EventStore {

    private static final String EVENT_STORE_DIRECTORY = "eventstore/prediction.weather/";

    private InternalEventStore internalEventStore;

    public EventStoreBuilder() {
        this.internalEventStore = new InternalEventStore();
    }

    @Override
    public void publishWeatherEvent(String eventJson) {
        // Puedes implementar lógica adicional antes de escribir en el archivo si es necesario
        internalEventStore.writeEventToFile(eventJson);
    }

    @Override
    public void startSubscription() {
        // Lógica de suscripción al broker
        // Aquí deberías implementar la lógica para recibir mensajes desde el broker
        // Puedes utilizar la implementación interna de InternalEventStore para recibir mensajes del broker
        System.out.println("Iniciando suscripción al broker...");
        // Implementa la lógica de suscripción aquí
        System.out.println("Suscripción al broker iniciada.");
    }

    public class InternalEventStore {
        private static final String EVENT_STORE_DIRECTORY = "eventstore/prediction.Weather/";

        public void writeEventToFile(String eventJson) {
            String currentDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
            String directoryPath = EVENT_STORE_DIRECTORY + currentDate;
            String filePath = directoryPath + ".events";

            // Verifica si el directorio existe; si no, créalo
            File directory = new File(directoryPath);
            if (!directory.exists()) {
                directory.mkdirs();  // Esto creará el directorio y sus padres si no existen
            }

            // Resto del código para escribir en el archivo...
            try (FileWriter writer = new FileWriter(filePath, true)) {
                writer.write(eventJson + "\n");
                System.out.println("Evento escrito en el archivo: " + filePath);
                System.out.println("Consulta cuando extraes datos de JMSWeatherStore: " + eventJson);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

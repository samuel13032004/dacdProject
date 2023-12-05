package org.example.eventStoreBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class InternalEventStore {
        private static final String EVENT_STORE_DIRECTORY = "eventstore/prediction.Weather/";
        public void writeEventToFile(String eventJson) {
            String currentDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
            String directoryPath = EVENT_STORE_DIRECTORY + currentDate;
            String filePath = directoryPath + ".events";
            File directory = new File(directoryPath);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            try (FileWriter writer = new FileWriter(filePath, true)) {
                writer.write(eventJson + "\n");
                System.out.println("Evento escrito en el archivo: " + filePath);
                System.out.println("Consulta cuando extraes datos de JMSWeatherStore: " + eventJson);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
}

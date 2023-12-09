package org.example.eventStoreBuilder;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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
            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
            String logMessage = String.format("[%s] Evento escrito en el archivo: %s", timestamp, filePath);
            System.out.println(logMessage);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            try (FileWriter writer = new FileWriter(filePath, true)) {
                writer.write(eventJson.replaceAll("\\s+", "") + "\n");
                System.out.println("Evento escrito en el archivo: " + filePath);
                System.out.println("Consulta cuando extraes datos de JMSWeatherStore: " + eventJson);
            } catch (IOException e) {
                System.err.println("Error al escribir en el archivo: " + filePath);
                e.printStackTrace();
            }
        }
   //private Date obtenerFechaPrediccion(String eventJson) {
   //    try {
   //        JsonObject jsonResponse = JsonParser.parseString(eventJson).getAsJsonObject();
   //        String predictionTimestamp = jsonResponse.get("predictionTimestamp").getAsString();

   //        // Parsear la cadena de fecha a un objeto Date
   //        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
   //        return dateFormat.parse(predictionTimestamp);
   //    } catch (Exception e) {
   //        e.printStackTrace();
   //        return new Date();  // En caso de error, devolver la fecha actual como valor por defecto
   //    }
   //}


}

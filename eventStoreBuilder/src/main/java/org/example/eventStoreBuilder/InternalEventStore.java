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
          String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
          String logMessage = String.format("[%s] Event written to the file: %s", timestamp, filePath);
          System.out.println(logMessage);
          if (!directory.exists()) {
              directory.mkdirs();
          }
          try (FileWriter writer = new FileWriter(filePath, true)) {
              writer.write(eventJson.replaceAll("\\s+", "") + "\n");
              System.out.println("Event written to the file: " + filePath);
              System.out.println("Query when extracting data from JMSWeatherStore: " + eventJson);
          } catch (IOException e) {
              System.err.println("Error writing to file: " + filePath);
              e.printStackTrace();
          }
        }
}

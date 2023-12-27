package org.example.BusinessUnit.control;

import org.example.BusinessUnit.model.HotelEvent;
import org.example.BusinessUnit.model.WeatherEvent;

import java.io.*;
import java.util.Date;

public class TSVWriter {
    static Date ts = new Date();
    static void writeWeatherEventToTSV(WeatherEvent weatherEvent, String filePath) {
        createDirectoryIfNotExists(filePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            if (fileIsEmpty(filePath)) {
                writer.write("Island\tCityName\tPredictionTime\tTemperature\tHumidity\tClouds\tWindSpeed\tTimestamp\n");
            }
            writer.write(String.format(
                    "%s\t%s\t%s\t%f\t%d\t%d\t%f\t%s\n",
                    weatherEvent.getIsland(),
                    weatherEvent.getCityName(),
                    weatherEvent.getPredictionTime(),
                    weatherEvent.getTemp(),
                    weatherEvent.getHumidity(),
                    weatherEvent.getClouds(),
                    weatherEvent.getWindSpeed(),
                    weatherEvent.getTs()
            ));

            System.out.println("Weather event written to TSV file: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static void writeHotelEventToTSV(HotelEvent hotelEvent, String filePath) {
        createDirectoryIfNotExists(filePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            // Escribir encabezados
            if (fileIsEmpty(filePath)) {
                writer.write("Island\tCity\tHotelName\tCheckout\tAveragePriceDay\tCheapPriceDay\tHighPriceDay\tTimestamp\n");
            }

            // Escribir datos de eventos de hotel
            writer.write(String.format(
                    "%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\n",
                    hotelEvent.getIsland(),
                    hotelEvent.getCity(),
                    hotelEvent.getHotelName(),
                    hotelEvent.getCheckout(),
                    String.join(",", hotelEvent.getAveragePriceDay()),
                    String.join(",", hotelEvent.getCheapPriceDay()),
                    String.join(",", hotelEvent.getHighPriceDay()),
                    ts
            ));

            System.out.println("Hotel event written to TSV file: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void createDirectoryIfNotExists(String filePath) {
        File file = new File(filePath);
        File parentDir = file.getParentFile();
        if (!parentDir.exists() && !parentDir.mkdirs()) {
            throw new IllegalStateException("Couldn't create directory: " + parentDir);
        }
    }
    private static boolean fileIsEmpty(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            return reader.readLine() == null;
        } catch (IOException e) {
            e.printStackTrace();
            return true; // Considerar el archivo como vacío en caso de error
        }
    }
}

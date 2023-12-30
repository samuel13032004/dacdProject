package org.example.BusinessUnit.control;

import org.example.BusinessUnit.model.HotelEvent;
import org.example.BusinessUnit.model.WeatherEvent;

import java.io.*;
import java.util.Date;

public class TSVWriter {
    static Date ts = new Date();
    private static final int MAX_LINES_FILEPATH_WEATHER = 281;
    private static final int MAX_LINES_FILEPATH_HOTEL = 16;

    static void clearFile(String filePath) {
        try (PrintWriter writer = new PrintWriter(filePath)) {
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static int countLines(String filePath) {
        try (LineNumberReader reader = new LineNumberReader(new FileReader(filePath))) {
            while (reader.skip(Long.MAX_VALUE) > 0) {
            }
            return reader.getLineNumber();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    static void writeWeatherEventToTSV(WeatherEvent weatherEvent, String filePath) {
        createDirectoryIfNotExists(filePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            if(countLines(filePath) >= MAX_LINES_FILEPATH_WEATHER) {
                clearFile(filePath);
            }
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static void writeHotelEventToTSV(HotelEvent hotelEvent, String filePath) {
        createDirectoryIfNotExists(filePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            if(countLines(filePath) >= MAX_LINES_FILEPATH_HOTEL) {
                clearFile(filePath);
            }
            if (fileIsEmpty(filePath)) {
                writer.write("Island\tCity\tHotelName\tCheckout\tAveragePriceDay\tCheapPriceDay\tHighPriceDay\tTimestamp\n");
            }
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
            return true;
        }
    }
}

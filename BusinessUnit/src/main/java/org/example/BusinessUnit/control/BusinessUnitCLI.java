package org.example.BusinessUnit.control;

import org.example.BusinessUnit.model.HotelEvent;
import org.example.BusinessUnit.model.WeatherEvent;

import java.util.List;
import java.util.Scanner;

public class BusinessUnitCLI implements CommandLineInterface{
    private EventSubscriber eventSubscriber;
    private final String[] islands = new String[]{
            "Gran Canaria", "Lanzarote", "Fuerteventura", "La Gomera", "La Palma", "El Hierro", "Tenerife"
    };

    public BusinessUnitCLI(EventSubscriber eventSubscriber) {
        this.eventSubscriber = eventSubscriber;
    }

    @Override
    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select an island: ");
        for (int i = 0; i < islands.length; i++) {
            System.out.println("Island " + (i + 1) + ": " + islands[i]);
        }
        int selectedIslandIndex;
        do {
            System.out.print("Enter the number of the selected island: ");
            selectedIslandIndex = scanner.nextInt();

            if (selectedIslandIndex < 1 || selectedIslandIndex > islands.length) {
                System.out.println("Invalid island number! Please choose a number within the range.");
            }
        } while (selectedIslandIndex < 1 || selectedIslandIndex > islands.length);
        scanner.nextLine();
        String selectedIsland = islands[selectedIslandIndex - 1];
        while (true) {
            System.out.println("\n1. Consult the weather forecast");
            System.out.println("2. Consult hotels");
            System.out.println("3. Change island");
            System.out.println("4. Exit");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    showWeatherForecast(selectedIsland);
                    break;
                case 2:
                    showHotels(selectedIsland);
                    break;
                case 3:
                    System.out.println("Select an island: ");
                    for (int i = 0; i < islands.length; i++) {
                        System.out.println("Island " + (i + 1) + ": " + islands[i]);
                    }
                    do {
                        System.out.print("Enter the number of the new island selected: ");
                        selectedIslandIndex = scanner.nextInt();

                        if (selectedIslandIndex < 1 || selectedIslandIndex > islands.length) {
                            System.out.println("Invalid island number! Please choose a number within the range.");
                        }
                    } while (selectedIslandIndex < 1 || selectedIslandIndex > islands.length);
                    scanner.nextLine();
                    selectedIsland = islands[selectedIslandIndex - 1];
                    break;
                case 4:
                    System.exit(0);
            }
        }
    }

    private void showWeatherForecast(String selectedIsland) {
        List<WeatherEvent> weatherEvents = eventSubscriber.getWeatherEvents();
        System.out.println("Weather forecast for " + selectedIsland + ":");
        for (WeatherEvent weatherEvent : weatherEvents) {
            if (selectedIsland.equals(weatherEvent.getIsland()))
                System.out.println("Date: " + weatherEvent.getPredictionTime() + ", Temperature: " + weatherEvent.getTemp() + "°C, Wind speed: " + weatherEvent.getWindSpeed() + " m/s");
        }
    }

    private void showHotels(String selectedIsland) {
        List<HotelEvent> hotelEvents = eventSubscriber.getHotelEvents();
        System.out.println("Hotel information for " + selectedIsland + ":");
        for (HotelEvent hotelEvent : hotelEvents) {
            if (selectedIsland.equals(hotelEvent.getIsland())) {
                System.out.println("Name of the hotel: " + hotelEvent.getHotelName());
                System.out.println("Departure date: " + hotelEvent.getCheckout());
                System.out.println("Average price days:");
                for (String day : hotelEvent.getAveragePriceDay()) {
                    System.out.println("  " + day);
                }
                System.out.println("Lower price days:");
                for (String day : hotelEvent.getCheapPriceDay()) {
                    System.out.println("  " + day);
                }
                System.out.println("Higher price days:");
                for (String day : hotelEvent.getHighPriceDay()) {
                    System.out.println("  " + day);
                }
                System.out.println("------------------------------------------------------------------");
            }
        }
    }
}

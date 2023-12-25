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
        System.out.println("Seleccione una isla:");
        for (int i = 0; i < islands.length; i++) {
            System.out.println("Isla " + (i + 1) + ": " + islands[i]);
        }
        int selectedIslandIndex;
        do {
            System.out.print("Ingrese el número de la isla seleccionada: ");
            selectedIslandIndex = scanner.nextInt();

            if (selectedIslandIndex < 1 || selectedIslandIndex > islands.length) {
                System.out.println("¡Número de isla no válido! Por favor, elija un número dentro del rango.");
            }
        } while (selectedIslandIndex < 1 || selectedIslandIndex > islands.length);
        scanner.nextLine();
        String selectedIsland = islands[selectedIslandIndex - 1];
        while (true) {
            System.out.println("\n1. Consultar pronóstico del tiempo");
            System.out.println("2. Consultar hoteles");
            System.out.println("3. Cambiar isla");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");
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
                    System.out.println("Seleccione una isla:");
                    for (int i = 0; i < islands.length; i++) {
                        System.out.println("Isla " + (i + 1) + ": " + islands[i]);
                    }
                    do {
                        System.out.print("Ingrese el número de la nueva isla seleccionada: ");
                        selectedIslandIndex = scanner.nextInt();

                        if (selectedIslandIndex < 1 || selectedIslandIndex > islands.length) {
                            System.out.println("¡Número de isla no válido! Por favor, elija un número dentro del rango.");
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
        System.out.println("Pronóstico del tiempo para " + selectedIsland + ":");
        for (WeatherEvent weatherEvent : weatherEvents) {
            if (selectedIsland.equals(weatherEvent.getIsland()))
                System.out.println("Fecha: " + weatherEvent.getPredictionTime() + ", Temperatura: " + weatherEvent.getTemp() + "°C, Velocidad del viento: " + weatherEvent.getWindSpeed() + " m/s");
        }
    }

    private void showHotels(String selectedIsland) {
        List<HotelEvent> hotelEvents = eventSubscriber.getHotelEvents();
        System.out.println("Información del hotel para " + selectedIsland + ":");
        for (HotelEvent hotelEvent : hotelEvents) {
            if (selectedIsland.equals(hotelEvent.getIsland())) {
                System.out.println("Nombre del hotel: " + hotelEvent.getHotelName());
                System.out.println("Fecha de salida: " + hotelEvent.getCheckout());
                System.out.println("Días de precio promedio:");
                for (String day : hotelEvent.getAveragePriceDay()) {
                    System.out.println("  " + day);
                }
                System.out.println("Días de precio más bajo:");
                for (String day : hotelEvent.getCheapPriceDay()) {
                    System.out.println("  " + day);
                }
                System.out.println("Días de precio más alto:");
                for (String day : hotelEvent.getHighPriceDay()) {
                    System.out.println("  " + day);
                }
                System.out.println("------------------------------------------------------------------");
            }
        }
    }
}

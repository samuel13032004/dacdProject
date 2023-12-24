package org.example.BusinessUnit.control;

import java.util.List;
import java.util.Scanner;

public class BusinessUnitCLI {
//  private DataMart dataMart;

//  public BusinessUnitCLI(DataMart dataMart) {
//      this.dataMart = dataMart;
//  }

//  public static void main(String[] args) {
//      // Configurar y conectar con la base de datos, el Data Lake, etc.
//      DataMart dataMart = new DataMart("jdbc:mysql://your-mysql-host:3306/your-database", "your-username", "your-password");

//      BusinessUnitCLI businessUnitCLI = new BusinessUnitCLI(dataMart);
//      businessUnitCLI.start();
//  }

//  public void start() {
//      Scanner scanner = new Scanner(System.in);
//      while (true) {
//          System.out.println("1. Consultar pronóstico del tiempo por isla");
//          System.out.println("2. Consultar hoteles por isla");
//          System.out.println("3. Salir");
//          System.out.print("Seleccione una opción: ");

//          int choice = scanner.nextInt();
//          scanner.nextLine(); // Consumir la nueva línea

//          switch (choice) {
//              case 1:
//                  showWeatherForecastByIsland();
//                  break;
//              case 2:
//                  showHotelsByIsland();
//                  break;
//              case 3:
//                  dataMart.close();
//                  System.exit(0);
//          }
//      }
//  }

//  private void showWeatherForecastByIsland() {
//      System.out.println("Seleccione una isla:");
//      List<String> availableIslands = dataMart.getAvailableIslandsFromEvents();

//      for (int i = 0; i < availableIslands.size(); i++) {
//          System.out.println((i + 1) + ". " + availableIslands.get(i));
//      }

//      Scanner scanner = new Scanner(System.in);
//      int selectedIslandIndex = scanner.nextInt();
//      String selectedIsland = availableIslands.get(selectedIslandIndex - 1);

//      List<WeatherForecast> weatherForecasts = dataMart.getWeatherForecastByIsland(selectedIsland);

//      System.out.println("Pronóstico del tiempo para " + selectedIsland + ":");
//      for (WeatherForecast forecast : weatherForecasts) {
//          System.out.println("Fecha: " + forecast.getDate() + ", Temperatura: " + forecast.getTemperature() + "°C");
//      }
//  }

//  private void showHotelsByIsland() {
//      System.out.println("Seleccione una isla:");
//      List<String> availableIslands = dataMart.getAvailableIslandsFromEvents();

//      for (int i = 0; i < availableIslands.size(); i++) {
//          System.out.println((i + 1) + ". " + availableIslands.get(i));
//      }

//      Scanner scanner = new Scanner(System.in);
//      int selectedIslandIndex = scanner.nextInt();
//      String selectedIsland = availableIslands.get(selectedIslandIndex - 1);

//      List<Hotel> hotels = dataMart.getHotelsByIsland(selectedIsland);

//      System.out.println("Hoteles en " + selectedIsland + ":");
//      for (Hotel hotel : hotels) {
//          System.out.println("Nombre: " + hotel.getName() + ", Precio: " + hotel.getPrice() + " por noche");
//      }
//  }
}


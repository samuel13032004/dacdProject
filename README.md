# Final project of the subject 

## Personal information

- **Name**: Samuel Déniz Santana
- **Subject**: Desarrollo de Aplicaciones para Ciencia de Datos (Development of Data Science Applications)
- **Year**: 2nd
- **Degree**: Degree in Data Science and Engineering
- **School of Computer Science of the University of Las Palmas de Gran Canaria**

## Summary of the Functionality
This project is a continuation of the two previous internships and is designed to handle weather data and relevant hotel information using Java 17 and the IntelliJ environment. The application consists of five parts: WeatherPredictionProvider, HotelPredictionProvider, a Broker (using Apache ActiveMQ), DataLakeBuilder and BusinessUnit.

The idea is that the Providers modules, both `WeatherPredictionProvider` which is in charge of obtaining weather data from certain Canary Islands and `HotelPredictionProvider` which is in charge of extracting information from different hotels on each island, are converted into events in JSON format and published as a topic to a server. These events, which include information such as forecast time, temperature, wind, cloud cover, etc. and hotel data such as days where prices are highest, lowest, etc., are sent to the Broker (ActiveMQ), which acts as an intermediary between the subscriber modules. The `DataLakeBuilder` module is in charge of storing these events of both topics in an orderly way in a directory, organising them by source and date. Finally, the `BusinessUnit` module aims to subscribe to both topics so that you can visualise the events in tsv files and perform an interactive query with the user. The client will be shown all the islands he can visit, once he selects an island, he will be offered both weather and accommodation information for the next five days.

## Design

In the following, some design principles and patterns present in the code will be explained:

### SOLID Design Principles:

**Single Responsibility Principle (SRP):**

Each class has a unique reason to change. For example, `EventStoreBuilder` handles subscribing to topics and storing events, while `EventSubscriber` handles subscribing and processing messages.

### Design Patterns:

**Observer Pattern:**

Used when implementing event subscription through the use of MessageListener. This allows different parts of the system to react asynchronously to events without direct coupling.

**Singleton pattern (for connection to ActiveMQ):**

The connection to ActiveMQ is handled using the Singleton pattern to ensure that only one connection instance exists. This improves efficiency and avoids unnecessary multiple connections.

**Singleton pattern (Approach):**

The current implementation of the controls (`WeatherControl` and `HotelControl`) are executed as scheduled tasks. While a classic singleton is not used, there is a singleton approach in the sense that a single instance of each control is executed over time.

#### General Patterns:
**Producer-Consumer:**

Both modules use the producer-consumer pattern to handle the generation of weather and hotel events, and their storage in `JMSWeatherStore` and `JMSHotelStore`, respectively.

## Class Diagram

![img.png](img.png)

## Arguments or environment variables

In the **WeatherPredictionProvider** module, an environment variable called "APIKEY" is used to protect the OpenWeatherMap API key used in weather queries. This practice ensures the security of the credentials, preventing their exposure in the source code. This environment variable can be accessed as follows:
```java
    private final String ApiKey = System.getenv("APIKEY");
```

On the other hand, in the **DataLakeBuilder** module, an argument called datalake is used when executing the program, representing the root directory where the events will be stored in text files.

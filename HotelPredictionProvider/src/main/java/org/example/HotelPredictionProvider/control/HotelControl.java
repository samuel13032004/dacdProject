package org.example.HotelPredictionProvider.control;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import org.example.HotelPredictionProvider.model.Details;
import org.example.HotelPredictionProvider.model.Location;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class HotelControl {
    LocalDate currentDate = LocalDate.now();
    LocalDate dateIn5Days = currentDate.plusDays(5);
    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String formattedDate = dateIn5Days.format(format);
    private final Location[] hotelLocations = {
            new Location("g562819-d600110", formattedDate,"HD Parque Cristobal Gran Canaria","Playa del inglés ","Gran Canaria"),
            new Location("g562819-d287443", formattedDate,"Barceló Margaritas","Playa del inglés ","Gran Canaria"),
            new Location("g562819-d289659", formattedDate,"Abora Continental by Lopesan Hotels","Playa del inglés ","Gran Canaria"),
            new Location("g187478-d273097", formattedDate,"Hotel Lancelot" ,"Arrecife","Lanzarote"),
            new Location("g187478-d507813", formattedDate,"Arrecife Gran Hotel & Spa","Arrecife","Lanzarote"),
            new Location("g659634-d237060", formattedDate,"Hotel LIVVO Risco Del Gato Suites","Corralejo","Fuerteventura"),
            new Location("g580322-d678447", formattedDate,"Barcelo Corralejo Bay" ,"Corralejo","Fuerteventura"),
            new Location("g580322-d288065", formattedDate,"LABRANDA Corralejo Village","Corralejo","Fuerteventura"),
            new Location("g187470-d15745744",formattedDate,"Avalos Beach House","San Sebastián de La Gomera","La Gomera"),
            new Location("g1877764-d4697336", formattedDate,"Pensión Amparo Las Hayas","San Sebastián de La Gomera","La Gomera"),
            new Location("g659324-d573646", formattedDate,"Hotel Valle Aridane","Llanos de Aridane","La Palma"),
            new Location("g659324-d948974", formattedDate,"Hotel Benahoare","Llanos de Aridane","La Palma"),
            new Location("g187474-d277394", formattedDate,"Parador de El Hierro","Valverde", "El Hierro"),
            new Location("g187482-d1555517", formattedDate,"Iberostar Heritage Grand Mencey","Santa Cruz de Tenerife","Tenerife"),
            new Location("g187482-d22999436", formattedDate,"Hotel Boutique San Diego","Santa Cruz de Tenerife","Tenerife"),
            new Location("g187482-d23271293", formattedDate,"AC Hotel Tenerife","Santa Cruz de Tenerife","Tenerife"),
    };
    public void run() {
        try {
            List<Details> hotelDetailsList = new ArrayList<>();
            for (Location location : hotelLocations) {
                System.out.println("Processing location: " + location.getHotelName());
                String apiUrl = buildApiUrl(location);
                String jsonResponse = makeApiRequest(apiUrl);
                Details details = processApiResponse(jsonResponse, location);
                if (details != null) {
                    hotelDetailsList.add(details);
                }
                System.out.println(hotelDetailsList);
                System.out.println(hotelDetailsList.size());
            }
            JMSHotelStore.save(hotelDetailsList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Details processApiResponse(String jsonResponse, Location location) {
        try {
            JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
            JsonObject resultObject = jsonObject.getAsJsonObject("result");
            String chkOut = resultObject.get("chk_out").getAsString();
            JsonObject heatmapObject = resultObject.getAsJsonObject("heatmap");
            JsonArray averagePriceDays = heatmapObject.getAsJsonArray("average_price_days");
            JsonArray cheapPriceDays = heatmapObject.getAsJsonArray("cheap_price_days");
            JsonArray highPriceDays = heatmapObject.getAsJsonArray("high_price_days");
            List<String> averagePriceDaysList = jsonArrayToList(averagePriceDays);
            List<String> cheapPriceDaysList = jsonArrayToList(cheapPriceDays);
            List<String> highPriceDaysList = jsonArrayToList(highPriceDays);
            long timestamp = jsonObject.get("timestamp").getAsLong();
            Date timestampDate = new Date(timestamp);
            String ss = "hotel-prediction";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println("Timestamp: " + sdf.format(timestampDate));
            System.out.println("chk_out: " + chkOut);
            System.out.println("average_price_days: " + averagePriceDaysList);
            System.out.println("cheap_price_days: " + cheapPriceDaysList);
            System.out.println("high_price_days: " + highPriceDaysList);
            System.out.println(location.getIsland());
            Details details = new Details(averagePriceDaysList, cheapPriceDaysList, highPriceDaysList, timestampDate, ss);
            details.addHotelLocation(location.getHotelKey(), location.getCheckout(), location.getHotelName(), location.getCity(), location.getIsland());
            return details;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String buildApiUrl(Location location) {
        return "https://data.xotelo.com/api/heatmap?hotel_key=" + location.getHotelKey() + "&chk_out=" + formattedDate;
    }

    private String makeApiRequest(String apiUrl) throws Exception {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        connection.disconnect();
        return response.toString();
    }

    private List<String> jsonArrayToList(JsonArray jsonArray) {
        List<String> resultList = new ArrayList<>();
        for (JsonElement element : jsonArray) {
            resultList.add(element.getAsString());
        }
        return resultList;
    }
}

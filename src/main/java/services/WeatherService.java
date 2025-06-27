package services;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherService {
    private static final String API_KEY = "e6af7855c26772d1c954c8fd46e744b1";

    public static entities.WeatherData getWeather(String cityName) throws Exception {
        String urlString = "https://api.openweathermap.org/data/2.5/weather?q="
                + cityName + "&appid=" + API_KEY + "&units=metric";

        URL url = new URL(urlString);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        int status = con.getResponseCode();
        if (status != 200) {
            throw new RuntimeException("Erreur API : HTTP " + status);
        }

        try (InputStreamReader reader = new InputStreamReader(con.getInputStream())) {
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();

            entities.WeatherData data = new entities.WeatherData();
            data.setCity(cityName);
            data.setTemperature(jsonObject.getAsJsonObject("main").get("temp").getAsDouble());
            data.setHumidity(jsonObject.getAsJsonObject("main").get("humidity").getAsInt());
            data.setDescription(jsonObject.getAsJsonArray("weather")
                    .get(0).getAsJsonObject().get("description").getAsString());

            return data;
        }
    }
}

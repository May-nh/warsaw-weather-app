import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONObject;

public class PolandWeather {

    // Replace with your own API key
    private static final String API_KEY = System.getenv("OPENWEATHER_API_KEY");



    public static void main(String[] args) {
        if (API_KEY == null) {
            System.err.println("API key not set!");
            return;
        }
        try {
            String city = "Warsaw";
            String url = "https://api.openweathermap.org/data/2.5/weather"
                    + "?q=" + city + ",PL"
                    + "&appid=" + API_KEY
                    + "&units=metric";

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                System.err.println(response.body());
                return;
            }



            JSONObject json = new JSONObject(response.body());

            double temperature = json.getJSONObject("main").getDouble("temp");
            int humidity = json.getJSONObject("main").getInt("humidity");
            String description = json.getJSONArray("weather")
                    .getJSONObject(0)
                    .getString("description");
            double windSpeed = json.getJSONObject("wind").getDouble("speed");

            System.out.println("Current weather in " + city + ", Poland:");
            System.out.println("Temperature: " + temperature + " °C");
            System.out.println("Weather: " + description);
            System.out.println("Humidity: " + humidity + "%");
            System.out.println("Wind speed: " + windSpeed + " m/s");

        } catch (Exception e) {
            System.err.println("Error fetching weather data");
            e.printStackTrace();
        }
    }
}


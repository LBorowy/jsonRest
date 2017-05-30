package pl.jwrabel.trainings.javandwro3.jsonRest.Unirest;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;

/**
 * Created by RENT on 2017-05-30.
 */
public class OpenMapWeatherClient {
    public static void main(String[] args) throws UnirestException {
        String apiKey = "3df1867b11fe664632aa9db7a8b10151";

        // Odpytanie o pogodę dla Warszawy
        String warsawJson = Unirest
                .get("http://api.openweathermap.org/data/2.5/weather?q=Warszawa,PL&appid=" + apiKey)
                .asString()
                .getBody();
        System.out.println("Dane dla Warszawy: ");
        System.out.println(warsawJson);

        // Odpytanie o pogodę dla podanego miasta
        String city = "Wrocław,PL";
        String weatherJson = Unirest
                .get("http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey)
                .asString()
                .getBody();
        System.out.println("Dane dla miasta " + city + ": ");
        System.out.println(weatherJson);

        // Odpytanie o pogodę dla podanego miasta i wyświetlenie temperatury w stopniach Celsjusza (metryczny układ jednostek)
        String city2 = "Wrocław,PL";
        String weatherJson2 = Unirest
                .get("http://api.openweathermap.org/data/2.5/weather?q=" + city2 + "&units=metric&appid=" + apiKey)
                .asString()
                .getBody();
        System.out.println("Dane dla miasta " + city + ": ");
        System.out.println(weatherJson2);


        // Wyciąganie elementów jsona z użyciem JsonNode -> chodzenie po JSONie bez konieczności mapowania na obiekty
		// http://api.openweathermap.org/data/2.5/weather?lat=35&lon=139&appid=a1fb2306e8575f67c23fc8f23062f7e1
        JsonNode weatherJsonObject = Unirest
                .get("http://api.openweathermap.org/data/2.5/weather?lat=35&lon=139&appid=3df1867b11fe664632aa9db7a8b10151")
                .asJson()
                .getBody();

        System.out.println("Dane dla współrzędnych lat=35, lon=139");
        double windSpeed = weatherJsonObject.getObject().optJSONObject("wind").getDouble("speed");
        System.out.println("Prędkość wiatru: " + windSpeed);

        String description = weatherJsonObject.getObject().optJSONArray("weather").optJSONObject(0).getString("description");
        System.out.println("Opis pogody: " + description);

        // Sprawdzenie temperatury w podanym mieście
        System.out.println("----- sprawdzam temperaturę dla Wrocławia ------");
        double temp = getTempForCity("Wrocław");
        System.out.println("Temp: " + temp);

        // Przejście po całej tablicy obiektów w JSON
        JSONArray weatherArray = weatherJsonObject.getObject().optJSONArray("weather");

        for (int i = 0; i < weatherArray.length(); i++) {
            String description1 = weatherArray.optJSONObject(i).getString("description");
            System.out.println(description1);
        }

        System.out.println("===== Mashape.com =====");
//        System.out.println("https://market.mashape.com/fayder/rest-countries-v1");

        // ODPYTANIE REST COUNTRIES
        JsonNode jsonNode = Unirest.get("https://restcountries-v1.p.mashape.com/all")
                .header("X-Mashape-Key", "NqMphWw04mmshJhkgC1nSywvqGYqp1rxhcKjsnv0r6yJxyODAp")
                .header("Accept", "application/json")
                .asJson().getBody();
        JSONArray array = jsonNode.getArray();
        System.out.println(array);
        System.out.println("-----------------------------------");

    }
    public static double getTempForCity(String cityName) {
        cityName = cityName.replace(" ", "%20");
        String apiKey = "3df1867b11fe664632aa9db7a8b10151";

        try {
            JsonNode jsonNode = Unirest
                    .get("http://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&appid=" + apiKey)
                    .asJson()
                    .getBody();
            double temp = jsonNode.getObject().optJSONObject("main").getDouble("temp");
            return temp;
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return -10000000;
    }
}


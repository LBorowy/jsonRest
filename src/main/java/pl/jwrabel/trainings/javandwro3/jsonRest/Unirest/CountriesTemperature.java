package pl.jwrabel.trainings.javandwro3.jsonRest.Unirest;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by RENT on 2017-05-30.
 */
public class CountriesTemperature {
    public static void main(String[] args) throws UnirestException {
//        String body = Unirest.get("https://restcountries-v1.p.mashape.com/all")
//                .header("X-Mashape-Key", "NqMphWw04mmshJhkgC1nSywvqGYqp1rxhcKjsnv0r6yJxyODAp")
//                .header("Accept", "application/json")
//                .asString()
//                .getBody();
//        System.out.println(body);

        JsonNode jsonNode = Unirest.get("https://restcountries-v1.p.mashape.com/all")
                .header("X-Mashape-Key", "NqMphWw04mmshJhkgC1nSywvqGYqp1rxhcKjsnv0r6yJxyODAp")
                .header("Accept", "application/json")
                .asJson()
                .getBody();
//        System.out.println(jsonNode);

        JSONArray jsonArray = jsonNode.getArray();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.optJSONObject(i);

            System.out.println("------------------------------------");
            String name = jsonObject.getString("name");
            System.out.println("Country: " + name);
            String capital = jsonObject.getString("capital");
            System.out.println("Capital: " + capital);

            double tempForCity = getTempForCity(capital);
            System.out.println("Temp: " + tempForCity + "\u00b0C");

        }

//        String name = jsonArray.optJSONObject(0).getString("name");
//        System.out.println(name);
//        String capital = jsonArray.optJSONObject(0).getString("capital");
//        System.out.println(capital);

    }

    public static double getTempForCity(String cityName) {
        String apiKey = "3df1867b11fe664632aa9db7a8b10151";

        cityName = cityName.replace(" ", "%20"); // zapezpieczenie przed spacja w nazwie (%20 w przeglądarce)
        if (cityName.equals("")) { // zabezpieczenie przed
            return -10000000;
        }
        // LUB
        if (cityName.isEmpty()) {
            return -10000000;
        }
        try {
            JsonNode jsonNode = Unirest
                    .get("http://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&units=metric&appid=" + apiKey)
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

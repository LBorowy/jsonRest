package pl.jwrabel.trainings.javandwro3.jsonRest;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

/**
 * Created by RENT on 2017-05-26.
 */
public class WeatherReader {
    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Weather weather = objectMapper.readValue(new File("weather.json"), Weather.class);
        System.out.println(weather);
    }
}

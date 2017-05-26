package pl.jwrabel.trainings.javandwro3.jsonRest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by RENT on 2017-05-25.
 */
public class Json {
    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper(); // potrafi mapować jasony na obiekty javove i odwrotnie
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        Point point = new Point(); // Obiekt klasy point
        point.setX(100);
        point.setY(300);

        String pointJson = objectMapper.writeValueAsString(point);
        System.out.println(pointJson);

        // ZAPIS JSONA do pliku
        objectMapper.writeValue(new File("points.json"), point);

        List<Point> pointList = new ArrayList<>();
        pointList.add(new Point());
        pointList.add(new Point());
        pointList.add(new Point());
        objectMapper.writeValue(new File("pointsList.json"), pointList);

        // ZAMIANA JSONa (Stringa, napisu) na obiekt JAVOVY
        String pointsJsonString = "{\n" +
                " \"x\" : 100,\n" +
                " \"x\" : 300\n" +
                "}";

        Point point1 = objectMapper.readValue(pointsJsonString, Point.class);

        // ZAMIANA JSONA Z PLIKU NA OBIEKT
        Point point2 = objectMapper.readValue(new File("points.json"), Point.class);
        List<Point> pointListFromFile = objectMapper.readValue(new File("pointsList.json"), List.class);

        // UWAGA
        // 1. musimy mieć gettery i settery
        // 2. musimy mieć konstruktor bezparametrowy

        ///////////////////////////////////////////////////////////////////////////////////////////////
        // 1. Stworzyć klasę odwzorowującą poniższego JSONA
//		Customer/Person
        String jsonString = "{\"firstName\":\"Adam\",\"lastName\":\"Kowalski\",\"birthYear\":1980,\"idNumber\":\"ABC\"}";

        // zamiana za Stringa
        Customer customer = objectMapper.readValue(jsonString, Customer.class);
        System.out.println("===== przeczytany customer =====");
        System.out.println(customer);
    }
}

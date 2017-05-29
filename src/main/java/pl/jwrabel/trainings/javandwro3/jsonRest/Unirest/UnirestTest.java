package pl.jwrabel.trainings.javandwro3.jsonRest.Unirest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by RENT on 2017-05-26.
 */
public class UnirestTest {
    public static void main(String[] args) throws UnirestException {
        // Zapytanie - odpowiedź = String (Hello World from Jakub)
        String simpleResponse = Unirest.get("http://195.181.209.160:8080/hi").asString().getBody();
        System.out.println(simpleResponse);


        // Zapytanie z użyciem Path variable/Path param -> zwraca Stringa (Hello World from Jakub + <PARAMETR>)
        String responseFromPathVariableQuery = Unirest.get("http://195.181.209.160:8080/helloPathParam/Adam").asString().getBody();
        System.out.println(responseFromPathVariableQuery);


        // Zapytanie z użyciem Request param -> zwraca Stringa (Hello World from Jakub + <PARAMETR_NAME>)
        String name = "ABC";
        String responseFromRequestParamQuery = Unirest.get("http://195.181.209.160:8080/helloRequestParam?name=" + name).asString().getBody();
        System.out.println(responseFromRequestParamQuery);

        // Zapytanie z użyciem 2 Request param -> zwraca Stringa (Hello World from Jakub + <PARAMETR_NAME> + <PARAMETR_SURNAME>)
        String responseFrom2RequestParamQuery = Unirest.get("http://195.181.209.160:8080/hello?name=Jakub&surname=Wrabel").asString().getBody();
        System.out.println(responseFrom2RequestParamQuery);

        // Zapytanie zwracające wszystkich customerów jako JSONA (Stringa)
        String customersJson = Unirest.get("http://195.181.209.160:8080/api/v1/customers").asString().getBody();
        System.out.println(customersJson);

        // WPIĘCIE JACKSONA W UNIRESTA - umożliwia autmatyczną zamianę JSONów na obiekty w Unireście (i obiektów na JSONy)
        Unirest.setObjectMapper(new ObjectMapper() {
            private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper
                    = new com.fasterxml.jackson.databind.ObjectMapper();

            public <T> T readValue(String value, Class<T> valueType) {
                try {
                    return jacksonObjectMapper.readValue(value, valueType);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            public String writeValue(Object value) {
                try {
                    return jacksonObjectMapper.writeValueAsString(value);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        // Pobranie jednego klienta (o ID string) i automatyczna zamiana
        // odpowiedzi serwera (JSONa z klientem) na obiekt klasy Customer
        Customer returnedCustomer = Unirest.get("http://195.181.209.160:8080/api/v1/customers/cfae8f41-e4f1-40a7-8598-db8430908111")
                .asObject(Customer.class).getBody();
        System.out.println(returnedCustomer);
//
//		// Pobranie wszystkich klientów i automatyczna zamiana
//		// odpowiedzi serwera (JSONa z kolekcją klientów) na tablicę obiektów klasy Customer
//		Customer[] customersArray = Unirest.get("http://195.181.209.160:8080/api/v1/customers").asObject(Customer[].class).getBody();
//		for (Customer customer : customersArray) {
//			System.out.println(customer);
//		}
//		// lub
////		Arrays.stream(customersArray).forEach(customer -> System.out.println(customer));
//
//		// Utworzenie i wysłanie POSTem nowego klienta do serwera (Tworzenie klienta)
		Customer customer = new Customer();
		customer.setBirthYear("1799");
		customer.setHeight(1.89);
		customer.setFirstName("Piotr");
		customer.setLastName("Kowalski");
		customer.setId(UUID.randomUUID().toString());

		String postResponse = Unirest
				.post("http://195.181.209.160:8080/api/v1/customers")
				.header("Content-Type", "application/json")
				.body(customer).asString().getBody();
		System.out.println(postResponse);
//
//
//		// Wyciąganie elementów jsona z użyciem JsonNode -> chodzenie po JSONie bez konieczności mapowania na obiekty
//		// http://api.openweathermap.org/data/2.5/weather?lat=35&lon=139&appid=a1fb2306e8575f67c23fc8f23062f7e1
//		JsonNode weatherJson = Unirest.get("http://api.openweathermap.org/data/2.5/weather?lat=35&lon=139&appid=a1fb2306e8575f67c23fc8f23062f7e1").asJson().getBody();
//
//		double windSpeed = weatherJson.getObject().optJSONObject("wind").getDouble("speed");
//		System.out.println(windSpeed);
//
//		String description = weatherJson.getObject().optJSONArray("weather").optJSONObject(0).getString("description");
//		System.out.println(description);

    }
}

package pl.jwrabel.trainings.javandwro3.jsonRest.consoleApp;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import pl.jwrabel.trainings.javandwro3.jsonRest.Unirest.Customer;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

/**
 * Created by RENT on 2017-05-29.
 */
public class ConsoleApp {
    public static void main(String[] args) throws UnirestException {
        setupUnirest();

        while (true) {
            System.out.println("===== MENU ======");
            System.out.println("1. Wypisz wszystkich - GET");
            System.out.println("2. Wypisz dane klienta - GET id");
            System.out.println("3. Stwóz klienta - POST");
            System.out.println("4. Zaktualizuj - PUT");
            System.out.println("5. Usuń - DELETE");
            System.out.println("0. Zakończ program");
            System.out.println("\nPodaj kod operacji: ");

            String operationString = new Scanner(System.in).nextLine();

            switch(operationString){
                case "0":
                    System.exit(0);
                case "1":
                    printAllCustomers();
                    break;
                case "2":
                    printAllDataCustomersById();
                    break;
                case "3":
                    createCustomer();
                    break;
                case "4":
                    updateCustomer();
                    break;
                case "5":
                    deleteCustomer();
                    break;
                default:
                    System.out.println("Niepoprawny kod operacji");
            }
        }
    }

    private static void deleteCustomer() {

    }

    private static void updateCustomer() {

    }

    private static void createCustomer() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj imię: ");
        String firstName = scanner.nextLine();
        System.out.println("Podaj nazwisko: ");
        String lastName = scanner.nextLine();
        System.out.println("Podaj datę urodzenia: ");
        String birthYear = scanner.nextLine();
        System.out.println("Podaj wzrost: ");
        Double height = scanner.nextDouble();

        Customer customer = new Customer();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setBirthYear(birthYear);
        customer.setHeight(height);
        customer.setId(UUID.randomUUID().toString());

        String postResponse = null;
        try {
            postResponse = Unirest
                    .post("http://195.181.209.160:8080/api/v1/customers")
                    .header("Content-Type","application/json")
                    .body(customer)
                    .asString().getBody();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        System.out.println("Odpowiedź serwera: " + postResponse);
    }

    private static void printAllDataCustomersById() {
        String id = new Scanner(System.in).nextLine();
        try {
            Customer customer = Unirest.get("http://195.181.209.160:8080/api/v1/customers/" + id).asObject(Customer.class).getBody();
            System.out.println("Dane użytkownika o ID: " + id);
            System.out.println(customer);
        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }

    private static void printAllCustomers() {
        String costumerJson = null;
        try {
            costumerJson = Unirest.get("http://195.181.209.160:8080/api/v1/customers").asString().getBody();

            com.fasterxml.jackson.databind.ObjectMapper objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();
            List<Customer> allCustomersList = objectMapper.readValue(costumerJson,
                    TypeFactory.defaultInstance().constructCollectionType(List.class, Customer.class));

            for (Customer customer : allCustomersList) {
                System.out.println(customer);
            }
        } catch (UnirestException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setupUnirest() {
        Unirest.setObjectMapper(new ObjectMapper() {
            private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper
                    = new com.fasterxml.jackson.databind.ObjectMapper();

            public <T> T readValue(String value, Class<T> valueType) {
                try {
                    return jacksonObjectMapper.readValue(value,valueType);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public String writeValue(Object value) {
                try {
                    return jacksonObjectMapper.writeValueAsString(value);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}

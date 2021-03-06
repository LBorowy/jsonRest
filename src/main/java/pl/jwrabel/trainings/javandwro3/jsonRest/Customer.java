package pl.jwrabel.trainings.javandwro3.jsonRest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by RENT on 2017-05-25.
 */
public class Customer {
    @JsonProperty("imię")
    private String firstName;
    @JsonProperty("nazwisko")
    private String lastName;
    private int birthYear;
    @JsonIgnore
    private String idNumber;

    public Customer(){}

//    public Customer(String firstName, String lastName, int birthYear, String idNumber) {
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.birthYear = birthYear;
//        this.idNumber = idNumber;
//    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthYear=" + birthYear +
                ", idNumber='" + idNumber + '\'' +
                '}';
    }
}

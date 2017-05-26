package pl.jwrabel.trainings.javandwro3.jsonRest;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * Created by RENT on 2017-05-26.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherDetails {
    private String main;
    private String description;

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "WeatherDetails{" +
                "main='" + main + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

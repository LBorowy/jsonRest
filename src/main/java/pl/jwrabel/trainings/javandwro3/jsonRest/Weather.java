package pl.jwrabel.trainings.javandwro3.jsonRest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by RENT on 2017-05-26.
 */
@JsonIgnoreProperties(ignoreUnknown = true) // wyłączamy walidacje w ten sposób, a nie dla wszystkich (77 w Json)
public class Weather {
    private String name;
    private String id;
    private List<WeatherDetails> weather;

    public List<WeatherDetails> getWeather() {
        return weather;
    }

    public void setWeather(List<WeatherDetails> weather) {
        this.weather = weather;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", weather=" + weather +
                '}';
    }
}

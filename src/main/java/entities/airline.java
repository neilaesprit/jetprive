package entities;

import java.util.List;

public class airline {

    private int id;
    private String name;
    private String country;
    private List<airport> airports;  // one-to-many relationship

    public airline(int id, String name, String country) {
        this.id = id;
        this.name = name;
        this.country = country;
    }

    public airline(String name, String country) {
        this.name = name;
        this.country = country;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public airline(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<airport> getAirports() {
        return airports;
    }

    public void setAirports(List<airport> airports) {
        this.airports = airports;
    }

    @Override
    public String toString() {
        return "Airline{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", airports=" + airports +
                '}';
    }
}

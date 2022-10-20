package main.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Cineplex implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private List<Cinema> cinemas;

    public Cineplex(String name, Cinema[] cinemas) {
        this.name = name;
        this.cinemas = new ArrayList<>(Arrays.asList(cinemas));
    }

    public List<Cinema> getCinemas() {
        return cinemas;
    }

    public Cinema getCinema(String location) {
        for (Cinema cinema : cinemas) {
            if (location.equals(cinema.getLocation()))
                return cinema;
        }
        return null;
    }

    public String getName() {
        return name;
    }
    
}

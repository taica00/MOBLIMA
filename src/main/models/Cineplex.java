package main.models;

import java.util.List;

/**
 * Represents a cineplex company.
 * A cineplex has cinemas in multiple locations.
 * @author Tai Chen An
 * @version 1.0 
 * @since 2022-11-06 
 */

public class Cineplex implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
     * The name of this cineplex company.
     */
    private String name;

    /**
     * The list of cinemas that belong to this cineplex.
     */
    private List<Cinema> cinemas;

    /**
     * Creates a cineplex with the given name and list of cinemas.
     * @param name
     * @param cinemas
     */
    
    public Cineplex(String name, List<Cinema> cinemas) {
        this.name = name;
        this.cinemas = cinemas;
    }

    /** 
     * Adds a cinema to this cineplex.
     * @param cinema
     */
    public void addCinema(Cinema cinema) {
        cinemas.add(cinema);
    }

    /** 
     * @return List<Cinema>
     */
    public List<Cinema> getCinemas() {
        return cinemas;
    }

    /** 
     * Searches the list of cinemas for the cinema at the given location.
     * @param location 
     * @return Cinema
     */
    public Cinema getCinema(String location) {
        for (Cinema cinema : cinemas) {
            if (location.equals(cinema.getLocation()))
                return cinema;
        }
        return null;
    }
    
    /** 
     * @return name of cineplex.
     */
    public String getName() {
        return name;
    }
    
}

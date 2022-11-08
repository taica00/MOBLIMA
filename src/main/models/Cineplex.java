package main.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a cineplex branch of Shaw Theatres.
 * A cineplex holds multiple cinema theatres.
 * Certain cineplexes may only hold cinema theatres of certain cinema classes.
 * @author Tai Chen An
 * @version 1.1 
 * @since 2022-11-09 
 */

public class Cineplex implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
     * The location of this cineplex.
     */
    private String location;

    /**
     * The list of cinema theatres at this cineplex.
     */
    private List<Cinema> cinemas;

    /**
     * Creates a cineplex with the given location.
     * Initiates the list of cinemas.
     * @param location location of cineplex.
     */
    public Cineplex(String location) {
        this.location = location;
        cinemas = new ArrayList<>();
    }

    /** 
     * Adds a cinema theatre to this cineplex.
     * @param cinema
     */
    public void addCinema(Cinema cinema) {
        cinemas.add(cinema);
    }

    /** 
     * @return List of cinema theatres.
     */
    public List<Cinema> getCinemas() {
        return cinemas;
    }

    /** 
     * Searches the list of cinemas for the cinema at the given location.
     * @param location 
     * @return Cinema
     */
    public Cinema getCinema(CinemaClass cinemaClass, int cinemaNumber) {
        for (Cinema cinema : cinemas) {
            if (cinemaClass.equals(cinema.getCinemaClass()) && cinemaNumber == cinema.getCinemaNumber())
                return cinema;
        }
        return null;
    }
    
    /** 
     * @return location of cineplex.
     */
    public String getLocation() {
        return location;
    }
    
}

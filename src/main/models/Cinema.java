package main.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a physical cinema. 
 * A cinema can hold many showtime sessions.
 * @author Tai Chen An
 * @version 1.0 
 * @since 2022-11-06 
 */

public class Cinema implements java.io.Serializable {
    private static final long serialVersionUID = 2L;
    /**
     * The cineplex that this cinema belongs to
     */
    private String cineplex;

    /**
     * The cinema code of this cinema to be used in {@link Transaction}.
     */
    private String cinemaCode;

    /**
     * The physical location of this cinema, which is also the name that it is known by.
     */
    private String location;

    /**
     * A list of showtime sessions that is held at this cinema.
     */
    private List<Session> sessions;

    /**
     * Creates a new Cinema which is owned by the given cineplex and located at the given location.
     * The cinema code is then generated.
     * @param cineplex
     * @param location
     */
    public Cinema(String cineplex, String location) {
        if (cineplex == null || cineplex.isEmpty() || location == null || location.isBlank())
            throw new IllegalArgumentException("Fields cannot be null or blank.");
        this.cineplex = cineplex;
        this.location = location;
        sessions = new ArrayList<>();
        generateCinemaCode();
    }

    /** 
     * Creates a showtime {@link Session} with the given inputs.
     * The session is then added to this cinema.
     * @param movie
     * @param dateTime
     * @param cinemaClass
     * @param is3D
     */
    public void addSession(Movie movie, LocalDateTime dateTime, String cinemaClass, boolean is3D) {
        sessions.add(new Session(this, movie, dateTime, cinemaClass, is3D));
    }

    /**
     * Searches for all showtime sessions in this cinema screening the given movie.
     * These sessions are then returned in a list.
     * @param movie
     * @return List<Session>
     */
    public List<Session> getSessions(Movie movie) {
        List<Session> movieSessions = new ArrayList<>();
        for (Session session : sessions) {
            if (session.getMovie().getTitle().equals(movie.getTitle()))
                movieSessions.add(session);
        }
        return movieSessions;
    }

    /**
     * Removes the given session from the list of sessions. 
     * @param session
     */
    public void removeSession(Session session) {
        sessions.remove(session);
    }
    
    /** 
     * @return location of the cinema along with its cineplex.
     */
    @Override
    public String toString() {
        return cineplex + " - " + location;
    }

    /**
     * Generates the cinema code for this cinema.
     * First letter is from the cineplex, next two are from the location.
     */
    private void generateCinemaCode() {
        StringBuilder sb = new StringBuilder();
        sb.append(cineplex.charAt(0));
        String[] locationWords = location.split(" ");
        if (locationWords.length == 2)
            sb.append(locationWords[0].charAt(0) + "" + locationWords[1].charAt(0));
        else
            sb.append(location.substring(0, 2).toUpperCase());
        cinemaCode = sb.toString();
    }
    
    /** 
     * @return List<Session>
     */
    public List<Session> getSessions() {
        return sessions;
    }

    /** 
     * @return location of cinema.
     */
    public String getLocation() {
        return location;
    }

    /** 
     * @return cinemaCode of cinema.
     */
    public String getCinemaCode() {
        return cinemaCode;
    }

    /** 
     * @return cineplex name of cinema.
     */
    public String getCineplex() {
        return cineplex;
    }
}

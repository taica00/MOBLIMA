package main.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a cinema theatre.
 * A theatre can host many showtime sessions.
 * @author Tai Chen An
 * @version 1.1 
 * @since 2022-11-09 
 */

public class Cinema implements java.io.Serializable {
    private static final long serialVersionUID = 2L;
    /**
     * The cineplex that this cinema belongs to.
     */
    private Cineplex cineplex;

    /**
     * The identifier for this cinema theatre. 
     * Cinemas of different cinema classes can have the same number.
     * Cinemas of the same cinema class must have a unique number.
     */
    private int cinemaNumber;

    /**
     * The cinema code of this cinema to be used in {@link Transaction}.
     */
    private String cinemaCode;

    /** 
     * The class of this cinema.
     */
    private CinemaClass cinemaClass;

    /**
     * A list of showtime sessions that is held at this cinema.
     */
    private List<Session> sessions;

    /**
     * Creates a new Cinema which is owned by the given cineplex and located at the given location.
     * The cinema code is then generated.
     * @param cineplex cineplex that this cinema belongs to.
     * @param cinemaNumber identifier for this cinema theatre.
     * @param cinemaClass class of this cinema.
     */
    public Cinema(Cineplex cineplex, int cinemaNumber, CinemaClass cinemaClass) {
        if (cineplex == null || cinemaClass == null)
            throw new IllegalArgumentException("Fields cannot be null or blank.");
        if (cinemaNumber < 1)
            throw new IllegalArgumentException("Cinema number start from 1 onwards.");
        this.cineplex = cineplex;
        this.cinemaNumber = cinemaNumber;
        this.cinemaClass = cinemaClass;
        sessions = new ArrayList<>();  
        generateCinemaCode();
    }

    /**
     * Adds a session with the given fields to the list of sessions for this cinema.
     * A new Seating is generate with this cinema class.
     * @param movie movie of session
     * @param dateTime date and time of session
     * @param is3D if session is 3D screening
     */
    public void addSession(Movie movie, LocalDateTime dateTime, boolean is3D) {
        sessions.add(new Session(this, movie, dateTime, new Seating(cinemaClass), is3D));
    }

    /**
     * Searches for all showtime sessions in this cinema screening the given movie.
     * These sessions are then returned in a list.
     * @param movie movie of sessions to search for.
     * @return list of sessions screening the given movie.
     */
    public List<Session> getSessions(Movie movie) {
        List<Session> movieSessions = new ArrayList<>();
        for (Session session : sessions) {
            if (session.getMovie().equals(movie))
                movieSessions.add(session);
        }
        return movieSessions;
    }

    /**
     * Removes the given session from this cinema.
     * @param session session to be removed
     */
    public void removeSession(Session session) {
        sessions.remove(session);
    }

    /**
     * @return string of cineplex location, cinema class and number of this cinema.
     */
    @Override
    public String toString() {
        return cineplex.getLocation() + " " + cinemaClass + " " + cinemaNumber;
    }

    /**
     * Generates the cinema code for this cinema.
     * First letter is from the cineplex.
     * Second letter is from the cinema class.
     * Last letter is from the cinema number.
     */
    private void generateCinemaCode() {
        StringBuilder sb = new StringBuilder();
        sb.append(cineplex.getLocation().toUpperCase().charAt(0));
        sb.append(cinemaClass.toString().toUpperCase().charAt(0));
        sb.append((char)(cinemaNumber + '@'));
        cinemaCode = sb.toString();
    }

    /**
     * @return class of this cinema
     */
    public CinemaClass getCinemaClass() {
        return cinemaClass;
    }

    /** 
     * @return list of sessions for this cinema
     */
    public List<Session> getSessions() {
        return sessions;
    }

    /** 
     * @return cinemaCode of this cinema
     */
    public String getCinemaCode() {
        return cinemaCode;
    }

    /** 
     * @return Cineplex that this cinema is at
     */
    public Cineplex getCineplex() {
        return cineplex;
    }

    /**
     * @return number of this cinema
     */
    public int getCinemaNumber() {
        return cinemaNumber;
    }
}

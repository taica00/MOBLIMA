package main.models;

import java.time.LocalDateTime;

/**
 * Represents a showtime session of a movie.
 * A session can only be screening one movie.
 * @author Tai Chen An
 * @version 1.0 
 * @since 2022-11-06 
 */

public class Session implements java.io.Serializable {
    private static final long serialVersionUID = 6L;

    /**
     * The cinema that this session is held at.
     */
    private Cinema cinema;

    /**
     * The movie that this session is screening.
     */
    private Movie movie;
    
    /**
     * The date and time of this session.
     */
    private LocalDateTime dateTime;

    /**
     * Indicates whether the session is a 3D screening.
     */
    private boolean is3D;

    /**
     * Creates a new Session with the given fields.
     * The seating layout is determined by the given cinema class.
     * @param cinema
     * @param movie
     * @param dateTime
     * @param is3D
     */
    public Session(Cinema cinema, Movie movie, LocalDateTime dateTime, boolean is3D) {
        if (cinema == null || movie == null || dateTime == null)
            throw new IllegalArgumentException("Fields cannot be null");
        this.cinema = cinema;
        this.movie = movie;
        this.dateTime = dateTime;
        this.is3D = is3D;
    }

    /** 
     * @return details of the session.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Cinema: " + cinema);
        sb.append("\nMovie: " + movie.getTitle());
        sb.append("\nDate: " + dateTime.toLocalDate());
        sb.append("\nTime: " + dateTime.toLocalTime());
        sb.append("\n3D Screening: " + is3D);
        return sb.toString();
    }

    /** 
     * @return Cinema hosting this session.
     */
    public Cinema getCinema() {
        return cinema;
    }

    /** 
     * @param cinema cinema hosting this session.
     */
    public void setCinema(Cinema cinema) {
        if (cinema == null)
            throw new IllegalArgumentException("cinema cannot be null.");
        this.cinema = cinema;
    }

    /** 
     * @return Movie screened during this session.
     */
    public Movie getMovie() {
        return movie;
    }
    
    /** 
     * @param movie movie screened during this session.
     */
    public void setMovie(Movie movie) {
        if (movie == null)
            throw new IllegalArgumentException("movie cannot be null.");
        this.movie = movie;
    }

    /** 
     * @return date and time of this session.
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }
  
    /** 
     * @param dateTime date and time of this session.
     */
    public void setDateTime(LocalDateTime dateTime) {
        if (dateTime == null)
            throw new IllegalArgumentException("dateTime cannot be null.");
        this.dateTime = dateTime;
    }

    /** 
     * @param is3D
     */
    public void set3D(boolean is3D) {
        this.is3D = is3D;
    }

    /** 
     * @return true if this session is a 3D screening.
     */
    public boolean is3D() {
        return is3D;
    }

}

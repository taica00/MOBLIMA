package main.models;

import java.time.LocalDateTime;

public class Session implements java.io.Serializable {
    private static final long serialVersionUID = 6L;
    private Cinema cinema;
    private Movie movie;
    private LocalDateTime dateTime;
    private CinemaClass cinemaClass;
    private Seating seating;
    private boolean is3D;

    public Session(Cinema cinema, Movie movie, LocalDateTime dateTime, String cinemaClass, boolean is3D) {
        if (cinema == null || movie == null || dateTime == null)
            throw new IllegalArgumentException("Fields cannot be null");
        this.cinema = cinema;
        this.movie = movie;
        this.dateTime = dateTime;
        this.cinemaClass = CinemaClass.valueOf(cinemaClass);
        seating = new Seating(this.cinemaClass);
        this.is3D = is3D;
    }

    
    /** 
     * @return String
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Cinema: " + cinema);
        sb.append("\nMovie: " + movie.getTitle());
        sb.append("\nDate: " + dateTime.toLocalDate());
        sb.append("\nTime: " + dateTime.toLocalTime());
        sb.append("\nCinema class: " + cinemaClass);
        sb.append("\n3D Screening: " + is3D);
        return sb.toString();
    }

    
    /** 
     * @return Cinema
     */
    public Cinema getCinema() {
        return cinema;
    }

    
    /** 
     * @param cinema
     */
    public void setCinema(Cinema cinema) {
        if (cinema == null)
            throw new IllegalArgumentException("cinema cannot be null.");
        this.cinema = cinema;
    }

    
    /** 
     * @return Movie
     */
    public Movie getMovie() {
        return movie;
    }

    
    /** 
     * @param movie
     */
    public void setMovie(Movie movie) {
        if (movie == null)
            throw new IllegalArgumentException("movie cannot be null.");
        this.movie = movie;
    }

    
    /** 
     * @return LocalDateTime
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    
    /** 
     * @param dateTime
     */
    public void setDateTime(LocalDateTime dateTime) {
        if (dateTime == null)
            throw new IllegalArgumentException("dateTime cannot be null.");
        this.dateTime = dateTime;
    }

    
    /** 
     * @return CinemaClass
     */
    public CinemaClass getCinemaClass() {
        return cinemaClass;
    }

    
    /** 
     * @param cinemaClass
     */
    public void setCinemaClass(CinemaClass cinemaClass) {
        this.cinemaClass = cinemaClass;
    }

    
    /** 
     * @return Seating
     */
    public Seating getSeating() {
        return seating;
    }

    
    /** 
     * @param is3D
     */
    public void set3D(boolean is3D) {
        this.is3D = is3D;
    }

    
    /** 
     * @return boolean
     */
    public boolean is3D() {
        return is3D;
    }

}

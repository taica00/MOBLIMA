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

    public Cinema getCinema() {
        return cinema;
    }

    public void setCinema(Cinema cinema) {
        if (cinema == null)
            throw new IllegalArgumentException("cinema cannot be null.");
        this.cinema = cinema;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        if (movie == null)
            throw new IllegalArgumentException("movie cannot be null.");
        this.movie = movie;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        if (dateTime == null)
            throw new IllegalArgumentException("dateTime cannot be null.");
        this.dateTime = dateTime;
    }

    public CinemaClass getCinemaClass() {
        return cinemaClass;
    }

    public void setCinemaClass(CinemaClass cinemaClass) {
        this.cinemaClass = cinemaClass;
    }

    public Seating getSeating() {
        return seating;
    }

    public void set3D(boolean is3D) {
        this.is3D = is3D;
    }

    public boolean is3D() {
        return is3D;
    }

}

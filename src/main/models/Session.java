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
        if (cinema == null || movie == null)
            throw new IllegalArgumentException("Cinema/Movie cannot be null");
        this.cinema = cinema;
        this.movie = movie;
        this.dateTime = dateTime;
        this.cinemaClass = CinemaClass.valueOf(cinemaClass);
        seating = new Seating(this.cinemaClass);
        this.is3D = is3D;
    }

    public Cinema getCinema() {
        return cinema;
    }

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
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

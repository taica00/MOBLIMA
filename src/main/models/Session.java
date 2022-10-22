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

    public enum CinemaClass {STANDARD, GVMAX, GOLDCLASS, GOLDCLASSEXPRESS, DELUXEPLUS, GEMINI, IMAX, LUMIERE, DREAMERS, PREMIERE, PLATINUM}

    public Session(Cinema cinema, Movie movie, LocalDateTime dateTime, String cinemaClass) {
        this.cinema = cinema;
        this.movie = movie;
        this.dateTime = dateTime;
        this.cinemaClass = CinemaClass.valueOf(cinemaClass);
        seating = new Seating(this.cinemaClass);
        is3D = false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(cinema.getCineplex() + " " + cinema.getLocation() + " - " + cinemaClass + "\n");
        sb.append(movie.getTitle() + " (" + movie.getRating() + ")\n");
        sb.append(dateTime.getDayOfMonth() + " " + dateTime.getMonth() + " " + dateTime.getYear() + " " + dateTime.toLocalTime() + "\n");
        return sb.toString();
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

    public void setSeating(Seating seating) {
        this.seating = seating;
    }
 

}

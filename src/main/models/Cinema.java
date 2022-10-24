package main.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Cinema implements java.io.Serializable {
    private static final long serialVersionUID = 2L;
    private String cineplex;
    private String cinemaCode;
    private String location;
    private List<Session> sessions;

    public Cinema(String cineplex, String location) {
        this.cineplex = cineplex;
        this.location = location;
        sessions = new ArrayList<>();
        generateCinemaCode();
    }

    public void addSession(Movie movie, LocalDateTime dateTime, String cinemaClass, boolean is3D) {
        sessions.add(new Session(this, movie, dateTime, cinemaClass, is3D));
    }

    @Override
    public String toString() {
        return cineplex + " - " + location;
    }

    private void generateCinemaCode() {
        StringBuilder sb = new StringBuilder();
        sb.append(cineplex.charAt(0));
        String[] locationWords = location.split(" ");
        if (locationWords.length == 2)
            sb.append(locationWords[0].charAt(0) + "" + locationWords[1].charAt(1));
        else
            sb.append(location.substring(0, 2).toUpperCase());
        cinemaCode = sb.toString();
    }

    public List<Session> getSessions(Movie movie) {
        List<Session> movieSessions = new ArrayList<>();
        for (Session session : sessions) {
            if (session.getMovie().getTitle().equals(movie.getTitle()) && session.getDateTime().isAfter(LocalDateTime.now()))
                movieSessions.add(session);
        }
        return movieSessions;
    }
    
    public List<Session> getSessions() {
        return sessions;
    }

    public String getLocation() {
        return location;
    }

    public String getCinemaCode() {
        return cinemaCode;
    }

    public String getCineplex() {
        return cineplex;
    }
}

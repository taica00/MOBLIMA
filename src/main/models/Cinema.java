package main.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Cinema implements java.io.Serializable {
    private static final long serialVersionUID = 2L;
    private String cineplex;
    private String location;
    private List<Session> showTimes;

    public Cinema(String cineplex, String location) {
        this.cineplex = cineplex;
        this.location = location;
        showTimes = new ArrayList<>();
    }

    public void addSession(Movie movie, String date, String time, String cinemaType) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy hh:mma");
        LocalDateTime dateTime = LocalDateTime.parse(date + " " + time, formatter);
        showTimes.add(new Session(this, movie, dateTime, cinemaType));
    }

    @Override
    public String toString() {
        return cineplex + " - " + location;
    }

    public List<Session> getShowTimes() {
        return showTimes;
    }
    
    public List<Session> getShowTimes(Movie movie) {
        List<Session> sessions = new ArrayList<>();
        for (Session session : showTimes) {
            if (session.getMovie().getTitle().equals(movie.getTitle()))
                sessions.add(session);
        }
        return sessions;
    }

    public String getLocation() {
        return location;
    }

    public String getCineplex() {
        return cineplex;
    }
}

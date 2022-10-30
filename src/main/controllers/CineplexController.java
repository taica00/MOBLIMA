package main.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import main.models.Cinema;
import main.models.Cineplex;
import main.models.Movie;
import main.models.Session;
import main.ui.CinemaListUI;
import main.ui.ShowTimesUI;

public class CineplexController extends Controller {
    private static List<Cineplex> cineplexes;
    private static final String FILEPATH = "src/main/data/cineplexes.ser";

    public static void viewShowTimes(Movie movie) {
        List<List<Session>> movieSessions = new ArrayList<>();
        for (Cineplex cineplex : cineplexes) {
            for (Cinema cinema : cineplex.getCinemas()) {
                List<Session> cinemaSessions = cinema.getSessions(movie);
                if (cinemaSessions.isEmpty())
                    continue;
                movieSessions.add(cinemaSessions);
            }
        }
        ShowTimesUI.view(movieSessions);
    }

    public static void displayCinemas(boolean admin) {
        List<Cinema> cinemas = new ArrayList<>();
        for (Cineplex cineplex : cineplexes) {
            for (Cinema cinema : cineplex.getCinemas())
                cinemas.add(cinema);
        }
        CinemaListUI.view(cinemas, admin);
    }

    public static void addSession(Cinema cinema, String movieTitle, String date, String cinemaClass, String time, boolean is3D) {
        Movie movie = MovieController.searchMovie(movieTitle);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyHHmm");
        LocalDateTime dateTime = LocalDateTime.parse(date+time, formatter);
        cinema.addSession(movie, dateTime, cinemaClass, is3D);
    }

    public static void updateSession(Cinema cinema, String movie, String date, String cinemaClass, String time) {
        Session session = searchSession(cinema, movie, date, cinemaClass, time);
    }

    public static void removeSession(Cinema cinema, String movie, String date, String cinemaClass, String time) {
        Session session = searchSession(cinema, movie, date, cinemaClass, time);
    }

    public static Session searchSession(Cinema cinema, String movie, String date, String cinemaClass, String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyHHmm");
        LocalDateTime dateTime = LocalDateTime.parse(date+time, formatter);
        for (Session session : cinema.getSessions()) {
            if (movie.equalsIgnoreCase(session.getMovie().getTitle()) &&  dateTime.isEqual(session.getDateTime()) && CinemaClass.valueOf(cinemaClass).equals(session.getCinemaClass())) {
                return session;
            }
        }
    }

    public static void loadCineplexes() {
        cineplexes = loadData(FILEPATH);
    }

    public static void saveCineplexes() {
        saveData(cineplexes, FILEPATH);
    }
}

package main.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import main.models.Cinema;
import main.models.CinemaClass;
import main.models.Cineplex;
import main.models.Movie;
import main.models.Session;
import main.ui.CinemaList;
import main.ui.ShowTimes;
import main.ui.UpdateSession;

public class CineplexController extends Controller {
    private static List<Cineplex> cineplexes;
    private static final String FILEPATH = "src/main/data/cineplexes.ser";
    private static final String PATTERN = "ddMMyyHHmm";

    
    /** 
     * @param movie
     */
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
        ShowTimes.view(movieSessions);
    }

    
    /** 
     * @param admin
     */
    public static void displayCinemas(boolean admin) {
        List<Cinema> cinemas = new ArrayList<>();
        for (Cineplex cineplex : cineplexes) {
            for (Cinema cinema : cineplex.getCinemas())
                cinemas.add(cinema);
        }
        CinemaList.view(cinemas, admin);
    }

    
    /** 
     * @param cinema
     * @param movieTitle
     * @param date
     * @param cinemaClass
     * @param time
     * @param is3D
     */
    public static void addSession(Cinema cinema, String movieTitle, String date, String cinemaClass, String time, boolean is3D) {
        Movie movie = MovieController.searchMovie(movieTitle);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN);
        LocalDateTime dateTime = LocalDateTime.parse(date+time, formatter);
        cinema.addSession(movie, dateTime, cinemaClass, is3D);
    }

    
    /** 
     * @param cinema
     * @param movie
     * @param date
     * @param cinemaClass
     * @param time
     * @return boolean
     */
    public static boolean updateSession(Cinema cinema, String movie, String date, String cinemaClass, String time) {
        Session session = searchSession(cinema, movie, date, cinemaClass, time);
        if (session == null)
            return false;
        UpdateSession.view(session);
        return true;
    }

    
    /** 
     * @param cinema
     * @param movie
     * @param date
     * @param cinemaClass
     * @param time
     */
    public static void removeSession(Cinema cinema, String movie, String date, String cinemaClass, String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN);
        LocalDateTime dateTime = LocalDateTime.parse(date+time, formatter);
        List<Session> cinemaSessions = cinema.getSessions();
        cinemaSessions.removeIf(x -> (movie.equalsIgnoreCase(x.getMovie().getTitle()) &&  dateTime.isEqual(x.getDateTime()) && CinemaClass.valueOf(cinemaClass).equals(x.getCinemaClass())));
    }

    
    /** 
     * @param cinema
     * @param movie
     * @param date
     * @param cinemaClass
     * @param time
     * @return Session
     */
    public static Session searchSession(Cinema cinema, String movie, String date, String cinemaClass, String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN);
        LocalDateTime dateTime = LocalDateTime.parse(date+time, formatter);
        for (Session session : cinema.getSessions()) {
            if (movie.equalsIgnoreCase(session.getMovie().getTitle()) &&  dateTime.isEqual(session.getDateTime()) && CinemaClass.valueOf(cinemaClass).equals(session.getCinemaClass())) {
                return session;
            }
        }
        return null;
    }

    
    /** 
     * @param cinemaString
     * @return Cinema
     */
    public static Cinema searchCinema(String cinemaString) {
        for (Cineplex cineplex : cineplexes) {
            Cinema cinema = cineplex.getCinema(cinemaString);
            if (cinema != null)
                return cinema;
        }
        return null;
    }

    public static void loadCineplexes() {
        cineplexes = loadData(FILEPATH);
    }

    public static void saveCineplexes() {
        saveData(cineplexes, FILEPATH);
    }
}

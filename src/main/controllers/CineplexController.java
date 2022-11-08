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

/**
 * This class manages user actions pertaining to the {@link Cineplex} and {@link Cinema} class.
 * Stores a list of cineplexes.
 * @author Tai Chen An
 * @version 1.1 
 * @since 2022-11-09 
 */

public class CineplexController extends Controller {
    /**
     * The list of all cineplexes.
     */
    private static List<Cineplex> cineplexes;
    private static final String FILEPATH = "src/main/data/cineplexes.ser";
    private static final String PATTERN = "ddMMyyHHmm";

    /** 
     * Search all cinemas for sessions screening the given movie.
     * Calls {@link ShowTimes} to display these sessions.
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
     * Calls {@link CinemaList} to display all cinemas. 
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
     * Adds a new session to the given cinema with the given fields.
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
        cinema.addSession(new Session(cinema, movie, dateTime, is3D));
    }
 
    /**
     * This method is called when an admin wishes to update the details of a session.
     * {@link searchSession} is first called to search the given cinema for a session that matches the given inputs.
     * Then {@link UpdateSession} is called to receive user inputs.
     * @param cinema
     * @param movie
     * @param date
     * @param cinemaClass
     * @param time
     * @return true if session is found, false if session is not found.
     */
    public static boolean updateSession(Cinema cinema, String movie, String date, String cinemaClass, String time) {
        Session session = searchSession(cinema, movie, date, cinemaClass, time);
        if (session == null)
            return false;
        UpdateSession.view(session);
        return true;
    }

    /**
     * Removes a session with the given details from the given cinema's list of sessions.
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
     * Searches the given cinema for a session matching the given inputs.
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
     * Searches the list of cineplexes for a cinema that matches the given fields.
     * @param cinemaString
     * @return Cinema
     */
    public static Cinema searchCinema(CinemaClass cinemaClass, int cinemaNumber) {
        for (Cineplex cineplex : cineplexes) {
            Cinema cinema = cineplex.getCinema(cinemaClass, cinemaNumber);
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

package main.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import main.models.Cinema;
import main.models.CinemaClass;
import main.models.Cineplex;
import main.models.Movie;
import main.models.MovieStatus;
import main.models.Session;
import main.ui.CineplexList;
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
            List<Session> cineplexSessions = new ArrayList<>();
            for (Cinema cinema : cineplex.getCinemas()) {
                for (Session session : cinema.getSessions()) {
                    if (session.getMovie().equals(movie)) 
                        cineplexSessions.add(session);  
                }
            }
            if (cineplexSessions.isEmpty()) 
                continue;
            cineplexSessions.sort(new Comparator<Session>() {
                @Override
                public int compare(Session s1, Session s2) {
                    LocalDateTime s1DateTime = s1.getDateTime();
                    LocalDateTime s2DateTime = s2.getDateTime();
                    if (!s1DateTime.toLocalDate().equals(s2DateTime.toLocalDate()))
                        return s1DateTime.compareTo(s2DateTime);
                    CinemaClass s1CinemaClass = s1.getCinema().getCinemaClass();
                    CinemaClass s2CinemaClass = s2.getCinema().getCinemaClass();
                    if (s1CinemaClass != s2CinemaClass)
                        return s1CinemaClass.compareTo(s2CinemaClass);
                    return s1DateTime.compareTo(s2DateTime);
                }
            });
            movieSessions.add(cineplexSessions);
        }
        ShowTimes.view(movieSessions);
    }

    public static void viewShowTimes(Cineplex cineplex, boolean admin) {
        List<Session> sessions = new ArrayList<>();
        for (Cinema cinema : cineplex.getCinemas()) {
            for (Session session : cinema.getSessions()) {
                MovieStatus movieStatus = session.getMovie().getShowingStatus();
                if (movieStatus.equals(MovieStatus.PREVIEW) || movieStatus.equals(MovieStatus.NOW_SHOWING))
                    sessions.add(session);
            }
        }
        sessions.sort(new Comparator<Session>() {
            @Override
            public int compare(Session s1, Session s2) {
                if (!s1.getMovie().equals(s2.getMovie()))
                    return s1.getMovie().getTitle().compareTo(s2.getMovie().getTitle());
                LocalDateTime s1DateTime = s1.getDateTime();
                LocalDateTime s2DateTime = s2.getDateTime();
                if (!s1DateTime.toLocalDate().equals(s2DateTime.toLocalDate()))
                    return s1DateTime.compareTo(s2DateTime);
                CinemaClass s1CinemaClass = s1.getCinema().getCinemaClass();
                CinemaClass s2CinemaClass = s2.getCinema().getCinemaClass();
                if (s1CinemaClass != s2CinemaClass)
                    return s1CinemaClass.compareTo(s2CinemaClass);
                return s1DateTime.compareTo(s2DateTime);
            }
        });
        ShowTimes.view(sessions, admin);
    }

    /**
     * Calls {@link CineplexList} to display all cinemas. 
     * @param admin
     */
    public static void displayCineplexes(boolean admin) {
        CineplexList.view(cineplexes, admin);
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
    public static void addSession(Cinema cinema, String movieTitle, String date, String time, boolean is3D) {
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
    public static boolean updateSession(Cineplex cineplex, String movie, String date, String cinemaClass, String time) {
        Session session = searchSession(cineplex, movie, date, cinemaClass, time);
        if (session == null)
            return false;
        UpdateSession.view(session);
        return true;
    }

    /**
     * Removes a session with the given details from the given cinema's list of sessions.
     * @param cinema cinema hosting the session.
     * @param movie movie that session is screening.
     * @param date date of session.
     * @param cinemaClass class of cinema hosting the session.
     * @param time time of session.
     */
    public static void removeSession(Cinema cinema, String movie, String date, String cinemaClass, String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN);
        LocalDateTime dateTime = LocalDateTime.parse(date+time, formatter);
        List<Session> cinemaSessions = cinema.getSessions();
        cinemaSessions.removeIf(x -> (movie.equalsIgnoreCase(x.getMovie().getTitle()) &&  dateTime.isEqual(x.getDateTime()) && CinemaClass.valueOf(cinemaClass).equals(x.getCinema().getCinemaClass())));
    }

    /** 
     * Searches the given cinema for a session matching the given inputs.
     * @param cineplex cineplex of session to search for.
     * @param movie movie that session is screening.
     * @param date date of session to search for.
     * @param cinemaClass class of cinema screening the session.
     * @param time time of session to search for.
     * @return session that matches the given inputs or null if session is not found.
     */
    public static Session searchSession(Cineplex cineplex, String movie, String date, String cinemaClass, String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN);
        LocalDateTime dateTime = LocalDateTime.parse(date+time, formatter);
        for (Cinema cinema : cineplex.getCinemas()) {
            for (Session session : cinema.getSessions())
                if (movie.equalsIgnoreCase(session.getMovie().getTitle()) &&  dateTime.isEqual(session.getDateTime()) && CinemaClass.valueOf(cinemaClass.toUpperCase()).equals(cinema.getCinemaClass())) 
                    return session;
        }
        return null;
    }
    
    /**
     * Searches the list of cineplexes for a cinema that matches the given fields.
     * @param cinemaString
     * @return Cinema that matches the given inputs or null if cinema is not found.
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

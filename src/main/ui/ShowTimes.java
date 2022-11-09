package main.ui;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import main.controllers.CineplexController;
import main.controllers.InputController;
import main.controllers.SessionController;
import main.models.Cinema;
import main.models.CinemaClass;
import main.models.Cineplex;
import main.models.Movie;
import main.models.MovieStatus;
import main.models.Session;

/**
 * This class provides the UI to display session showtimes.
 * Showtimes can be viewed for a particular movie, or for a particular cinema.
 * @author Tai Chen An
 * @version 1.0 
 * @since 2022-11-08 
 */

public class ShowTimes extends UI {
    private static final String RETURN_HOME = "Returning to homepage.";
    private static final String RETURN_ADMIN = "Returning to admin menu.";
    private static final String NO_SESSION = "Session not found.";
    private static final String INVALID_DATETIME = "Invalid date or time format.";
    private static final String THIN_DIVIDER = "\n-------------------------------------------------------------";
    private static final String THICK_DIVIDER = "\n=============================================================";

    /** 
     * Displays all sessions of a particular movie across all cinemas, given in a list.
     * Movie-goer enters session info to book tickets for the session.
     * Calls {@link SessionController} to handle seats selection for requested session.
     * @param movieSessions list of sessions for a particular movie.
     */
    public static void view(List<List<Session>> movieSessions) { 
        if (movieSessions.isEmpty()) {
            System.out.println("No showtimes available for this movie");
            return;
        }
        LocalDate date = null;
        CinemaClass cinemaClass = null;
        int rowCount = 0;
        for (List<Session> cineplexSessions : movieSessions) {
            System.out.println(THICK_DIVIDER);
            System.out.print(cineplexSessions.get(0).getCinema().getCineplex().getLocation());
            for (Session session : cineplexSessions) {
                LocalDateTime dateTime = session.getDateTime();
                LocalDate sessionDate = dateTime.toLocalDate();
                CinemaClass sessionClass = session.getCinema().getCinemaClass();
                if (date == null || !sessionDate.isEqual(date)) {
                    System.out.println(THIN_DIVIDER);
                    date = sessionDate;
                    System.out.print(date.getDayOfMonth() + " " + date.getMonth() + " " + date.getYear());
                    cinemaClass = null;
                }
                if (cinemaClass == null || !cinemaClass.equals(sessionClass)) {
                    cinemaClass = sessionClass;
                    System.out.println("\n\n" + cinemaClass);
                    rowCount = 0;
                }
                System.out.print(dateTime.toLocalTime());
                if (session.is3D())
                    System.out.print("(3D)");
                System.out.print(" ");
                if (++rowCount == 10) {
                    System.out.println();
                    rowCount = 0;
                }
            }
        }
        System.out.println(THIN_DIVIDER);
        if (!bookTickets())
            return;
        String cineplexString = InputController.getString("Enter cineplex e.g. JCube : ");
        String[] sessionInfo = getSessionInfo();
        System.out.println();
        try {
            if (!SessionController.viewSeating(movieSessions, cineplexString, sessionInfo[0], sessionInfo[1].toUpperCase(), sessionInfo[2]))
                System.out.println(NO_SESSION + " " + RETURN_HOME);
        } catch (DateTimeParseException e) {
            System.out.println(INVALID_DATETIME + " " + RETURN_HOME);
        } catch (IllegalArgumentException e) {
            System.out.print("Invalid cinema class. Valid cinema classes: ");
            for (CinemaClass cc : CinemaClass.values())
                System.out.print(cc + ", ");
            System.out.println("\n" + RETURN_HOME);
        }
    }

    
    /**
     * Displays all sessions of the given cinema.
     * User enters session info to retrieve the session from database.
     * Displays different options depending on whether user is admin or movie-goer.
     * @param cinema 
     * @param sessions
     */
    public static void view(List<Session> sessions, boolean admin) { 
        if (sessions.isEmpty()) {
            System.out.println("No showtimes available for this cinema.");
            return;
        }
        LocalDate date = null;
        CinemaClass cinemaClass = null;
        Movie movie = null;
        int rowCount = 0;
        for (Session session: sessions) {
            MovieStatus movieStatus = session.getMovie().getShowingStatus();
            if (movieStatus.equals(MovieStatus.COMING_SOON) || movieStatus.equals(MovieStatus.END_OF_SHOWING))
                continue;
            LocalDateTime dateTime = session.getDateTime();
            LocalDate sessionDate = dateTime.toLocalDate();
            CinemaClass sessionClass = session.getCinema().getCinemaClass();
            if (movie == null || session.getMovie() != movie) {
                System.out.println("\n" + THICK_DIVIDER);
                movie = session.getMovie();
                System.out.print(movie.getTitle() + " (" + movie.getRating() + ")");
                date = null;
            }
            if (date == null || !sessionDate.isEqual(date)) {
                date = sessionDate;
                System.out.println(THIN_DIVIDER);   
                System.out.print(date.getDayOfMonth() + " " + date.getMonth() + " " + date.getYear());  
                cinemaClass = null;           
            }
            if (cinemaClass == null || !cinemaClass.equals(sessionClass)) {
                cinemaClass = sessionClass;
                System.out.println("\n\n" + cinemaClass);
                rowCount = 0;
            }
            System.out.print(dateTime.toLocalTime());
            if (session.is3D())
                System.out.print("(3D) ");
            else
                System.out.print(" ");
            if (++rowCount == 10) {
                System.out.println();
                rowCount = 0;
            }
        }
        System.out.println(THIN_DIVIDER);
        if (!admin)
            movieGoerOptions(sessions.get(0).getCinema().getCineplex());
        else
            adminOptions(sessions);
         
    }   
    
    /**
     * Asks if movie-goer wants to book tickets.
     * Gets information of requested session.
     * Calls {@link SessionController} to handle seats selection for requested session.
     * @param cineplex cineplex that movie-goer is viewing.
     */
    private static void movieGoerOptions(Cineplex cineplex) {
        if (!bookTickets())
            return;
        String sessionMovie = InputController.getString("Enter movie title: ");
        String[] sessionInfo = getSessionInfo();
        System.out.println();
        try {
            if (!SessionController.viewSeating(cineplex, sessionMovie, sessionInfo[0], sessionInfo[1].toUpperCase(), sessionInfo[2]))
                System.out.println(NO_SESSION + " " + RETURN_HOME);
        } catch (DateTimeParseException e) {
            System.out.println(INVALID_DATETIME + " " + RETURN_HOME);
        } catch (IllegalArgumentException e) {
            System.out.print("Invalid input. " + RETURN_HOME);
        }
    }
    
    /**
     * Displays admin options for configuring of session. 
     * @param cineplex cineplex that admin is viewing.
     */
    private static void adminOptions(List<Session> sessions) {
        System.out.println("\n1. Create showtime | 2. Update showtime | 3. Remove showtime | 4. Return to admin menu\n");
        int choice = InputController.getInt(1, 4, "Enter your option: ");
        if (choice == 4)
            return;
        System.out.println("Enter showtime details.");
        String sessionMovie = InputController.getString("Enter movie title: ");
        String[] sessionInfo = getSessionInfo();
        try {
            switch(choice) {
                case 1: 
                    boolean is3D = InputController.getBoolean("Is this a 3D showing?(Y/N): ", 'Y', 'N');
                    CineplexController.addSession(cinema, sessionMovie, sessionInfo[0], sessionInfo[1].toUpperCase(), sessionInfo[2], is3D);
                    System.out.println("Cinema showtime added. " + RETURN_ADMIN);
                    break;
                case 2: 
                    if (!CineplexController.updateSession(cinema, sessionMovie, sessionInfo[0], sessionInfo[1].toUpperCase(), sessionInfo[2]))
                        System.out.println(NO_SESSION + " " + RETURN_ADMIN);
                    break;
                case 3: 
                    CineplexController.removeSession(cinema, sessionMovie, sessionInfo[0], sessionInfo[1].toUpperCase(), sessionInfo[2]);
                    System.out.println("Cinema showtime removed. " + RETURN_ADMIN);
                    break;
                default: System.out.println("Something weird happened.");
            }
        } catch (DateTimeParseException e) {
            System.out.println(INVALID_DATETIME + " " + RETURN_HOME);
        } catch (IllegalArgumentException e) {
            System.out.print("Invalid input. " + RETURN_HOME);
        }
    }   

    /** 
     * @return true if movie-goer requests to book tickets.
     */
    private static boolean bookTickets() {
        System.out.println("1. Book tickets | 2. Return to homepage");
        int choice = InputController.getInt(1, 2, "Select an option: ");
        System.out.println();
        return choice == 1;
    }
    
    /**
     * Requests user to input details of a session.
     * @return array of user inputted session information.
     */
    private static String[] getSessionInfo() {
        String date = InputController.getNumericString("Enter date in ddMMyy format, e.g. 251022: ", 6, 6);
        String cinemaClass = InputController.getString("Enter cinemaClass, e.g. imax: ");
        String time = InputController.getNumericString("Enter time in HHmm format, e.g. 1620: ", 4, 4);
        return new String[]{date, cinemaClass, time};
    }
}

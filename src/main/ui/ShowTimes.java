package main.ui;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

import main.controllers.CineplexController;
import main.controllers.InputController;
import main.controllers.SessionController;
import main.models.Cinema;
import main.models.CinemaClass;
import main.models.Movie;
import main.models.MovieStatus;
import main.models.Session;

public class ShowTimes extends UI {
    private static final String RETURN_HOME = "Returning to homepage.";
    private static final String RETURN_ADMIN = "Returning to admin menu.";
    private static final String NO_SESSION = "Session not found.";
    private static final String THIN_DIVIDER = "\n-------------------------------------------------------------";
    private static final String THICK_DIVIDER = "\n=============================================================";

    public static void view(List<List<Session>> movieSessions) { // view sessions for a particular movie
        if (movieSessions.isEmpty()) {
            System.out.println("No showtimes available for this movie");
            return;
        }
        for (List<Session> cinemaSessions : movieSessions) {
            LocalDate date = null;
            CinemaClass cinemaClass = null;
            System.out.println(THICK_DIVIDER);
            Cinema cinema = cinemaSessions.get(0).getCinema();      
            System.out.print(cinema + " (" + cinema.getCinemaCode() + ")");
            int rowCount = 0;
            for (Session session : cinemaSessions) {
                LocalDateTime dateTime = session.getDateTime();
                LocalDate sessionDate = dateTime.toLocalDate();
                CinemaClass sessionClass = session.getCinemaClass();
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
                    System.out.print("(3D) ");
                else
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
        String cinemaCode = InputController.getString("Enter cinema code, e.g. for JCube (SJC), SJC is the cinema code: ");
        String[] sessionInfo = getSessionInfo();
        System.out.println();
        try {
            if (!SessionController.viewSeating(movieSessions, cinemaCode, sessionInfo[0], sessionInfo[1].toUpperCase(), sessionInfo[2]))
                System.out.println(NO_SESSION + " " + RETURN_HOME);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date or time format. " + RETURN_HOME);
        } catch (IllegalArgumentException e) {
            System.out.print("Invalid cinema class. Valid cinema classes: ");
            for (CinemaClass cc : CinemaClass.values())
                System.out.print(cc + ", ");
            System.out.println("\n" + RETURN_HOME);
        }
    }

    public static void view(Cinema cinema, boolean admin) { // view sessions for a particular cinema
        List<Session> sessions = cinema.getSessions();
        LocalDate date = null;
        CinemaClass cinemaClass = null;
        Movie movie = null;
        int rowCount = 0;
        boolean hasSession = false;
        for (Session session: sessions) {
            MovieStatus movieStatus = session.getMovie().getShowingStatus();
            if (movieStatus.equals(MovieStatus.COMING_SOON) || movieStatus.equals(MovieStatus.END_OF_SHOWING))
                continue;
            hasSession = true;
            LocalDateTime dateTime = session.getDateTime();
            LocalDate sessionDate = dateTime.toLocalDate();
            CinemaClass sessionClass = session.getCinemaClass();
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
        if (!hasSession) {
            System.out.println("No showtimes available for this cinema.");
            return;
        }
        System.out.println(THIN_DIVIDER);
        try {
            if (!admin)
                movieGoerOptions(cinema);
            else
                adminOptions(cinema);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date or time format. " + RETURN_HOME);
        } catch (IllegalArgumentException e) {
            System.out.print("Invalid input. " + RETURN_HOME);
        }
    }   

    private static void movieGoerOptions(Cinema cinema) {
        if (!bookTickets())
            return;
        String sessionMovie = InputController.getString("Enter movie title: ");
        String[] sessionInfo = getSessionInfo();
        System.out.println();
        if (!SessionController.viewSeating(cinema, sessionMovie, sessionInfo[0], sessionInfo[1].toUpperCase(), sessionInfo[2]))
            System.out.println(NO_SESSION + " " + RETURN_HOME);
    }

    private static void adminOptions(Cinema cinema) {
        System.out.println("\n1. Create showtime | 2. Update showtime | 3. Remove showtime | 4. Return to admin menu\n");
        int choice = InputController.getInt(1, 4, "Enter your option: ");
        if (choice == 4)
            return;
        System.out.println("Enter showtime details.");
        String sessionMovie = InputController.getString("Enter movie title: ");
        String[] sessionInfo = getSessionInfo();
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
    }

    private static boolean bookTickets() {
        System.out.println("1. Book tickets | 2. Return to homepage");
        int choice = InputController.getInt(1, 2, "Select an option: ");
        System.out.println();
        return choice == 1;
    }

    private static String[] getSessionInfo() {
        String date = InputController.getNumericString("Enter date in ddMMyy format, e.g. 251022: ", 6, 6);
        String cinemaClass = InputController.getString("Enter cinemaClass(non case-sensitive, please input '_' for spaces), e.g. gold_class_express: ");
        String time = InputController.getNumericString("Enter time in HHmm format, e.g. 1620: ", 4, 4);
        return new String[]{date, cinemaClass, time};
    }
}

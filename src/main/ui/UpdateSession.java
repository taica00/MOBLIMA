package main.ui;

import java.time.format.DateTimeParseException;

import main.controllers.InputController;
import main.controllers.SessionController;
import main.models.CinemaClass;
import main.models.Session;

/**
 * This class provides the UI for an admin to update session details.
 * @author Tai Chen An
 * @version 1.0 
 * @since 2022-11-08 
 */

public class UpdateSession extends UI {
    
    /**
     * Displays the given session details and gets user input for choice of configuration.
     * @param session session to be updated.
     */
    public static void view(Session session) {
        String choice = "";
        while (true) {
            System.out.println("\n" + session + "\n");
            choice = InputController.getString("Enter field(in lowercase) to update or '0' to return to admin menu: ");
            if (choice.equals("0"))
                return;
            switch(choice) {
                case "cinema": 
                    String cinema = InputController.getString("Enter cinema location, e.g Funan: ");
                    if (SessionController.updateCinema(session, cinema))
                        System.out.println("Successfully updated cinema.");
                    else
                        System.out.println("Cinema not found. Cinema not updated.");
                    break;
                case "movie": 
                    String movie = InputController.getString("Enter title of movie, e.g Black Adam: ");
                    if (SessionController.updateMovie(session, movie))
                        System.out.println("Successfully updated movie.");
                    else
                        System.out.println("Movie not found. Movie not updated.");
                    break;
                case "date": 
                    String date = InputController.getNumericString("Enter date in ddMMyy format: ", 6, 6);
                    try {
                        SessionController.updateDate(session, date);  
                        System.out.println("Successfully updated session date.");
                    } catch (DateTimeParseException e) {
                        System.out.println("Invalid date format. Date not updated.");
                    }
                    break;
                case "time": 
                    String time = InputController.getNumericString("Enter time in HHmm format: ", 4, 4);
                    try {
                        SessionController.updateDate(session, time);
                        System.out.println("Successfully updated session time.");
                    } catch (DateTimeParseException e) {
                        System.out.println("Invalid time format. Time not updated.");
                    }
                    break;
                case "cinema class":
                    String cinemaClass = InputController.getString("Enter cinema class, replace spaces with underscores: ");
                    try {
                        session.setCinemaClass(CinemaClass.valueOf(cinemaClass.toUpperCase()));
                        System.out.println("Successfully updated cinema class.");
                    } catch (IllegalArgumentException e) {
                        System.out.print("Invalid cinema class. Valid cinema classes: ");
                        for (CinemaClass cc : CinemaClass.values())
                            System.out.print(cc + ", ");
                        System.out.println("\nCinema class not updated.");
                    }
                    break;
                case "3D Screening": 
                    boolean is3D = InputController.getBoolean("Set this session as 3D?", 'Y', 'N');
                    session.set3D(is3D);
                    System.out.println("Successfully updated 3D screening settings.");
                    break;
                default: System.out.println("Invalid input. Please try again."); 
            }
        } 
    }
}

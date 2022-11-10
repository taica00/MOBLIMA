package main.ui;

import java.util.List;

import main.controllers.CineplexController;
import main.controllers.InputController;
import main.models.Cinema;
import main.models.Cineplex;

/**
 * This class provides the UI to display the list of cinemas in the database.
 * @author Tai Chen An
 * @version 1.1 
 * @since 2022-11-09
 */

public class CineplexList extends UI {

    /**
     * Retrieves all cineplexes from {@link CineplexController} and displays them.
     * Movie-goer chooses a cineplex to view showtimes.
     * Admin chooses a cineplex to view cinemas.
     * @param admin if user is admin or movie-goer
     */
    public static void view(boolean admin) {
        List<Cineplex> cineplexes = CineplexController.getCineplexes();
        System.out.println("Cineplex list: \n");
        int i = 1;
        for (Cineplex cineplex : cineplexes) {
            System.out.print((i++) + ". ");
            System.out.println(cineplex.getLocation());
        }
        System.out.println();
        if (admin) {
            int choice = InputController.getInt(1, cineplexes.size(), "Select index of cineplex to view cinemas: ");
            viewCinemas(cineplexes.get(choice-1));
        }
        else {
            int choice = InputController.getInt(1, cineplexes.size(), "Select index of cineplex to view showtimes: ");
            ShowTimes.view(cineplexes.get(choice-1));
        }
    }

    /**
     * Displays all cinemas of the cineplex to an admin.
     * Admin chooses a cinema to view its sessions.
     * @param cineplex cineplex to display cinemas
     */
    private static void viewCinemas(Cineplex cineplex) {
        int i = 1;
        List<Cinema> cinemas = cineplex.getCinemas();
        for (Cinema cinema : cinemas) {
            System.out.print((i++) + ". ");
            System.out.println(cinema.getCinemaClass() + " " + cinema.getCinemaNumber());
        }
        System.out.println();
        int choice = InputController.getInt(1, cinemas.size(), "Select index of cinema to view sessions: ");
        ShowTimes.view(cinemas.get(choice-1));
    }
}

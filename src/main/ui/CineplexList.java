package main.ui;

import java.util.List;

import main.controllers.CineplexController;
import main.controllers.InputController;
import main.models.Cineplex;

/**
 * This class provides the UI to display the list of cinemas in the database.
 * @author Tai Chen An
 * @version 1.0 
 * @since 2022-11-08 
 */

public class CineplexList extends UI {

    /**
     * Displays the given list of cineplexes
     * User chooses a cineplex to view showtimes
     * @param cinemas list of cinemas to display.
     * @param admin if user is admin or movie-goer.
     */
    public static void view(List<Cineplex> cineplexes, boolean admin) {
        System.out.println("Cineplex list: \n");
        int i = 1;
        for (Cineplex cineplex : cineplexes) {
            System.out.print((i++) + ". ");
            System.out.println(cineplex.getLocation());
        }
        System.out.println();
        int choice  = InputController.getInt(1, cineplexes.size(), "Select index of cinema to view showtimes: ");
        CineplexController.viewShowTimes(cineplexes.get(choice-1), admin);
    }
}

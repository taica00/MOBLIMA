package main.ui;

import java.util.List;

import main.controllers.InputController;
import main.models.Cinema;

/**
 * This class provides the UI to display the list of cinemas in the database.
 * @author Tai Chen An
 * @version 1.0 
 * @since 2022-11-08 
 */

public class CinemaList extends UI {

    /**
     * Displays the given list of cinemas.
     * User chooses a cinema to view showtimes. 
     * @param cinemas list of cinemas to display.
     * @param admin if user is admin or movie-goer.
     */
    public static void view(List<Cinema> cinemas, boolean admin) {
        System.out.println("Cinema list: \n");
        int i = 1;
        for (Cinema cinema : cinemas) {
            System.out.print((i++) + ". ");
            System.out.println(cinema);
        }
        System.out.println();
        int choice  = InputController.getInt(1, cinemas.size(), "Select index of cinema to view showtimes: ");
        ShowTimes.view(cinemas.get(choice-1), admin);
    }
}

package main.ui;

import main.controllers.InputController;
import main.controllers.MovieController;

/**
 * This class provides the UI for an admin to add a movie to the database.
 * @author Tai Chen An
 * @version 1.0 
 * @since 2022-11-08 
 */

public class AddMovie extends UI {
    /**
     * Takes input from the user for all movie fields.
     * Calls {@link MovieController} to add a movie with the inputs.
     */
    public static void view() {
        String title = InputController.getString("Enter movie title: ");
        String rating = InputController.getString("Enter rating [G, PG, PG13, NC16, M18, R21, NA]: ");
        String showingStatus = InputController.getString("Enter showing status [COMING_SOON, PREVIEW, NOW_SHOWING, END_OF_SHOWING]: ");
        String director = InputController.getString("Enter director: ");
        String casts = InputController.getString("Enter casts: ");
        String sypnopsis = InputController.getString("Enter sypnosis: ");
        try {
            MovieController.addMovie(title, rating, showingStatus, director, casts, sypnopsis);
            System.out.println("Movie added.");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid input. Movie not added. Returning to admin menu.");
        }
    }
}

package main.ui;

import main.controllers.InputController;
import main.models.Movie;

/**
 * This class provides the UI for an admin to update movie details.
 * @author Tai Chen An
 * @version 1.0 
 * @since 2022-11-08 
 */

public class UpdateMovie extends UI {
    
    /**
     * Displays the given movie details and gets user input on choice of configuration. 
     * @param movie movie to be updated.
     */
    public static void view(Movie movie) {
        String choice = "";
        while (true) {
            System.out.println("\n" + movie + "\n");
            choice = InputController.getString("Enter field(in lowercase) to update or '0' to return to admin menu: ");
            if (choice.equals("0"))
                return;
            String input = InputController.getString("Enter new " + choice + ": ");
            try {
                switch(choice) {
                    case "title": movie.setTitle(input); break;
                    case "rating": movie.setRating(input); break;
                    case "showing status": movie.setShowingStatus(input); break;
                    case "director": movie.setDirector(input); break;
                    case "cast": movie.setCasts(input); break;
                    case "sypnopsis": movie.setSypnopsis(input); break;
                    default: System.out.println("Invalid input. Please try again."); continue;
                }
                System.out.println("Field successfully updated.");
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input. Field not updated.");
            }
        } 
    }
}

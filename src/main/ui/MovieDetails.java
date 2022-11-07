package main.ui;

import main.controllers.CineplexController;
import main.controllers.InputController;
import main.models.Movie;

/**
 * This class provides the UI to display details of a movie.
 * @author Tai Chen An
 * @version 1.0 
 * @since 2022-11-08 
 */

public class MovieDetails extends UI {
    
    /**
     * Displays the details of the given movie. 
     * The user can choose to view the sessions or reviews of the given movie.
     * @param movie
     */
    public static void view(Movie movie) {
        System.out.println(movie);
        int choices = 0;
        System.out.print("1. Return to homepage ");
        switch (movie.getShowingStatus()) {
            case COMING_SOON: 
                choices = 1;
                break;
            case END_OF_SHOWING: 
                System.out.print("| 2. View reviews "); 
                choices = 2;
                break;
            case NOW_SHOWING: 
            case PREVIEW: 
                System.out.print("| 2. View reviews | 3. View showtimes"); 
                choices = 3;
                break;
        }
        System.out.println();
        int choice = InputController.getInt(1, choices, "Select an option: ");
        System.out.println();
        switch(choice) {
            case 1: return;
            case 2: ReviewsList.view(movie); break;
            case 3: CineplexController.viewShowTimes(movie); break; 
            default: System.out.println("Something weird happened");
        }
    }
}

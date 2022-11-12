package main.ui;

import java.util.List;

import main.controllers.InputController;
import main.controllers.MovieController;
import main.models.Movie;

/**
 * This class provides the UI to display a list of movies.
 * There are different functions between admin and movie-goer users.
 * @author Tai Chen An
 * @version 1.0 
 * @since 2022-11-08 
 */

public class MovieList extends UI {
    
    /** 
     * Displays the given list of movies.
     * Displays different options depending on whether user is admin or movie-goer.
     * @param movies list of movies.
     * @param admin if user is admin or movie-goer.
     */
    public static void view(List<Movie> movies, boolean admin) {
        System.out.println("List of movies:");
        int i = 1;
        for (Movie movie : movies) 
            System.out.println((i++) + ". " + movie.getTitle() + " (" + movie.getRating() + ")");
        if (admin) 
            adminOptions(movies);
        else 
            movieGoerOptions(movies);
    }
    
    /**
     * Gets movie-goer to choose a movie to view its details.
     * Passes the chosen movie to {@link MovieDetails}. 
     * @param movies list of movies displayed.
     */
    protected static void movieGoerOptions(List<Movie> movies) {
        int choice = InputController.getInt(0, movies.size(), "Enter list number of movie to view details or '0' to return to homepage: ");
        System.out.println();
        if (choice == 0)
            return;
        MovieDetails.view(movies.get(choice-1));
    }

    /**
     * Displays the options for an admin to configure the movie database. 
     * @param movies list of movies displayed.
     */
    private static void adminOptions(List<Movie> movies) {
        System.out.println("\n1. Create listing | 2. Update listing | 3. Remove listing | 4. Return to admin menu\n");
        int choice = InputController.getInt(1, 4, "Enter your option: ");
        int listIndex = -1;
        switch(choice) {
            case 1: AddMovie.view(); break;
            case 2: 
                listIndex = InputController.getInt(1, movies.size(), "Enter list number of movie to edit: "); 
                UpdateMovie.view(movies.get(listIndex-1));
                break;
            case 3: 
                listIndex = InputController.getInt(1, movies.size(), "Enter list number of movie to remove: "); 
                MovieController.removeMovie(listIndex-1);
                System.out.println("Movie listing removed. Returning to admin menu.");
                break;
            case 4: break;
            default: System.out.println("Something weird happened.");
        }
    }
}

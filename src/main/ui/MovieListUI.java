package main.ui;

import java.util.List;

import main.controllers.InputController;
import main.controllers.MovieController;
import main.models.Movie;

public class MovieListUI extends UI {

    public static void view(List<Movie> movies, boolean admin) {
        System.out.println("List of movies:");
        int i = 1;
        for (Movie movie : movies) 
            System.out.println((i++) + ". " + movie.getTitle());
        if (admin) 
            adminOptions(movies);
        else 
            movieGoerOptions(movies);
    }

    private static void movieGoerOptions(List<Movie> movies) {
        int choice = InputController.getInt(0, movies.size(), "Enter list number of movie to view details or '0' to return to homepage: ");
        System.out.println();
        if (choice == 0)
            return;
        MovieDetailsUI.view(movies.get(choice-1));
    }

    private static void adminOptions(List<Movie> movies) {
        System.out.println("\n1. Create listing | 2. Update listing | 3. Remove listing\n");
        int choice = InputController.getInt(1, 3, "Enter your option: ");
        int listIndex = -1;
        switch(choice) {
            case 1: AddMovieUI.view(); break;
            case 2: 
                listIndex = InputController.getInt(1, movies.size(), "Enter list number of movie to edit: "); 
                UpdateMovieUI.view(movies.get(listIndex-1));
                break;
            case 3: 
                listIndex = InputController.getInt(1, movies.size(), "Enter list number of movie to remove: "); 
                MovieController.removeMovie(listIndex-1);
                System.out.println("Movie listing removed. Returning to admin menu.");
                break;
            default: System.out.println("Something weird happened.");
        }
    }

    
}

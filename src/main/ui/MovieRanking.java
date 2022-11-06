package main.ui;

import java.util.List;

import main.controllers.InputController;
import main.controllers.MovieController;
import main.controllers.TransactionsController;
import main.models.Movie;

public class MovieRanking extends MovieList {
    
    /** 
     * @param movies
     * @param ticketSales
     * @param admin
     */
    public static void view(List<Movie> movies, boolean ticketSales, boolean admin) {
        int i = 1;
        for (Movie movie : movies) {
            System.out.print((i++) + ". " + movie.getTitle());
            if (admin) {
                System.out.print(" (");
                if (ticketSales)
                    System.out.print(TransactionsController.getTicketSales(movie) + ")");
                else
                    System.out.print(movie.getReviewerRating() + ")");
            }
            System.out.println();
        }
        if (admin)
            adminOptions(movies);
        else
            movieGoerOptions(movies);
    }

    
    /** 
     * @param movies
     */
    private static void adminOptions(List<Movie> movies) {
        System.out.println("\n1. Update listing | 2. Remove listing | 3. Return to admin menu\n");
        int choice = InputController.getInt(1, 3, "Enter your option: ");
        int listIndex = -1;
        switch(choice) {
            case 1: 
                listIndex = InputController.getInt(1, movies.size(), "Enter list number of movie to edit: "); 
                UpdateMovie.view(movies.get(listIndex-1));
                break;
            case 2: 
                listIndex = InputController.getInt(1, movies.size(), "Enter list number of movie to remove: "); 
                MovieController.removeMovie(listIndex-1);
                System.out.println("Movie listing removed. Returning to admin menu.");
                break;
            case 3: break;
            default: System.out.println("Something weird happened.");
        }
    }
}

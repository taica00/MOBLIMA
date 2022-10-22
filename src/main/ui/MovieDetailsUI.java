package main.ui;

import main.controllers.CineplexController;
import main.controllers.InputController;
import main.controllers.ReviewsController;
import main.models.Movie;

public class MovieDetailsUI extends UI {

    public static void view(Movie movie) {
        System.out.println(movie);
        int choices = 0;
        System.out.print("1. Return to homepage ");
        switch (movie.getShowingStatus()) {
            case COMINGSOON: 
                choices = 1;
                break;
            case ENDOFSHOWING: 
                System.out.print("| 2. View reviews "); 
                choices = 2;
                break;
            case NOWSHOWING: 
            case PREVIEW: 
                System.out.print("| 2. View reviews | 3. View showtimes"); 
                choices = 3;
                break;
        }
        System.out.println();
        int choice = InputController.getInt(1, choices, "Enter your choice: ");
        switch(choice) {
            case 1: return;
            case 2: ReviewsController.viewReviews(movie); break;
            case 3: CineplexController.viewShowTimes(movie); break; 
            default: System.out.println("Something weird happened");
        }
    }
}

package main.ui;

import main.controllers.CineplexController;
import main.controllers.InputController;
import main.controllers.ReviewsController;
import main.models.Movie;

public class MovieDetailsUI {
    private static Movie movie;
    public static void main(String[] args) {
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
        int choice = getChoice(choices);
        switch(choice) {
            case 2: ReviewsController.viewReviews(movie);
            case 3: CineplexController.viewShowTimes(movie); break; 
        }
    }

    public static void setMovie(Movie m) {
        movie = m;
    }

    private static int getChoice(int choices) {
        int choice = -1;
        boolean validInput = false;
        while (!validInput) {
            InputController.clear();
            System.out.print("Enter selection: ");
            choice = InputController.getInt();
            if (choice >= 1 && choice <= choices)
                validInput = true;
            else
                System.out.println("Invalid selection. Please try again\n");
        }
        return choice;
    }
}

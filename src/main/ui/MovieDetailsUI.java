package main.ui;

import main.controllers.CineplexController;
import main.controllers.InputController;
import main.controllers.ReviewsController;
import main.models.Cineplex;
import main.models.Movie;

public class MovieDetailsUI {
    private static Movie movie;
    public static void main(String[] args) {
        int choice = -1;
        boolean validInput = false;
        System.out.println("1. View showtimes | 2. View reviews | 3. Return to homepage");
        while (!validInput) {
            InputController.clear();
            System.out.print("Enter selection: ");
            choice = InputController.getInt();
            if (choice >= 1 && choice <= 3)
                validInput = true;
            else
                System.out.println("Invalid selection. Please try again\n");
        }
        switch(choice) {
            case 1: CineplexController.viewShowTimes(movie); break;
            case 2: ReviewsController.viewReviews(movie); 
        }
    }

    public static void setMovie(Movie m) {
        movie = m;
    }
}

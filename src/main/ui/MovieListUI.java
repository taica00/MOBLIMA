package main.ui;

import main.controllers.InputController;
import main.controllers.MovieController;

public class MovieListUI {
    public static void main(String[] args) {
        boolean validInput = false;
        int choice = -1;
        while (!validInput) {    
            InputController.clear();
            System.out.print("Enter list number of movie to view details or '0' to return to homepage: ");
            choice = InputController.getInt();
            if (choice == 0)
                return;
            if (choice >= 1 && choice <= Integer.parseInt(args[0]))
                validInput = true;
            else
                System.out.println("Invalid selection. Please try again\n");
        }
        System.out.println();
        MovieController.viewMovieDetails(choice);
    }
}

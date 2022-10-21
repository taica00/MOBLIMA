package main.ui;

import main.controllers.InputController;
import main.controllers.MovieController;
import main.models.Movie;

public class MainMenuUI {
    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("*********** MOBLIMA ***********\n");
            System.out.println("1. List all movies.");
            System.out.println("2. Search for a movie.");
            System.out.println("3. View showtimes");
            System.out.println("4. View booking history");
            System.out.println("5. List Top 5 ranking movies by ticket sales");
            System.out.println("6. List Top 5 ranking movies by reviewers' ratings");
            System.out.println("7. Admin login");
            System.out.println("8. Exit");
            System.out.print("Select an option: ");
            choice  = InputController.getInt();
            System.out.println();
            
            switch(choice) {
                case 1: MovieController.listMovies(null); break;
                case 2: MovieController.listMovies(InputController.getString()); break;
                case 3: System.out.println("hi");
                case 4: System.out.println("hi");
                case 5: System.out.println("hi");
                case 6: System.out.println("hi");
                case 7: System.out.println("hi");
                case 8: break;
                default: System.out.println("Invalid selection. Please try again\n");
            }
            InputController.clear();
        } while (choice != 8);
    }
}

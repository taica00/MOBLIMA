package main.ui;

import main.controllers.CineplexController;
import main.controllers.InputController;
import main.controllers.MovieController;

public class MainMenu extends UI {
    public static void view() {
        int choice;
        do {
            System.out.println("\n********************** MOBLIMA **********************\n");
            System.out.println("1. List all movies.");
            System.out.println("2. Search for a movie.");
            System.out.println("3. View showtimes.");
            System.out.println("4. View booking history.");
            System.out.println("5. List Top 5 ranking movies by ticket sales.");
            System.out.println("6. List Top 5 ranking movies by reviewers' ratings.");
            System.out.println("7. Admin login.");
            System.out.println("8. Exit.");
            choice = InputController.getInt(1, 8, "Select an option: ");
            System.out.println();
            
            switch(choice) {
                case 1: MovieController.listMovies(false, false, false); break;
                case 2: MovieController.searchMovies(); break;
                case 3: CineplexController.displayCinemas(false); break;
                case 4: BookingHistory.view(); break;
                case 5: MovieController.listMovies(false, true, false); break;
                case 6: MovieController.listMovies(false, false, true); break; 
                case 7: AdminMenu.login(); break;
                case 8: break;
                default: System.out.println("Something weird happened");
            }
        } while (choice != 8);
    }
}

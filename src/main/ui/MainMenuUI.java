package main.ui;

import main.controllers.InputController;
import main.controllers.MovieController;
import main.controllers.TransactionsController;

public class MainMenuUI extends UI {
    public static void view() {
        int choice;
        do {
            System.out.println("\n********************** MOBLIMA **********************\n");
            System.out.println("1. List all movies.");
            System.out.println("2. Search for a movie.");
            System.out.println("3. View booking history");
            System.out.println("4. List Top 5 ranking movies by ticket sales");
            System.out.println("5. List Top 5 ranking movies by reviewers' ratings");
            System.out.println("6. Admin login");
            System.out.println("7. Exit");
            choice = InputController.getInt(1, 7, "Select an option: ");
            System.out.println();
            
            switch(choice) {
                case 1: MovieController.listMovies(); break;
                case 2: MovieController.searchMovies(); break;
                case 3: TransactionsController.viewTransactions(); break;
                case 4: break;
                case 5: break;
                case 6: break; 
                case 7: break;
                default: System.out.println("Something weird happened");
            }
        } while (choice != 8);
    }
}

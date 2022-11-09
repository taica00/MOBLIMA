package main.ui;

import main.controllers.InputController;
import main.controllers.MovieController;

/**
 * This class provides the UI to display the main menu of the application.
 * All functions of movie-goers are displayed here.
 * Admins can access the admin login through this menu.
 * @author Tai Chen An
 * @version 1.0 
 * @since 2022-11-08 
 */

public class MainMenu extends UI {
    /**
     * Displays the main menu of the application.
     */
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
                case 1: MovieController.listMovies(false); break;
                case 2: MovieController.searchMovies(); break;
                case 3: CineplexList.view(false); break;
                case 4: BookingHistory.view(); break;
                case 5: MovieController.rankMovies(true, false); break;
                case 6: MovieController.rankMovies(false, false); break; 
                case 7: AdminMenu.login(); break;
                case 8: System.out.println("Thank you for using MOBLIMA. Hope to see you again!"); break;
                default: System.out.println("Something weird happened");
            }
        } while (choice != 8);
    }
}

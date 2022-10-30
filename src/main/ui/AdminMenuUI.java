package main.ui;

import main.controllers.AdminController;
import main.controllers.CineplexController;
import main.controllers.InputController;
import main.controllers.MovieController;
import main.models.Admin;

public class AdminMenuUI extends UI {
    public static void view(Admin admin) {
        int choice;
        do {
            System.out.println("\n******************** ADMIN MENU ********************\n");
            System.out.println("1. Configure movies listing.");
            System.out.println("2. Configure cinema showtimes.");
            System.out.println("3. Configure system settings.");
            System.out.println("4. List the Top 5 ranking by ticket sales.");
            System.out.println("5. List the Top 5 ranking by overall reviewers' ratings.");
            System.out.println("6. Return to homepage.\n");
            choice = InputController.getInt(1, 6, "Select an option: ");
            System.out.println();
            
            switch(choice) {
                case 1: MovieController.listMovies(true); break;
                case 2: CineplexController.displayCinemas(true); break;
                case 3: break;
                case 4: break;
                case 5: break;
                case 6: break;
                default: System.out.println("Something weird happened");
            }
        } while (choice != 6);
    }

    public static void login() {
        String userId = InputController.getString("Enter userId: ");
        Admin adminAccount = AdminController.searchUserId(userId);
        if (adminAccount == null) {
            System.out.println("Account does not exist. Returning to homepage.");
            return;
        }
        String password = InputController.getString("Enter password: ");
        if (!adminAccount.verifyPassword(password)) {
            System.out.println("Invalid password. Returning to homepage.");
            return;
        }
        view(adminAccount);
    }
}

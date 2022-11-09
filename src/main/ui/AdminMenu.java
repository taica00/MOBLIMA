package main.ui;

import main.controllers.AdminController;
import main.controllers.InputController;
import main.controllers.MovieController;
import main.controllers.PricingController;
import main.models.Admin;

/**
 * This class provides the UI to display the functions of an admin user.
 * An admin account is required to access these functions.
 * Admins can create more admin accounts.
 * @author Tai Chen An
 * @version 1.0 
 * @since 2022-11-08 
 */

public class AdminMenu extends UI {
    
    /**
     * Displays admin functions.
     * @param admin account of the admin
     */
    private static void view(Admin admin) {
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
                case 2: CineplexList.view(true);; break;
                case 3: systemSettings(admin); break;
                case 4: MovieController.rankMovies(true, true); break;
                case 5: MovieController.rankMovies(false, true); break;
                case 6: break;
                default: System.out.println("Something weird happened");
            }
        } while (choice != 6);
    }

    /**
     * Gets the user to login with their admin account.
     * A successful login is required to access the admin menu.
     */
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
    
    /**
     * Displays system settings 
     * @param admin account of admin.
     */
    private static void systemSettings(Admin admin) {
        System.out.println("\n******************** SYSTEM SETTINGS ********************\n");
        System.out.println("1. Configure ticket prices.");
        System.out.println("2. Configure holiday dates.");
        System.out.println("3. Change password.");
        System.out.println("4. Create new admin account.");
        System.out.println("5. Return to admin menu.\n");
        int choice = InputController.getInt(1, 5, "Select an option: ");
        System.out.println();
        
        switch(choice) {
            case 1: UpdatePrices.view(); break;
            case 2: PricingController.configureHolidays(); break;
            case 3: changePassword(admin); break;
            case 4: 
                String userId = InputController.getString("Enter userId: ");
                String password = InputController.getString("Enter password: ");
                AdminController.addAdmin(userId, password);
                System.out.println("Admin account successfully added. Returning to admin menu.");
                break;
            case 5: break;
            default: System.out.println("Something weird happened");
        }
    }

    /**
     * Changes the password for the given admin account. 
     * User has 3 attempts to correctly input the current password of the given admin account.
     * @param admin account of admin.
     */
    private static void changePassword(Admin admin) {
        int count = 0;
        while (true) {
            String curPassword = InputController.getString("Enter current password: ");
            if (admin.verifyPassword(curPassword))
                break;
            if (++count == 3) {
                System.out.println("Too many failed atttempts. Returning to admin menu.");
                return;
            }
            System.out.println("Wrong password. Please try again.");
        }
        String newPassword = InputController.getString("Enter new password: ");
        admin.setPassword(newPassword);
        System.out.println("Your password has been updated. Returning to admin menu.");
    }
}

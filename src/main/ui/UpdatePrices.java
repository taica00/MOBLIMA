package main.ui;

import main.controllers.InputController;
import main.models.CinemaClass;

/**
 * This class provides the UI for an admin to update ticket prices.
 * @author Tai Chen An
 * @version 1.0 
 * @since 2022-11-08 
 */

public class UpdatePrices extends UI {
    /**
     * Prompts user to enter cinema class.
     * If cinema class is valid, then prompts user for pricing category to update.
     */
    public static void view() {
        String cinemaClassString = InputController.getString("Enter cinema class (replace space with underscore): ");
        CinemaClass cinemaClass;
        try {
            cinemaClass = CinemaClass.valueOf(cinemaClassString.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.print("Invalid cinema class. Valid cinema classes: ");
            for (CinemaClass cc : CinemaClass.values())
                System.out.print(cc + ", ");
            System.out.println("\nReturning to admin menu.");
            return;
        }
        System.out.println("\nSelect pricing category to change: ");
        System.out.println("1. Senior Citizen (Mon-Fri before 6pm)");
        System.out.println("2. Student (Mon-Fri before 6pm)");
        System.out.println("3. Non-peak (Mon-Thurs)");
        System.out.println("4. Peak (Fri-Sun/PH)");
        int choice = InputController.getInt(1, 4, "\nSelect an option: ");
        double price = InputController.getDouble("Enter new price: ");
        cinemaClass.setPrice(choice-1, price);
        System.out.println("Pricing updated. Returning to admin menu.");
    }
}

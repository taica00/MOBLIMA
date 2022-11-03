package main.ui;

import main.controllers.InputController;
import main.models.CinemaClass;

public class UpdatePrices extends UI {
    public static void view() {
        String cinemaClassString = InputController.getString("Enter cinema class (replace space with underscore): ");
        CinemaClass cinemaClass = CinemaClass.valueOf(cinemaClassString.toUpperCase());
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

package main.ui;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Set;

import main.controllers.InputController;
import main.controllers.PricingController;

/**
 * This class provides the UI for an admin to configure holiday dates.
 * @author Tai Chen An
 * @version 1.0 
 * @since 2022-11-08 
 */

public class ConfigureHolidays extends UI {
    
    /**
     * Displays the options for configuring holiday dates. 
     * @param holidays set of holiday dates.
     */
    public static void view(Set<LocalDate> holidays) {
        System.out.println("1. Set a date as holiday.");
        System.out.println("2. Unset a holiday date.");
        System.out.println("3. Return to admin menu.");
        int choice = InputController.getInt(1, 3, "Enter your option: ");
        switch(choice) {
            case 1:
                String date = InputController.getString("Enter date in ddMMyy format: ");
                try {    
                    PricingController.addHolidayDate(date);
                    System.out.println("Successfully set date as holiday.");
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid date format. Date not set as holiday.");
                }
                break;
            case 2: removeHolidayDate(holidays.toArray(new LocalDate[0])); break;
            case 3: return;
            default: System.out.println("Something weird happened.");
        }
    }

    /**
     * Prints out all holiday dates and gets the user to choose one to remove. 
     * @param holidays array of holiday dates.
     */
    private static void removeHolidayDate(LocalDate[] holidays) {
        int i = 1;
        for (LocalDate date : holidays)
            System.out.println((i++) + ". " + date);
        int index = InputController.getInt(1, holidays.length, "Enter index of date to unset as holiday: ");
        PricingController.removeHolidayDate(holidays[index-1]);
        System.out.println("Successfully unset date as holiday. Returning to admin menu.");
    }
}

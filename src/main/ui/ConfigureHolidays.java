package main.ui;

import java.time.LocalDate;
import java.util.Set;

import main.controllers.InputController;
import main.controllers.PricingController;

public class ConfigureHolidays extends UI {
    public static void view(Set<LocalDate> holidays) {
        System.out.println("1. Add holiday date");
        System.out.println("2. Remove holiday date");
        System.out.println("3. Return to admin menu");
        int choice = InputController.getInt(1, 3, "Enter your option: ");
        switch(choice) {
            case 1:
                String date = InputController.getString("Enter date in ddMMyy format: ");
                PricingController.addHolidayDate(date);
                break;
            case 2: removeHolidayDate(holidays.toArray(new LocalDate[0])); break;
            case 3: return;
            default: System.out.println("Something weird happened.");
        }
    }

    private static void removeHolidayDate(LocalDate[] holidays) {
        int i = 1;
        for (LocalDate date : holidays)
            System.out.println((i++) + ". " + date);
        int index = InputController.getInt(1, holidays.length, "Enter index of holiday date to remove: ");
        PricingController.removeHolidayDate(holidays[index-1]);
        System.out.println("Holiday date successfully removed. Returning to admin menu.");
    }
}

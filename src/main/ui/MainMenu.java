package main.ui;

import main.controllers.InputController;

public class MainMenu {
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
            switch(choice) {
                case 1: System.out.println("hi");
                case 2: System.out.println("hi");
                case 3: System.out.println("hi");
                case 4: System.out.println("hi");
                case 5: System.out.println("hi");
                case 6: System.out.println("hi");
                case 7: System.out.println("hi");
                case 8: break;
                default: System.out.println("Invalid selection");
            }
        } while (choice != 8);
    }
}

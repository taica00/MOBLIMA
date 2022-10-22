package main.controllers;

import java.util.Scanner;

public class InputController extends Controller {
    private static Scanner scan = new Scanner(System.in);

    public static int getInt(int start, int end, String message) {
        boolean validInput = false;
        int choice = -1;
        while (!validInput) {    
            System.out.print(message);
            if (scan.hasNextInt())
                choice = scan.nextInt();
            else
                clear();
            if (choice >= start && choice <= end)
                validInput = true;
            else
                System.out.println("Invalid input. Please try again\n");
        }
        return choice;
    }

    public static String getString(String message) {
        clear();
        System.out.println(message);
        return scan.nextLine();
    }

    private static void clear() {
        scan.nextLine();
    }

    


}

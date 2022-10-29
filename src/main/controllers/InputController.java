package main.controllers;

import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

import main.models.MovieGoer;

public class InputController extends Controller {
    private static Scanner scan = new Scanner(System.in);

    public static int getInt(int start, int end, String message) {
        while (true) {
            clear();    
            System.out.print(message);
            if (scan.hasNextInt()) {
                int choice = scan.nextInt();
                if (choice >= start && choice <= end)
                    return choice;
            }
            System.out.println("Invalid input. Please try again\n");
        }
    }

    public static String getString(String message) {
        clear();
        System.out.print(message);
        return scan.nextLine();
    }

    public static String getNumericString(String message, int lo, int hi) {
        while (true) {
            clear();
            System.out.print(message);
            String input = scan.nextLine();
            if (StringUtils.isNumeric(input) || (input.length() >= lo && input.length() <= hi))
                return input;
            System.out.println("Invalid input. Please try again");
        }
    }

    public static boolean getBoolean(String message, char T, char F) {
        while (true) {
            clear();
            System.out.print(message);
            String choice = scan.next();
            if (choice.length() == 1) {
                if (choice.charAt(0) == T)
                    return true;
                else if (choice.charAt(0) == F)
                    return false;
            }
            System.out.println("Invalid input. Please try again");
        }
    }

    public static boolean confirmIdentity(MovieGoer movieGoer) {
        while (true) {
            String name = InputController.getString("Enter name: ");
            String mobile = InputController.getString("Enter mobile number: ");
            if (name.equals(movieGoer.getName()) && mobile.equals(movieGoer.getMobile()))
                return true;
            else {
                System.out.println("Invalid particulars.");
                if (!getBoolean("Try again? (Y/N)", 'Y', 'N'))
                    return false;
            }
        }
    }

    private static void clear() {
        scan = new Scanner(System.in);
    }

    


}

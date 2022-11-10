package main.controllers;

import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

import main.models.MovieGoer;

/**
 * This class takes inputs from the user when the main application requires so.
 * There are different methods for different variable types.
 * @author Tai Chen An
 * @version 1.0 
 * @since 2022-11-08 
 */

public class InputController extends Controller {
    private static Scanner scan = new Scanner(System.in);
    private static final String INVALID_INPUT = "Invalid input. Please try again\n";

    /**
     * Prints the given message and takes an integer input from the user.
     * Input must be between(inclusive) the given start and end fields.
     * Continues to prompt the user for a input if previous input is invaid. 
     * @param start smallest valid integer input.
     * @param end largest valid integer input
     * @param message message to display to user.
     * @return int input from user.
     */
    public static int getInt(int start, int end, String message) {
        while (true) {
            clear();    
            System.out.print(message);
            if (scan.hasNextInt()) {
                int choice = scan.nextInt();
                if (choice >= start && choice <= end)
                    return choice;
            }
            System.out.println(INVALID_INPUT);
        }
    }

    /**
     * Prints the given message and takes a string input from the user.
     * @param message message to display to user.
     * @return String input from user
     */
    public static String getString(String message) {
        clear();
        System.out.print(message);
        return scan.nextLine();
    }

    /**
     * Prints the given message and takes a numeric string input from the user.
     * Length of input must be between(inclusive) the given lo and hi fields. 
     * Continues to prompt the user for a input if previous input is invaid. 
     * @param message message to display to user.
     * @param lo shortest valid length of string.
     * @param hi longest valid length of string.
     * @return numeric string input from user.
     */
    public static String getNumericString(String message, int lo, int hi) {
        while (true) {
            clear();
            System.out.print(message);
            String input = scan.nextLine();
            if (StringUtils.isNumeric(input) && input.length() >= lo && input.length() <= hi)
                return input;
            System.out.println(INVALID_INPUT);
        }
    }

    /**
     * Prints the given message and takes a character input from the user.
     * Input must be either of T or F.
     * Continues to prompt the user for a input if previous input is invaid.  
     * @param message message to display to user.
     * @param T character representing the true value.
     * @param F character representing the false value.
     * @return user input as a boolean value.
     */
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
            System.out.println(INVALID_INPUT);
        }
    }

    /**
     * Prints the given message and takes a double input from the user.
     * Continues to prompt the user for a input if previous input is not a double. 
     * @param message message to display to user.
     * @return double input from user.
     */
    public static double getDouble(String message) {
        while (true) {
            clear();
            System.out.print(message);
            Double input = scan.nextDouble();
            if (input < 0)
                System.out.println(INVALID_INPUT);
            return input;
        }
    }

    /**
     * Takes inputs for name and mobile number from the user.
     * Verifies the input with the given movieGoer. 
     * Continues to prompt the user for inputs if previous input is invalid.
     * @param movieGoer movie-goer to verify identity.
     * @return true if user input matches the given movieGoer details.
     */
    public static boolean confirmIdentity(MovieGoer movieGoer) {
        while (true) {
            String name = InputController.getString("Enter name: ");
            String mobile = InputController.getString("Enter mobile number: ");
            if (name.equals(movieGoer.getName()) && mobile.equals(movieGoer.getMobile()))
                return true;
            else {
                System.out.println("Invalid particulars.");
                if (!getBoolean("Try again? (Y/N) ", 'Y', 'N'))
                    return false;
            }
        }
    }

    /**
     * Reinitialises the Scanner object.
     */
    private static void clear() {
        scan = new Scanner(System.in);
    }
}

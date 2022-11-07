package main.ui;

import main.controllers.InputController;
import main.controllers.TransactionsController;
import main.models.MovieGoer;
import main.models.Transaction;

/**
 * This class provides the UI to display the booking history of a movie-goer.
 * @author Tai Chen An
 * @version 1.0 
 * @since 2022-11-08 
 */

public class BookingHistory extends UI {
    /**
     * Displays the booking history of a movie-goer.
     * User enters email address and the email address is searched for past transactions.
     * If past transactions are found, user is then prompted to verify their personal information 
     * before the booking history is displayed.
     */
    public static void view() {
        String email = InputController.getString("Enter email address: ");
        MovieGoer movieGoer = TransactionsController.getMovieGoer(email);
        if (movieGoer == null) {
            System.out.println("No transactions found. Returning to homepage.");
            return;
        }
        System.out.println("Past transactions found. Please confirm your identity.");
        if (!InputController.confirmIdentity(movieGoer)) {
            System.out.println("Returning to homepage.");
            return;
        }
        System.out.println("\nBOOKING HISTORY:\n");
        for (Transaction transaction : movieGoer.getTransactions())
            System.out.println(transaction);
        InputController.getString("Enter any key to return to homepage.\n");
    }
}

package main.ui;

import java.util.List;

import main.controllers.InputController;
import main.controllers.TransactionsController;
import main.models.MovieGoer;
import main.models.Transaction;

public class BookingHistoryUI extends UI {
    public static void view() {
        String email = InputController.getString("Enter email address: ");
        MovieGoer movieGoer = TransactionsController.getMovieGoer(email);
        if (movieGoer == null) {
            System.out.println("No transactions found. Returning to homepage.");
            return;
        }
        if (!InputController.confirmIdentity(movieGoer)) {
            System.out.println("Returning to homepage.");
            return;
        }
        System.out.println("BOOKING HISTORY:");
        for (Transaction transaction : movieGoer.getTransactions())
            System.out.println(transaction);
    }
}

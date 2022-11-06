package main.ui;

import java.util.List;

import main.controllers.InputController;
import main.controllers.PricingController;
import main.controllers.SessionController;
import main.controllers.TransactionsController;
import main.models.MovieGoer;
import main.models.Session;

public class TicketsPayment extends UI {
    
    /** 
     * @param session
     * @param bookedSeats
     */
    public static void view(Session session, List<String> bookedSeats) {
        // print out booked seats
        System.out.print("Seat(s) booked: ");
        for (String seat : bookedSeats)   
            System.out.print(seat + " ");
        System.out.println();

        int seniors = 0;
        int students = 0;
        int numTickets = bookedSeats.size();
        // if showtime eligible for student/senior price, get no. of student/seniors
        if (PricingController.eligibleForConcession(session.getDateTime())) { 
            seniors = InputController.getInt(0, numTickets, "Enter number of senior citizens: ");
            students = InputController.getInt(0, numTickets-seniors, "Enter number of students: ");
        }
        // print out tickets price
        System.out.println("\n" + session.getCinemaClass());
        double price = PricingController.getTicketsPrice(session, numTickets, seniors, students);
        System.out.printf("Grand Total: $%.2f%n%n", price);
        // get user email, name, phone number
        String email = InputController.getString("Enter email address: ");
        MovieGoer movieGoer = TransactionsController.getMovieGoer(email); // use email to identify movie goer
        if (movieGoer != null) {  // moviegoer has booked movie(s) before
            System.out.println("We have records of your past transactions. Please confirm your identity.");
            if (!InputController.confirmIdentity(movieGoer)) {  // unable to confirm identity, booking unsuccessful
                SessionController.undoBooking(session, bookedSeats);  // mark seats back as available
                System.out.println("Returning to homepage.");
                return;
            }
            String message = "Would you like to use your existing payment method " + movieGoer.getMaskedCardNumber() + "? (Y/N) ";
            if (!InputController.getBoolean(message, 'Y', 'N'))
                movieGoer.setCardNumber(InputController.getNumericString("Enter card number: ", 8, 19)); // use new payment method
        }
        else { // moviegoer is a first timer
            String name = InputController.getString("Enter name: ");
            String mobile = InputController.getNumericString("Enter mobile number (8-15 digits): ", 8, 15);
            String cardNumber = InputController.getNumericString("Enter card number (8-19 digits): ", 8, 19);
            movieGoer = new MovieGoer(name, mobile, email, cardNumber);
            TransactionsController.addMovieGoer(movieGoer);
        }
        // generate transaction
        TransactionsController.makePayment(session, bookedSeats, movieGoer, price); 
        System.out.println("Payment successful. Returning to homepage.");
    }
}

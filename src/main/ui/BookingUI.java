package main.ui;

import java.time.LocalDateTime;
import java.util.List;

import main.controllers.InputController;
import main.controllers.PricingController;
import main.controllers.TransactionsController;
import main.models.MovieGoer;
import main.models.Session;

public class BookingUI extends UI {
    public static void view(Session session, List<String> bookedSeats) {
        System.out.print("Seat(s) booked: ");
        for (String seat : bookedSeats)
            System.out.print(seat + " ");
        System.out.println();
        int seniors = 0;
        int students = 0;
        int numTickets = bookedSeats.size();
        if (PricingController.eligibleForConcession(session.getDateTime())) {
            seniors = InputController.getInt(0, numTickets, "Enter number of senior citizens: ");
            students = InputController.getInt(0, numTickets-seniors, "Enter number of students: ");
        }
        double price = PricingController.getTicketsPrice(session, numTickets, seniors, students);
        System.out.printf("Grand Total: %.2f\n", price);
        String email = InputController.getString("Enter email address: ");
        MovieGoer movieGoer = TransactionsController.getMovieGoer(email);
        if (movieGoer != null) {
            System.out.println("We have records of your past transactions. Please confirm your identity.");
            String name = InputController.getString("Enter name: ");
            String mobile = InputController.getString("Enter mobile number: ");
        }
        String name = InputController.getString("Enter name: ");
        String mobile = InputController.getString("Enter mobile number: ");
        
        TransactionsController.makePayment(session, bookedSeats, name, mobile, email, price);
    }
}

package main.utils;

import java.util.Random;

import main.controllers.CineplexController;
import main.controllers.PricingController;
import main.controllers.TransactionsController;
import main.models.Cineplex;
import main.models.Seating;
import main.models.Session;

public class PopulateSessionSeats extends Populator {  
    public static void main(String[] args) {
        TransactionsController.loadTicketSales();
        PricingController.loadHolidays();
        CineplexController.loadCineplexes();
        for (Cineplex cineplex : CineplexController.getCineplexes()) {
            for (Session session : CineplexController.getSessions(cineplex)) {
                helper(session);
            }
        }
        CineplexController.saveCineplexes();
        TransactionsController.saveTicketSales();
    }

    private static void helper(Session session) {
        Random rd = new Random();
        Seating seating = session.getCinema().getSeating();
        int rows = seating.getNumRows();
        int cols = seating.getNumCols();
        int numSeatsToBook = rd.nextInt(cols * rows * 1/2);
        int actualSeatsBooked = 0;
        for (int i = 0; i < numSeatsToBook; i++) {
            char row = (char)('A' + rd.nextInt(rows));
            int col = rd.nextInt(cols) + 1;
            if (seating.bookSeat(row, col))
                actualSeatsBooked++;     
        }
        TransactionsController.addTicketSales(session.getMovie(), actualSeatsBooked);
    }
}

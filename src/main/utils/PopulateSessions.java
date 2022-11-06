package main.utils;

import java.util.List;
import java.util.Random;

import main.controllers.PricingController;
import main.controllers.TransactionsController;
import main.models.Cinema;
import main.models.Cineplex;
import main.models.Seating;
import main.models.Session;

public class PopulateSessions extends Populator {
    
    /** 
     * @param args
     */
    public static void main(String[] args) {
        TransactionsController.loadTicketSales();
        PricingController.loadHolidays();
        List<Cineplex> cineplexes = loadData("cineplexes.ser");
        for (Cineplex cineplex : cineplexes) {
            for (Cinema cinema : cineplex.getCinemas()) {
                for (Session session : cinema.getSessions())
                    helper(session);
            }
        }
        serialize(cineplexes, "cineplexes.ser");
        TransactionsController.saveTicketSales();
    }

    
    /** 
     * @param session
     */
    private static void helper(Session session) {
        Random rd = new Random();
        Seating seating = session.getSeating();
        int rows = seating.getNumRows();
        int cols = seating.getNumCols();
        int numSeatsToBook = rd.nextInt(cols * rows * 3/4);
        int actualSeatsBooked = 0;
        for (int i = 0; i < numSeatsToBook; i++) {
            char row = (char)('A' + rd.nextInt(rows));
            int col = rd.nextInt(cols-1) + 1;
            if (seating.bookSeat(row, col))
                actualSeatsBooked++;
        }
        TransactionsController.addTicketSales(session.getMovie(), actualSeatsBooked);
    }
}

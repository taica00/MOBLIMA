package main.controllers;

import java.util.List;
import java.util.Map;

import main.models.Movie;
import main.models.MovieGoer;
import main.models.Session;
import main.models.Transaction;

public class TransactionsController extends Controller {
    private static List<MovieGoer> movieGoers;
    private static Map<Movie, Integer> ticketSales;
    private static final String FILEPATH = "src/main/data/";

    
    /** 
     * @param session
     * @param bookedSeats
     * @param movieGoer
     * @param price
     */
    public static void makePayment(Session session, List<String> bookedSeats, MovieGoer movieGoer, double price) {
        Transaction transaction = new Transaction(session, bookedSeats, price);
        movieGoer.addTransaction(transaction);
        addTicketSales(session.getMovie(), bookedSeats.size());
    }
    
    /** 
     * @param email
     * @return MovieGoer
     */
    public static MovieGoer getMovieGoer(String email) {
        for (MovieGoer movieGoer : movieGoers) {
            if (movieGoer.getEmail().equals(email))
                return movieGoer;
        }
        return null;
    }

    
    /** 
     * @param movieGoer
     */
    public static void addMovieGoer(MovieGoer movieGoer) {
        movieGoers.add(movieGoer);
    }

    
    /** 
     * @param movie
     * @return int
     */
    public static int getTicketSales(Movie movie) {
        return ticketSales.getOrDefault(movie, 0);
    }

    
    /** 
     * @param movie
     * @param numTickets
     */
    public static void addTicketSales(Movie movie, int numTickets) {
        int sales = ticketSales.getOrDefault(movie, 0);
        sales += numTickets;
        ticketSales.put(movie, sales);
    }

    public static void loadMovieGoers() {
        movieGoers = loadData(FILEPATH + "moviegoers.ser");
    }

    public static void saveMovieGoers() {
        saveData(movieGoers, FILEPATH + "moviegoers.ser");
    }

    public static void loadTicketSales() {
        ticketSales = loadData(FILEPATH + "ticketsales.ser");
    }

    public static void saveTicketSales() {
        saveData(ticketSales, FILEPATH + "ticketsales.ser");
    }
}

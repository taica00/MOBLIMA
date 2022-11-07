package main.controllers;

import java.util.List;
import java.util.Map;

import main.models.Movie;
import main.models.MovieGoer;
import main.models.Session;
import main.models.Transaction;

/**
 * This class manages user actions pertaining to the sale of movie tickets.
 * A list of movie-goers and a map of movies to ticket sales is stored in this class.
 * @author Tai Chen An
 * @version 1.0 
 * @since 2022-11-08 
 */

public class TransactionsController extends Controller {
    /**
     * The list of movie-goers who have previously made transactions.
     */
    private static List<MovieGoer> movieGoers;

    /**
     * A map of movies to number of ticket sales.
     */
    private static Map<Movie, Integer> ticketSales;
    private static final String FILEPATH = "src/main/data/";

    /**
     * This method is called when movie-goer makes a successful transaction.
     * A new Transaction is created with the given fields and stored in the given movieGoer.
     * The number of ticket sales for the movie in the given session is incremented. 
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
     * Searches the list of movie-goers for one that matches the given email address. 
     * @param email
     * @return MovieGoer with the given email address.
     */
    public static MovieGoer getMovieGoer(String email) {
        for (MovieGoer movieGoer : movieGoers) {
            if (movieGoer.getEmail().equals(email))
                return movieGoer;
        }
        return null;
    }
 
    /**
     * Adds the given movie-goer to the stored list of movie-goers.
     * @param movieGoer
     */
    public static void addMovieGoer(MovieGoer movieGoer) {
        movieGoers.add(movieGoer);
    }

    /** 
     * @param movie
     * @return number of ticket sales for the given movie.
     */
    public static int getTicketSales(Movie movie) {
        return ticketSales.getOrDefault(movie, 0);
    }

    /**
     * Increments the number of ticket sales for the given movie by the given number of tickets. 
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

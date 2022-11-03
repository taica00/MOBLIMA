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

    public static void makePayment(Session session, List<String> bookedSeats, MovieGoer movieGoer, double price) {
        Transaction transaction = new Transaction(session, bookedSeats, price);
        movieGoer.addTransaction(transaction);
        addTicketSales(session.getMovie(), transaction.getNumTickets());
    }

    public static MovieGoer getMovieGoer(String email) {
        for (MovieGoer movieGoer : movieGoers) {
            if (movieGoer.getEmail().equals(email))
                return movieGoer;
        }
        return null;
    }

    public static void addMovieGoer(MovieGoer movieGoer) {
        movieGoers.add(movieGoer);
    }

    public static Map<Movie, Integer> getTicketSales() {
        return ticketSales;
    }

    private static void addTicketSales(Movie movie, int numTickets) {
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

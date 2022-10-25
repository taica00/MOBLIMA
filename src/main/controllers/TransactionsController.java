package main.controllers;

import java.util.List;
import main.models.MovieGoer;
import main.models.Session;

public class TransactionsController extends Controller {
    private static List<MovieGoer> movieGoers;
    private static final String FILEPATH = "src/main/data/moviegoers.ser";

    public static void makePayment(Session session, List<String> bookedSeats, String name, String mobile, String email, double price) {
        MovieGoer movieGoer = null;
        for (MovieGoer mGoer : movieGoers) {

        }
    }

    public static MovieGoer getMovieGoer(String email) {
        for (MovieGoer movieGoer : movieGoers) {
            if (movieGoer.getEmail().equals(email))
                return movieGoer;
        }
        return null;
    }

    public static void viewTransactions() {

    }

    public static void loadMovieGoers() {
        movieGoers = loadData(FILEPATH);
    }

    public static void saveMovieGoers() {
        saveData(movieGoers, FILEPATH);

    }
}

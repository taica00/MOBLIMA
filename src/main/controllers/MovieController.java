package main.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import main.models.Movie;
import main.models.MovieStatus;
import main.ui.MovieList;

public class MovieController extends Controller {
    private static List<Movie> movies;
    private static final String FILEPATH = "src/main/data/";

    public static void listMovies(boolean admin, boolean ticketSales, boolean rating) {
        List<Movie> movieList = new ArrayList<>();
        if (ticketSales) { // top 5 ranking by ticket sales
            Map<Movie, Integer> sales = TransactionsController.getTicketSales();
            PriorityQueue<Movie> ranking = new PriorityQueue<>((x, y) -> sales.getOrDefault(x, 0) - sales.getOrDefault(y, 0));
            while (movieList.size() < 5) 
                movieList.add(ranking.poll());
        }
        else if (rating) { // top 5 ranking by reviewer rating
            PriorityQueue<Movie> ranking = new PriorityQueue<>((x, y) -> (int)(x.getReviewerRating() - y.getReviewerRating()));
            while (movieList.size() < 5)
                movieList.add(ranking.poll());
        }
        else
            movieList = movies;
        MovieList.view(movieList, admin, ticketSales, rating);
    }

    public static void searchMovies() {
        String search = InputController.getString("Enter title of movie to search for: ");
        List<Movie> searchResults = new ArrayList<>();
        for (Movie movie : movies) {
            if (movie.getTitle().toLowerCase().contains(search.toLowerCase())) 
                searchResults.add(movie);
        }
        if (searchResults.isEmpty()) {
            System.out.println("No movies found. Returning to homepage");
            return;
        }
        System.out.println("Search results: ");
        MovieList.view(searchResults, false, false, false);
    }

    public static Movie searchMovie(String movieTitle) {
        for (Movie movie : movies) {
            if (movie.getTitle().equalsIgnoreCase(movieTitle))
                return movie;
        }
        return null;
    }

    public static void addMovie(String title, String rating, String showingStatus, String director, String casts, String sypnosis) {
        Movie movie = new Movie(title, rating, MovieStatus.valueOf(showingStatus), sypnosis, director, casts);
        movies.add(movie);
    }

    public static void removeMovie(int index) {
        movies.remove(index);
    }

    public static void loadMovies() {
        movies = loadData(FILEPATH + "movies.ser");
    }

    public static void saveMovies() {
        saveData(movies, FILEPATH + "movies.ser");
    }
}

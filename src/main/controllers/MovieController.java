package main.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import main.models.Movie;
import main.models.MovieStatus;
import main.ui.MovieList;
import main.ui.MovieRanking;

/**
 * This class manages user actions pertaining to the {@link Movie} class.
 * Stores a list of all movies.
 * @author Tai Chen An
 * @version 1.0 
 * @since 2022-11-08 
 */

public class MovieController extends Controller {
    private static List<Movie> movies;
    private static final String FILEPATH = "src/main/data/";
    
    /**
     * Calls {@link MovieList} to display all movies in the stored list. 
     * @param admin indicates if user is an admin or movie-goer.
     */
    public static void listMovies(boolean admin) {
        MovieList.view(movies, admin);
    }

    /** 
     * This method is called when user requests to display top 5 movies by ticket sales or reviewer ratings.
     * The list of movies is first sorted by a priority queue.
     * The front five elements are then added into a list and passed to {@link MovieRanking}. 
     * @param ticketSales indicates if movies are to be ranked by ticket sales or reviewer ratings.
     * @param admin indicates if user is an admin or movie-goer.
     */
    public static void rankMovies(boolean ticketSales, boolean admin) {
        List<Movie> movieList = new ArrayList<>();
        PriorityQueue<Movie> ranking;
        if (ticketSales)  // top 5 ranking by ticket sales
            ranking = new PriorityQueue<>((x, y) -> TransactionsController.getTicketSales(y) - TransactionsController.getTicketSales(x));
        else  // top 5 ranking by reviewer rating
            ranking = new PriorityQueue<>((x, y) -> Double.compare(y.getReviewerRating(), x.getReviewerRating())); 
        for (Movie movie : movies)
            ranking.offer(movie);
        while (movieList.size() < 5) 
            movieList.add(ranking.poll());
        MovieRanking.view(movieList, ticketSales, admin);
    }

    /**
     * This method is called when user requests to search for a movie.
     * A list of movies containing the input string is passed to {@link MovieList}.
     * If not, returns to {@link MainMenu}.
     */
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
        MovieList.view(searchResults, false);
    }

    /**
     * Search for a movie that contains the given string. 
     * @param movieTitle movie title to search for.
     * @return Movie containing the given string.
     */
    public static Movie searchMovie(String movieTitle) {
        for (Movie movie : movies) {
            if (movie.getTitle().equalsIgnoreCase(movieTitle))
                return movie;
        }
        return null;
    }

    /**
     * Creates a new Movie with the given fields and then adds it to the list of movies. 
     * @param title title of movie
     * @param rating rating of movie
     * @param showingStatus showing status of movie
     * @param director director of movie
     * @param casts casts of movie
     * @param sypnosis sypnosis of movie
     */
    public static void addMovie(String title, String rating, String showingStatus, String director, String casts, String sypnosis) {
        Movie movie = new Movie(title, rating, MovieStatus.valueOf(showingStatus), sypnosis, director, casts);
        movies.add(movie);
        
    }

    /**
     * Removes a movie from the list at the given index. 
     * @param index index of movie to remove.
     */
    public static void removeMovie(int index) {
        movies.remove(index);
    }
    
    /**
     * Removes the given movie from the list of movies
     * @param movie movie to remove.
     */
    public static void removeMovie(Movie movie) {
        movies.remove(movie);
    }

    /**
     * Deserialises the list of movies.
     */
    public static void loadMovies() {
        movies = loadData(FILEPATH + "movies.ser");
    }

    /**
     * Serialises the list of movies.
     */
    public static void saveMovies() {
        saveData(movies, FILEPATH + "movies.ser");
    }
}

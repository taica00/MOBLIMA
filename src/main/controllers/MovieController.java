package main.controllers;

import java.util.ArrayList;
import java.util.List;

import main.models.Movie;
import main.models.MovieStatus;
import main.ui.MovieListUI;

public class MovieController extends Controller {
    private static List<Movie> movies;
    private static final String FILEPATH = "src/main/data/movies.ser";

    public static void listMovies(boolean admin) {
        MovieListUI.view(movies, admin);
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
        MovieListUI.view(searchResults, false);
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
        movies = loadData(FILEPATH);
    }

    public static void saveMovies() {
        saveData(movies, FILEPATH);
    }
}

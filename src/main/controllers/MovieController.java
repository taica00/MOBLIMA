package main.controllers;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import main.models.Movie;
import main.ui.MovieListUI;

public class MovieController extends Controller {
    private static List<Movie> movies;
    private static final String FILENAME = "src/main/data/movies.ser";

    public static void listMovies() {
        MovieListUI.view(movies);
    }

    public static void searchMovies() {
        String search = InputController.getString("Enter title of movie to search for:");
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
        MovieListUI.view(searchResults);
    }

    public static void loadMovies() {
        try {
            FileInputStream fileIn = new FileInputStream(FILENAME);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            movies = (List<Movie>)in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            System.out.println("Class not found");
            c.printStackTrace();
            return;
        }
    }
}

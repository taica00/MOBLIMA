package main.controllers;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

import main.models.Movie;
import main.ui.MovieDetailsUI;
import main.ui.MovieListUI;

public class MovieController extends Controller {
    private static List<Movie> movies;
    private static final String FILENAME = "src/main/data/movies.ser";

    public static void listMovies(String movieName) {
        int i = 1;
        for (Movie movie : movies) {
            System.out.println((i++) + ". " + movie.getTitle());
        }
        System.out.println();
        MovieListUI.main(new String[]{""+movies.size()});
    }

    public static void viewMovieDetails(int index) {
        Movie movie = movies.get(index-1);
        System.out.println(movie);
        MovieDetailsUI.setMovie(movie);
        MovieDetailsUI.main(null);
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

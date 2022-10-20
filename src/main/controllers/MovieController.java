package main.controllers;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

import main.models.Movie;

public class MovieController {
    private static List<Movie> movies;
    private static final String FILENAME = "src/main/data/movies.ser";

    private MovieController() {
        throw new IllegalStateException("Utility class");
    }

    public static void listMovies() {
        for (Movie movie : movies)
            System.out.println(movie);
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

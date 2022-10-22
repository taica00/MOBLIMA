package main.ui;

import java.util.List;

import main.controllers.InputController;
import main.models.Movie;

public class MovieListUI extends UI {

    public static void view(List<Movie> movies) {
        int i = 1;
        for (Movie movie : movies) 
            System.out.println((i++) + ". " + movie.getTitle());
        int choice = InputController.getInt(0, movies.size(), "Enter list number of movie to view details or '0' to return to homepage: ");
        System.out.println();
        if (choice == 0)
            return;
        MovieDetailsUI.view(movies.get(choice));
    }
}

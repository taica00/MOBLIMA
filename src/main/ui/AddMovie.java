package main.ui;

import main.controllers.InputController;
import main.controllers.MovieController;

public class AddMovie extends UI {
    public static void view() {
        String title = InputController.getString("Enter movie title: ");
        String rating = InputController.getString("Enter rating [G, PG, PG13, NC16, M18, R21, NA]: ");
        String showingStatus = InputController.getString("Enter showing status [COMING_SOON, PREVIEW, NOW_SHOWING, END_OF_SHOWING]: ");
        String director = InputController.getString("Enter director: ");
        String casts = InputController.getString("Enter casts: ");
        String sypnopsis = InputController.getString("Enter sypnosis: ");
        MovieController.addMovie(title, rating, showingStatus, director, casts, sypnopsis);
        System.out.println("Movie added.");
    }
}

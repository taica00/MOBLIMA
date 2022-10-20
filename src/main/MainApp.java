package main;

import main.controllers.CineplexController;
import main.controllers.MovieController;
import main.ui.MainMenuUI;

public class MainApp {
    public static void main(String[] args) {
        initialiseData();
        MainMenuUI.main(null);
    }

    public static void initialiseData() {
        CineplexController.loadCineplexes();
        MovieController.loadMovies();
    }
}

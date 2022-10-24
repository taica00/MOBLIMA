package main;
import main.controllers.CineplexController;
import main.controllers.MovieController;
import main.ui.MainMenuUI;

public class MOBLIMA {
    public static void main(String[] args) {
        loadState();
        MainMenuUI.view();
        saveState();
    }

    private static void loadState() {
        CineplexController.loadCineplexes();
        MovieController.loadMovies();
    }

    private static void saveState() {
        CineplexController.saveCineplexes();
        MovieController.saveMovies();
    }
}

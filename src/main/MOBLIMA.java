package main;
import main.controllers.AdminController;
import main.controllers.CineplexController;
import main.controllers.MovieController;
import main.controllers.PricingController;
import main.controllers.TransactionsController;
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
        TransactionsController.loadMovieGoers();
        PricingController.loadHolidays();
        AdminController.loadAdminAccounts();
    }

    private static void saveState() {
        CineplexController.saveCineplexes();
        MovieController.saveMovies();
        TransactionsController.saveMovieGoers();
        PricingController.saveHolidays();
        AdminController.saveAdminAccounts();
    }
}

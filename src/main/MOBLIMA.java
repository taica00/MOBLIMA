package main;
import main.controllers.AdminController;
import main.controllers.CineplexController;
import main.controllers.MovieController;
import main.controllers.PricingController;
import main.controllers.TransactionsController;
import main.ui.MainMenu;

/**
 * The class provides the main method to run the MOBLIMA application.
 * The database is loaded before running the application and saved upon exit.
 * @author Tai Chen An
 * @version 1.0 
 * @since 2022-11-08 
 */

public class MOBLIMA {
    
    /**
     * The main method to run the MOBLIMA application. 
     * @param args
     */
    public static void main(String[] args) {
        loadState();
        MainMenu.view();
        saveState();
    }

    /**
     * Deserialises the database of entities.
     */
    private static void loadState() {
        MovieController.loadMovies();
        CineplexController.loadCineplexes();
        TransactionsController.loadMovieGoers();
        TransactionsController.loadTicketSales();
        PricingController.loadHolidays();
        AdminController.loadAdminAccounts();
    }

    /**
     * Serialises the database of entities.
     */
    private static void saveState() {
        MovieController.saveMovies();
        CineplexController.saveCineplexes();
        TransactionsController.saveMovieGoers();
        TransactionsController.saveTicketSales();
        PricingController.saveHolidays();
        AdminController.saveAdminAccounts();
    }
}

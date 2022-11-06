package main;
import main.controllers.AdminController;
import main.controllers.CineplexController;
import main.controllers.MovieController;
import main.controllers.PricingController;
import main.controllers.TransactionsController;
import main.ui.MainMenu;

public class MOBLIMA {
    
    /** 
     * @param args
     */
    public static void main(String[] args) {
        loadState();
        MainMenu.view();
        saveState();
    }

    private static void loadState() {
        CineplexController.loadCineplexes();
        MovieController.loadMovies();
        TransactionsController.loadMovieGoers();
        TransactionsController.loadTicketSales();
        PricingController.loadHolidays();
        AdminController.loadAdminAccounts();
    }

    private static void saveState() {
        CineplexController.saveCineplexes();
        MovieController.saveMovies();
        TransactionsController.saveMovieGoers();
        TransactionsController.saveTicketSales();
        PricingController.saveHolidays();
        AdminController.saveAdminAccounts();
    }
}

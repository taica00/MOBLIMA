package main.ui;

import java.util.List;

import main.controllers.InputController;
import main.models.Cinema;

public class CinemaList extends UI {
    
    /** 
     * @param cinemas
     * @param admin
     */
    public static void view(List<Cinema> cinemas, boolean admin) {
        System.out.println("Cinema list: \n");
        int i = 1;
        for (Cinema cinema : cinemas) {
            System.out.print((i++) + ". ");
            System.out.println(cinema);
        }
        System.out.println();
        int choice  = InputController.getInt(1, cinemas.size(), "Select index of cinema to view showtimes: ");
        ShowTimes.view(cinemas.get(choice-1), admin);
    }
}

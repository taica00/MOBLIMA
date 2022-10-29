package main.ui;

import java.util.List;

import main.controllers.InputController;
import main.models.Cinema;

public class CinemaListUI extends UI {
    public static void view(List<Cinema> cinemas) {
        System.out.println("Cinemas available for booking: \n");
        int i = 1;
        for (Cinema cinema : cinemas) {
            System.out.print((i++) + ". ");
            System.out.println(cinema);
        }
        System.out.println();
        int choice  = InputController.getInt(1, cinemas.size(), "Select index of cinema to view showtimes: ");
        ShowTimesUI.view(cinemas.get(choice-1));
    }
}

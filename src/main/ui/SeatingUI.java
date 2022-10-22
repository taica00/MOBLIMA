package main.ui;

import main.controllers.InputController;
import main.controllers.SessionController;
import main.models.Seating;
import main.models.Session;

public class SeatingUI extends UI {
    public static void view(Session session) {
        Seating seating = session.getSeating();
        System.out.println(seating);
        String message = "Enter seat IDs, e.g. F1 F2 F3";
        String seats = InputController.getString(message);
        SessionController.bookSeats(session, seats.split(" "));
    }
}

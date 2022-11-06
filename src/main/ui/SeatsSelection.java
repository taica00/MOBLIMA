package main.ui;

import main.controllers.InputController;
import main.controllers.SessionController;
import main.models.Seating;
import main.models.Session;

public class SeatsSelection extends UI {
    
    /** 
     * @param session
     */
    public static void view(Session session) {
        Seating seating = session.getSeating();
        System.out.println(seating);
        String seats = InputController.getString("Enter seat IDs to book, separated by spaces, e.g. F1 F2 F3:\n");
        SessionController.bookSeats(session, seats.split(" "));  
    }
}

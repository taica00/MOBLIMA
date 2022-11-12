package main.ui;

import main.controllers.InputController;
import main.controllers.SessionController;
import main.models.Seating;
import main.models.Session;

/**
 * This class provides the UI to display the seats selection of a session.
 * @author Tai Chen An
 * @version 1.0 
 * @since 2022-11-08 
 */

public class SeatsSelection extends UI {
    
    /** 
     * Displays the seating layout of the given session.
     * Movie-goers can see which seats are available and which are booked.
     * Movie-goers to enter seat IDs they request to book.
     * Session and seats to book are passed to {@link SessionController}.
     * @param session session to view seats selection
     */
    public static void view(Session session) {
        Seating seating = session.getSeating();
        seating.displaySeating();
        String seats = InputController.getString("Enter seat IDs to book, separated by spaces, e.g. F1 F2 F3:\n");
        SessionController.bookSeats(session, seats.split(" "));  
    }
}

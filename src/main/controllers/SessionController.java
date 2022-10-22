package main.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import main.models.Session;
import main.models.Session.CinemaClass;
import main.ui.SeatingUI;

public class SessionController extends Controller {
    public static void viewSeating(List<List<Session>> movieSessions, String[] input) {
        String cinemaCode = input[0];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyHHmm");
        LocalDateTime dateTime = LocalDateTime.parse(input[1]+input[3], formatter);
        CinemaClass cinemaClass = CinemaClass.valueOf(input[2]);
        for (List<Session> cinemaSessions : movieSessions) {
            for (Session session : cinemaSessions) {
                if (!session.getCinema().getCinemaCode().equals(cinemaCode))
                    break;
                if (dateTime.isEqual(session.getDateTime()) && cinemaClass.equals(session.getCinemaClass())) {
                    SeatingUI.view(session);
                    return;
                }
            }
        }
        System.out.println("Session not found. Returning to homepage.");
    }

    public static void bookSeats(Session session, String[] seats) {

    }
}

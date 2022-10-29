package main.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import main.models.Cinema;
import main.models.CinemaClass;
import main.models.Session;
import main.ui.BookingUI;
import main.ui.SeatingUI;

public class SessionController extends Controller {
    public static void viewSeating(List<List<Session>> movieSessions, String cinemaCode, String date, String cinemaClass, String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyHHmm");
        LocalDateTime dateTime = LocalDateTime.parse(date+time, formatter);
        for (List<Session> cinemaSessions : movieSessions) {
            for (Session session : cinemaSessions) {
                if (!session.getCinema().getCinemaCode().equals(cinemaCode))
                    break;
                if (dateTime.isEqual(session.getDateTime()) && CinemaClass.valueOf(cinemaClass).equals(session.getCinemaClass())) {
                    SeatingUI.view(session);
                    return;
                }
            }
        }
        System.out.println("Session not found. Returning to homepage.");
    }

    public static void viewSeating(Cinema cinema, String movie, String date, String cinemaClass, String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyHHmm");
        LocalDateTime dateTime = LocalDateTime.parse(date+time, formatter);
        for (Session session : cinema.getSessions()) {
            if (movie.equalsIgnoreCase(session.getMovie().getTitle()) &&  dateTime.isEqual(session.getDateTime()) && CinemaClass.valueOf(cinemaClass).equals(session.getCinemaClass())) {
                SeatingUI.view(session);
                return;
            }
        }
        System.out.println("Session not found. Returning to homepage.");
    }

    public static void bookSeats(Session session, String[] seats) {
        List<String> bookedSeats = new ArrayList<>();
        for (String seat : seats) {
            char row = seat.charAt(0);
            char col = seat.charAt(1);
            if (!Character.isUpperCase(row) || !Character.isDigit(col)) 
                System.out.println(seat + " is not a valid selection.");
            else if (!session.getSeating().bookSeat(row, col)) 
                System.out.println("Seat " + seat + " is occupied");
            else
                bookedSeats.add(seat);
        }
        if (bookedSeats.isEmpty()) {
            System.out.println("No seats booked. Returning to homepage.");
            return;
        }
        BookingUI.view(session, bookedSeats);
    }

    public static void undoBooking(Session session, List<String> seats) {
        for (String seat : seats) {
            char row = seat.charAt(0);
            char col = seat.charAt(1);
            session.getSeating().unBookSeat(row, col);
        }
    }
}

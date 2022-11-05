package main.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import main.models.Cinema;
import main.models.CinemaClass;
import main.models.Movie;
import main.models.Session;
import main.ui.TicketsPayment;
import main.ui.SeatsSelection;

public class SessionController extends Controller {
    public static boolean viewSeating(List<List<Session>> movieSessions, String cinemaCode, String date, String cinemaClass, String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyHHmm");
        LocalDateTime dateTime = LocalDateTime.parse(date+time, formatter);
        for (List<Session> cinemaSessions : movieSessions) {
            for (Session session : cinemaSessions) {
                if (!session.getCinema().getCinemaCode().equals(cinemaCode))
                    break;
                if (dateTime.isEqual(session.getDateTime()) && CinemaClass.valueOf(cinemaClass).equals(session.getCinemaClass())) {
                    SeatsSelection.view(session);
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean viewSeating(Cinema cinema, String movie, String date, String cinemaClass, String time) {
        Session session = CineplexController.searchSession(cinema, movie, date, cinemaClass, time);
        if (session == null) 
            return false;
        SeatsSelection.view(session);
        return true;
    }

    public static void bookSeats(Session session, String[] seats) {
        List<String> bookedSeats = new ArrayList<>();
        for (String seat : seats) {
            char row = seat.charAt(0);
            String col = seat.substring(1);
            if (!Character.isUpperCase(row) || !StringUtils.isNumeric(col) || Integer.parseInt(col) == 0 || Integer.parseInt(col) > session.getSeating().getNumCols()) 
                System.out.println(seat + " is not a valid selection.");
            else if (!session.getSeating().bookSeat(row, Integer.parseInt(col))) 
                System.out.println("Seat " + seat + " is occupied");
            else
                bookedSeats.add(seat);
        }
        if (bookedSeats.isEmpty()) {
            System.out.println("No seats booked. Returning to homepage.");
            return;
        }
        TicketsPayment.view(session, bookedSeats);
    }

    public static void undoBooking(Session session, List<String> seats) {
        for (String seat : seats) {
            char row = seat.charAt(0);
            int col = Integer.parseInt(seat.substring(1));
            session.getSeating().unBookSeat(row, col);
        }
    }

    public static boolean updateCinema(Session session, String cinemaString) {
        Cinema cinema = CineplexController.searchCinema(cinemaString);
        if (cinema == null)
            return false;
        session.setCinema(cinema);
        return true;
    }

    public static boolean updateMovie(Session session, String movieString) {
        Movie movie = MovieController.searchMovie(movieString);
        if (movie == null)
            return false;
        session.setMovie(movie);
        return true;
    }

    public static void updateDate(Session session, String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyy");
        LocalDate date = LocalDate.parse(dateString, formatter);
        LocalTime time = session.getDateTime().toLocalTime();
        session.setDateTime(LocalDateTime.of(date, time));
    }

    public static void updateTime(Session session, String timeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmm");
        LocalTime time = LocalTime.parse(timeString, formatter);
        LocalDate date = session.getDateTime().toLocalDate();
        session.setDateTime(LocalDateTime.of(date, time));
    }
}

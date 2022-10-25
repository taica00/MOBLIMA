package main.ui;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import main.controllers.InputController;
import main.controllers.SessionController;
import main.models.Cinema;
import main.models.CinemaClass;
import main.models.Session;

public class ShowTimesUI extends UI {
    public static void view(List<List<Session>> movieSessions) {
        for (List<Session> cinemaSessions : movieSessions) {
            LocalDate date = null;
            CinemaClass cinemaClass = null;
            System.out.println("\n=============================================================");
            Cinema cinema = cinemaSessions.get(0).getCinema();      
            System.out.print(cinema + " (" + cinema.getCinemaCode() + ")");
            int rowCount = 0;
            for (Session session : cinemaSessions) {
                LocalDateTime dateTime = session.getDateTime();
                LocalDate sessionDate = dateTime.toLocalDate();
                CinemaClass sessionClass = session.getCinemaClass();
                if (date == null || !sessionDate.isEqual(date)) {
                    System.out.println("\n-------------------------------------------------------------");
                    date = sessionDate;
                    System.out.print(date.getDayOfMonth() + " " + date.getMonth() + " " + date.getYear());
                    cinemaClass = null;
                }
                if (cinemaClass == null || !cinemaClass.equals(sessionClass)) {
                    cinemaClass = sessionClass;
                    System.out.println("\n\n" + cinemaClass);
                    rowCount = 0;
                }
                System.out.print(dateTime.toLocalTime() + " ");
                if (++rowCount == 10) {
                    System.out.println();
                    rowCount = 0;
                }
            }
        }
        System.out.println("\n-------------------------------------------------------------");
        System.out.println("1. Book tickets | 2. Return to homepage");
        int choice = InputController.getInt(1, 2, "Select an option: ");
        System.out.println();
        if (choice == 2)
            return;
        String cinemaCode = InputController.getString("Enter cinema code, e.g. for JCube (SJC), SJC is the cinema code: ");
        String date = InputController.getString("Enter date in ddMMyy format, e.g. 251022: ");
        String cinemaClass = InputController.getString("Enter cinemaClass(non case-sensitive, please input '_' for spaces), e.g. gold_class_express: ");
        String time = InputController.getString("Enter time in HHmm format, e.g. 1620: ");
        System.out.println();
        SessionController.viewSeating(movieSessions, cinemaCode, date, cinemaClass.toUpperCase(), time);
    }
}

package tests;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import main.controllers.PricingController;
import main.models.Cinema;
import main.models.CinemaClass;
import main.models.Cineplex;
import main.models.Movie;
import main.models.MovieStatus;
import main.models.Session;

public class TicketPricingTests {
    public static void main(String[] args) {
        // initiate entities
        PricingController.loadHolidays();
        Cinema standardCinema = new Cinema(new Cineplex("test"), 1, CinemaClass.STANDARD);
        Cinema premiereCinema = new Cinema(new Cineplex("test"), 1, CinemaClass.PREMIERE);
        Cinema imaxCinema = new Cinema(new Cineplex("test"), 1, CinemaClass.IMAX);
        Movie testMovie = new Movie("test", "PG" , MovieStatus.NOW_SHOWING, "test", "test", "test");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyHHmm");
        
        // configure holiday date and ticket price shown correctly
        LocalDateTime dateTime = LocalDateTime.parse("0211221200", formatter);
        Session testSession = new Session(standardCinema, testMovie, dateTime, false);
        System.out.println("Before configuring as holiday:");
        PricingController.getTicketsPrice(testSession, 1, 0, 0);
        PricingController.addHolidayDate("021122"); 
        System.out.println("\nAfter configuring as holiday:");
        PricingController.getTicketsPrice(testSession, 1, 0, 0); 

        // booking on different days of the week
        LocalDateTime nonPeakDateTime = LocalDateTime.parse("0311221200", formatter);
        Session nonPeakSession = new Session(standardCinema, testMovie, nonPeakDateTime, false);
        LocalDateTime peakDateTime = LocalDateTime.parse("0511221200", formatter);
        Session peakSession = new Session(standardCinema, testMovie, peakDateTime, false);
        System.out.println("\nMon-Thurs pricing:");
        PricingController.getTicketsPrice(nonPeakSession, 1, 0, 0);
        System.out.println("\nFri-Sun pricing:");
        PricingController.getTicketsPrice(peakSession, 1, 0, 0);

        // booking different suites of cinema
        Session standardSession = nonPeakSession;
        Session premiereSession = new Session(premiereCinema, testMovie, nonPeakDateTime, false);
        Session imaxSession = new Session(imaxCinema, testMovie, nonPeakDateTime, false);
        System.out.println("\nStandard session:");
        PricingController.getTicketsPrice(standardSession, 1, 0, 0);
        System.out.println("\npremiere session:");
        PricingController.getTicketsPrice(premiereSession, 1, 0, 0);
        System.out.println("\nIMAX session:");
        PricingController.getTicketsPrice(imaxSession, 1, 0, 0);
        
        // Senior citizen and student pricing
        System.out.println("\nSenior citizen pricing:");
        PricingController.getTicketsPrice(standardSession, 1, 1, 0);
        System.out.println("\nStudent pricing:");
        PricingController.getTicketsPrice(standardSession, 1, 0, 1);

        // 3D session pricing
        System.out.println("\n3D session pricing:");
        standardSession.set3D(true);
        PricingController.getTicketsPrice(standardSession, 1, 0, 0);

    }
}

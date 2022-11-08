package tests;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import main.controllers.PricingController;
import main.models.Cinema;
import main.models.Movie;
import main.models.MovieStatus;
import main.models.Session;

public class TicketPricingTests {
    public static void main(String[] args) {
        // initiate entities
        PricingController.loadHolidays();
        Cinema testCinema = new Cinema("test", "test");
        Movie testMovie = new Movie("test", "PG" , MovieStatus.NOW_SHOWING, "test", "test", "test");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyHHmm");
        
        // configure holiday date and ticket price shown correctly
        LocalDateTime dateTime = LocalDateTime.parse("0211221200", formatter);
        Session testSession = new Session(testCinema, testMovie, dateTime, "STANDARD", false);
        System.out.println("Before configuring as holiday:");
        PricingController.getTicketsPrice(testSession, 1, 0, 0);
        PricingController.addHolidayDate("021122"); 
        System.out.println("\nAfter configuring as holiday:");
        PricingController.getTicketsPrice(testSession, 1, 0, 0); 

        // booking on different days of the week
        LocalDateTime nonPeakDateTime = LocalDateTime.parse("0311221200", formatter);
        Session nonPeakSession = new Session(testCinema, testMovie, nonPeakDateTime, "STANDARD", false);
        LocalDateTime peakDateTime = LocalDateTime.parse("0511221200", formatter);
        Session peakSession = new Session(testCinema, testMovie, peakDateTime, "STANDARD", false);
        System.out.println("\nNon peak pricing:");
        PricingController.getTicketsPrice(nonPeakSession, 1, 0, 0);
        System.out.println("\nPeak pricing:");
        PricingController.getTicketsPrice(peakSession, 1, 0, 0);

        // booking different suites of cinema
        Session standardSession = nonPeakSession;
        Session premiereSession = new Session(testCinema, testMovie, nonPeakDateTime, "PREMIERE", false);
        Session imaxSession = new Session(testCinema, testMovie, nonPeakDateTime, "IMAX", false);
        System.out.println("\nStandard session:");
        PricingController.getTicketsPrice(standardSession, 1, 0, 0);
        System.out.println("\npremiere session:");
        PricingController.getTicketsPrice(premiereSession, 1, 0, 0);
        System.out.println("\nIMAX session:");
        PricingController.getTicketsPrice(imaxSession, 1, 0, 0);
        
        // TODO senior citizen, student and 3D pricing
        

    }
}

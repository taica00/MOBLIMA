package main.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import main.models.Session;
import main.ui.ConfigureHolidays;

/**
 * This class manages user actions pertaining to ticket prices.
 * Stores a set of holiday dates.
 * @author Tai Chen An
 * @version 1.0 
 * @since 2022-11-08 
 */

public class PricingController extends Controller {
    /**
     * Set containing the dates of holidays.
     */
    private static Set<LocalDate> holidays;
    private static final String FILEPATH = "src/main/data/holidays.ser";

    /**
     * Calculates the price of the given number of tickets for the given session.
     * Price of ticket is dependent on cinema class, date and time of the given session.
     * A 3D movie costs an additional $2 for each ticket. 
     * @param session 
     * @param numTickets total number of tickets to be booked.
     * @param seniors number of senior citizens.
     * @param students number of students.
     * @return price of all tickets booked for the given session.
     */
    public static double getTicketsPrice(Session session, int numTickets, int seniors, int students) {
        double[] pricing = session.getCinema().getCinemaClass().ticketPrices();
        double total = 0;
        total += calculateAndPrintPrice(pricing[0], "Senior Citizen", seniors);
        total += calculateAndPrintPrice(pricing[1], "Student", students);
        LocalDate date = session.getDateTime().toLocalDate();
        int others = numTickets - seniors - students;
        if (!isPeakPricing(date))  
            total += calculateAndPrintPrice(pricing[2], "Non-Peak", others);
        else
            total += calculateAndPrintPrice(pricing[3], "Peak", others);
        if (session.is3D()) 
            total += calculateAndPrintPrice(2, "3D Screening", numTickets);
        return total;
    }

    /**
     * This method is called when an admin requests to configure the holiday dates.
     * Calls {@link ConfigureHolidays}.
     */
    public static void configureHolidays() {
        ConfigureHolidays.view(holidays);
    }

    /**
     * Adds a date to the list of holiday dates. 
     * @param dateString
     */
    public static void addHolidayDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyy");
        LocalDate date = LocalDate.parse(dateString, formatter);
        holidays.add(date);
    }
    
    /** 
     * Removes a date from the list of holiday dates.
     * @param date
     */
    public static void removeHolidayDate(LocalDate date) {
        holidays.remove(date);
    }
 
    /**
     * Determines if given date and time is eligible for senior citizen or student pricing.
     * Senior citizen or student pricing is only eligible from Monday to Friday, before 6pm.
     * @param dateTime
     * @return true if the given date and time is eligible for senior citizen or student pricing.
     */
    public static boolean eligibleForConcession(LocalDateTime dateTime) {
        int day = dateTime.getDayOfWeek().getValue();
        return day >= 1 && day <= 5 && dateTime.getHour() < 18 && !holidays.contains(dateTime.toLocalDate());
    }

    /**
     * Calculates the total price for the given quantity of the given price category of 
     * tickets each costing the given ticket price.
     * Prints out the calculation details.
     * @param ticketPrice
     * @param priceCat
     * @param quantity
     * @return total price for the tickets.
     */
    private static double calculateAndPrintPrice(double ticketPrice, String priceCat, int quantity) {
        if (quantity == 0)
            return 0;
        double cost = quantity * ticketPrice;
        System.out.printf("%s $%.2f x%d : $%.2f%n", priceCat, ticketPrice, quantity, cost);
        return cost;
    }
    
    /**
     * Determine if the given date is a peak date.
     * Friday to Sunday and holidays are peak dates.
     * @param date
     * @return true if given date is a peak date.
     */
    private static boolean isPeakPricing(LocalDate date) {
        return date.getDayOfWeek().getValue() >= 5 || holidays.contains(date);
    }

    public static void loadHolidays() {
        holidays = loadData(FILEPATH);
    }

    public static void saveHolidays() {
        saveData(holidays, FILEPATH);
    }
}


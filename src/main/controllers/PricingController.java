package main.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import main.models.Session;
import main.ui.ConfigureHolidays;

public class PricingController extends Controller {
    private static Set<LocalDate> holidays;
    private static final String FILEPATH = "src/main/data/holidays.ser";

    public static double getTicketsPrice(Session session, int numTickets, int seniors, int students) {
        double[] pricing = session.getCinemaClass().ticketPrices();
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

    public static void configureHolidays() {
        ConfigureHolidays.view(holidays);
    }

    public static void addHolidayDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyy");
        LocalDate date = LocalDate.parse(dateString, formatter);
        holidays.add(date);
    }

    public static void removeHolidayDate(LocalDate date) {
        holidays.remove(date);
    }

    public static boolean eligibleForConcession(LocalDateTime dateTime) {
        int day = dateTime.getDayOfWeek().getValue();
        return day >= 1 && day <= 5 && dateTime.getHour() < 18 && !holidays.contains(dateTime.toLocalDate());
    }

    private static double calculateAndPrintPrice(double ticketPrice, String priceCat, int quantity) {
        if (quantity == 0)
            return 0;
        double cost = quantity * ticketPrice;
        System.out.printf("%s $%.2f x%d : $%.2f%n", priceCat, ticketPrice, quantity, cost);
        return cost;
    }

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


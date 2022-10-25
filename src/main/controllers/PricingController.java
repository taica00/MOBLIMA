package main.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import main.models.Session;

public class PricingController extends Controller {
    private static Set<LocalDate> holidays;
    private static final String FILEPATH = "src/main/data/holidays.ser";

    public static double getTicketsPrice(Session session, int numTickets, int seniors, int students) {
        double[] pricing = session.getCinemaClass().ticketPrices();
        double total = 0;
        for (int i = 0; i < seniors; i++)  // senior citizens
            total += pricing[0];
        for (int i = 0; i < students; i++) // students
            total += pricing[1];
        LocalDate date = session.getDateTime().toLocalDate();;
        int pricingIndex = 2; // non peak pricing
        if (isPeakPricing(date))  // peak pricing
            pricingIndex = 3;
        for (int i = 0; i < numTickets-seniors-students; i++) {
            total += pricing[pricingIndex];
        }
        if (session.is3D())
            total += numTickets * 2; // extra $2 for 3D movies
        return total;
    }

    public static boolean isPeakPricing(LocalDate date) {
        return date.getDayOfWeek().getValue() >= 5 || holidays.contains(date);
    }

    public static boolean eligibleForConcession(LocalDateTime dateTime) {
        int day = dateTime.getDayOfWeek().getValue();
        return day >= 1 && day <= 5 && dateTime.getHour() < 18;
    }

    public static void loadHolidays() {
        holidays = loadData(FILEPATH);
    }

    public static void saveHolidays() {
        saveData(holidays, FILEPATH);
    }
}


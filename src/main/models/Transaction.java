package main.models;

import java.time.LocalDateTime;
import java.util.List;

public class Transaction implements java.io.Serializable {
    private static final long serialVersionUID = 7L;
    private String TID;
    private Session session;
    private List<String> seats;
    private double price;

    public Transaction(Session session, List<String> seats, double price) {
        if (session == null || seats == null)
            throw new IllegalArgumentException("fields cannot be null.");
        if (seats.isEmpty())
            throw new IllegalArgumentException("list of seats cannot be empty.");
        if (price < 0)
            throw new IllegalArgumentException("price cannot be negative");
        this.session = session;
        this.seats = seats;
        this.price = price;
        generateTID();
    }

    private void generateTID() {
        StringBuilder sb = new StringBuilder();
        sb.append(session.getCinema().getCinemaCode());
        LocalDateTime dateTime = LocalDateTime.now();
        sb.append(dateTime.getYear());
        int month = dateTime.getMonthValue();
        if (month < 10)
            sb.append("0" + month);
        else
            sb.append(month);
        int day = dateTime.getDayOfMonth();
        if (day < 10)
            sb.append("0" + day);
        else
            sb.append(day);
        int hour = dateTime.getHour();
        if (hour < 10)
            sb.append("0" + hour);
        else
            sb.append(hour);
        int minute = dateTime.getMinute();
        if (minute < 10)
            sb.append("0" + minute);
        else
            sb.append(minute);    
        TID = sb.toString();
    }

    
    /** 
     * @return String
     */
    @Override
    public String toString() {
        LocalDateTime dateTime = session.getDateTime();
        StringBuilder sb = new StringBuilder();
        sb.append("Transaction id: " + TID + "\n");
        sb.append(session.getCinema() + " " + session.getCinemaClass() + "\n");
        sb.append(session.getMovie().getTitle() + " (" + session.getMovie().getRating() + ")\n");
        sb.append(dateTime.getDayOfMonth() + " " + dateTime.getMonth() + " " + dateTime.getYear() + " " + dateTime.toLocalTime() + "\n");
        sb.append("Seats booked: ");
        for (String seat : seats) 
            sb.append(seat + " ");
        String priceString = String.format("$%.2f", price);
        sb.append("\nTotal price: " + priceString + "\n");
        return sb.toString();
    }
    
}

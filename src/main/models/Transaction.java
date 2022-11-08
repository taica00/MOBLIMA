package main.models;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Represents a Transaction made by a movie-goer.
 * A transaction contains information of payment made for booked seat(s) of one session.
 * @author Tai Chen An
 * @version 1.0 
 * @since 2022-11-06 
 */

public class Transaction implements java.io.Serializable {
    private static final long serialVersionUID = 7L;

    /**
     * The transaction ID of this transaction.
     */
    private String TID;

    /**
     * The session of the seat(s) booked in this transaction.
     */
    private Session session;

    /**
     * The list of seat(s) booked in this transaction.
     */
    private List<String> seats;

    /**
     * The price (SGD) paid for the seat(s) booked in this transaction.
     */
    private double price;

    /**
     * Creates a new Transaction with the given fields.
     * The transaction ID is then generated. 
     * @param session
     * @param seats
     * @param price
     */
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

    /**
     * The transaction ID is generated using the cinema code of the cinema hosting the session
     * as well as the date and time that this transaction is made.
     */
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
     * @return details of the transaction.
     */
    @Override
    public String toString() {
        LocalDateTime dateTime = session.getDateTime();
        StringBuilder sb = new StringBuilder();
        sb.append("Transaction id: " + TID + "\n");
        sb.append(session.getCinema() + "\n");
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

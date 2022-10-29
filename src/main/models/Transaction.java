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
        this.session = session;
        this.seats = seats;
        this.price = price;
        generateTID();
    }

    private void generateTID() {
        StringBuilder sb = new StringBuilder();
        sb.append(session.getCinema().getCinemaCode());
        LocalDateTime dateTime = session.getDateTime();
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

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return super.toString();
    }
    
}

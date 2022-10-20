package main.models;

public class Ticket implements java.io.Serializable {
    private static final long serialVersionUID = 7L;
    private Cinema cinema;
    private Session movieSession;
    private double price;
    private String seat;
}

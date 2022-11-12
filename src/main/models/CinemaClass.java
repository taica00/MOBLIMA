package main.models;

/**
 * Cinema classes for showtime sessions.
 * Each class has different seating layouts and ticket prices.
 * @author Tai Chen An
 * @version 1.1 
 * @since 2022-11-09 
 */

public enum CinemaClass implements SeatingLayout {
    STANDARD("Standard", 17, 34, new int[]{7, 27}, new double[]{5, 7, 10, 14.5}),
    IMAX("IMAX", 8, 21, new int[]{4, 16}, new double[]{17, 19, 20, 23}),
    LUMIERE("Lumiere", 4, 6, new int[]{2, 4}, new double[]{17, 18, 23, 28}),
    DREAMERS("Dreamers", 6, 10, new int[]{5}, new double[]{7, 8, 9, 14}),
    PREMIERE("premiere", 6, 8, new int[]{2, 4, 6}, new double[]{22, 25, 30, 35});
    
    /**
     * The name of the cinema class.
     */
    private String name;

    /**
     * The number of rows of seats for the showtime session.
     */
    private int rows;

    /**
     * The number of columns of seats for the showtime session.
     */
    private int columns;

    /**
     * An array holding the column indexes of where aisles would be.
     * Used for printing {@link StandardSeating}.
     */
    private int[] aisles;

    /**
     * An array holding the ticket prices for each pricing category.
     * The indexes represent {Senior Citizen, Student, Non-peak, Peak}.
     */
    private double[] ticketPrices;

    /**
     * Creates a CinemaClass value with the given fields.
     * @param name name of class
     * @param rows rows of seats
     * @param columns columns of seats
     * @param aisles index of aisles
     * @param ticketPrices ticket prices for each pricing category
     */
    CinemaClass(String name, int rows, int columns, int[] aisles, double[] ticketPrices) {
        this.name = name;
        this.rows = rows;
        this.columns = columns;
        this.aisles = aisles;
        this.ticketPrices = ticketPrices;
    }

    /**
     * @param index index represents pricing category.
     * @param price price of each ticket.
     */
    public void setPrice(int index, double price) {
        ticketPrices[index] = price;
    }

    /**
     * @return number of rows
     */
    @Override
    public int rows() {return rows;}

    /**
     * @return number of columns
     */
    @Override
    public int columns() {return columns;}

    /**
     * @return index values of aisles
     */
    @Override
    public int[] aisles() {return aisles;}

    /**
     * @return ticket prices for each pricing category
     */
    @Override
    public double[] ticketPrices() {return ticketPrices;}

    /**
     * @return name of cinema class
     */
    @Override
    public String toString() {return name;}
}

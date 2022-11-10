package main.models;

/**
 * The interface for seating layouts.
 * Provides methods for information about the seating layout.
 * @author Tai Chen An
 * @version 1.0 
 * @since 2022-11-06 
 */

public interface SeatingLayout {
    /**
     * @return number of rows for this seating layout
     */
    public int rows();

    /**
     * Does not include aisles.
     * @return number of columns for this seating layout
     */
    public int columns(); 

    /**
     * Each value represents the column that is an aisle
     * @return indexes of aisles
     */
    public int[] aisles(); // each value represents the column that is an aisle

    /**
     * pricing category indexes are {senior citizen, student, mon-thur, fri-sun/ph}.
     * @return ticket prices for each pricing category
     */
    public double[] ticketPrices(); 
}

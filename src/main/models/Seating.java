package main.models;

/**
 * Represents the seating layout of a session, displaying which seats are booked and which are not.
 * This interface allows for different seating types to display the seating layout differently.
 * @author Tai Chen An
 * @version 1.1 
 * @since 2022-11-12 
 */

public interface Seating extends java.io.Serializable {
    public void displaySeating();
    public boolean bookSeat(char row, int col);
    public void unBookSeat(char row, int col);
    public int getNumCols();
    public int getNumRows();
}

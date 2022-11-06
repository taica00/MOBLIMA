package main.models;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Represents the seating layout of a showtime session.
 * 
 * @author Tai Chen An
 * @version 1.0 
 * @since 2022-11-06 
 */

public class Seating implements java.io.Serializable {
    private static final long serialVersionUID = 5L;
    
    /**
     * The seats of this seating represented in a 2D array.
     */
    private int[][] seats;

    /**
     * This set holds the column index of all aisles.
     */
    private Set<Integer> aisles;

    /**
     * Creates a new Seating with the given cinemaClass.
     * The fields for this seating are provided by methods in {@link CinemaClass}.
     * @param cinemaClass
     */
    public Seating(CinemaClass cinemaClass) {
        int rows = cinemaClass.rows();
        int columns = cinemaClass.columns();
        aisles = new HashSet<>(Arrays.stream(cinemaClass.aisles()).boxed().collect(Collectors.toList()));
        seats = new int[rows][columns];
    }

    /**
     * Books a seat with the given row and column indexes. 
     * @param row
     * @param col
     * @return true if seat is not taken.
     */
    public boolean bookSeat(char row, int col) {
        if (seats[row-'A'][col-1] == 1) // occupied seat
            return false;
        seats[row-'A'][col-1] = 1;
        return true;
    }

    /**
     * Unbooks a seat in the case where movie-goer does not complete payment. 
     * @param row
     * @param col
     */
    public void unBookSeat(char row, int col) {
        seats[row-'A'][col-1] = 0;
    }

    /** 
     * @return the seating layout, showing the available and taken seats.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SEATS SELECTION:\n\n");
        sb.append(" ");
        int rowLength = seats[0].length + aisles.size();
        for (int i = 0; i < rowLength-2 ; i++) {
            if (i == (rowLength-2)/2)
                sb.append("-SCREEN-");
            else
                sb.append("---");
        }
        sb.append("--\n\n");
        for (int i = 0; i < seats.length; i++) {  
            sb.append((char)(i+'A') + " ");  // row index
            for (int j = 0; j < seats[i].length; j++) { // seats
                if (aisles.contains(j)) 
                    sb.append("   ");    
                if (seats[i][j] == 0)
                    sb.append("[ ]");
                else 
                    sb.append("[X]");   
            }
            sb.append(" " + (char)(i+'A') + "\n"); // row index
        }
        sb.append("  ");
        for (int i = 1; i <= seats[0].length; i++) { // col index
            if (aisles.contains(i-1)) 
                sb.append("   ");  
            if (i >= 10)
                sb.append(i + " ");
            else
                sb.append(" " + i + " ");
        }
        sb.append("\nLegend: [ ]Available  [X]Occupied\n"); // legend
        return sb.toString();
    }
    
    /** 
     * @return number of columns for this seating.
     */
    public int getNumCols() {
        return seats[0].length;
    }
    
    /** 
     * @return number of rows for this seating.
     */
    public int getNumRows() {
        return seats.length;
    }
}

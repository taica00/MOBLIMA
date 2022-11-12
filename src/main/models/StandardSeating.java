package main.models;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Represents the seating layout of a cinema theatre.
 * 
 * @author Tai Chen An
 * @version 1.1 
 * @since 2022-11-12 
 */

public class StandardSeating implements Seating {
    private static final long serialVersionUID = 5L;
    
    /**
     * The seats of this theatre represented in a 2D array.
     */
    private int[][] seats;

    /**
     * This set holds the column index of all aisles.
     */
    private Set<Integer> aisles;

    /**
     * Creates a new Seating with the given cinemaClass.
     * The fields for this seating are provided by methods in {@link CinemaClass}.
     * @param cinemaClass class of the cinema
     */
    public StandardSeating(CinemaClass cinemaClass) {
        int rows = cinemaClass.rows();
        int columns = cinemaClass.columns();
        aisles = new HashSet<>(Arrays.stream(cinemaClass.aisles()).boxed().collect(Collectors.toList()));
        seats = new int[rows][columns];
    }

    /**
     * Books a seat with the given row and column indexes. 
     * @param row index of row
     * @param col index of column
     * @return true if seat is not taken.
     */
    @Override
    public boolean bookSeat(char row, int col) {
        if (seats[row-'A'][col-1] == 1) // occupied seat
            return false;
        seats[row-'A'][col-1] = 1;
        return true;
    }

    /**
     * Unbooks a seat in the case where movie-goer does not complete payment. 
     * @param row index of row
     * @param col index of column
     */
    @Override
    public void unBookSeat(char row, int col) {
        seats[row-'A'][col-1] = 0;
    }

    /** 
     * @return the seating layout, showing the available and taken seats.
     */
    @Override
    public void displaySeating() {
        System.out.println("SEATS SELECTION:\n ");
        int rowLength = seats[0].length + aisles.size();
        for (int i = 0; i < rowLength-2 ; i++) {
            if (i == (rowLength-2)/2)
                System.out.print("-SCREEN-");
            else
                System.out.print("---");
        }
        System.out.println("--\n");
        for (int i = 0; i < seats.length; i++) {  
            System.out.print((char)(i+'A') + " ");  // row index
            for (int j = 0; j < seats[i].length; j++) { // seats
                if (aisles.contains(j)) 
                    System.out.print("   ");    
                if (seats[i][j] == 0)
                    System.out.print("[ ]");
                else 
                    System.out.print("[X]");   
            }
            System.out.println(" " + (char)(i+'A')); // row index
        }
        System.out.print("  ");
        for (int i = 1; i <= seats[0].length; i++) { // col index
            if (aisles.contains(i-1)) 
                System.out.print("   ");  
            if (i >= 10)
                System.out.print(i + " ");
            else
                System.out.print(" " + i + " ");
        }
        System.out.println("\nLegend: [ ]Available  [X]Occupied\n"); // legend
    }
    
    /** 
     * @return number of columns for this seating.
     */
    @Override
    public int getNumCols() {
        return seats[0].length;
    }
    
    /** 
     * @return number of rows for this seating.
     */
    @Override
    public int getNumRows() {
        return seats.length;
    }
}

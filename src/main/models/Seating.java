package main.models;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Seating implements java.io.Serializable {
    private static final long serialVersionUID = 5L;
    private int[][] seats;
    private Set<Integer> aisles;

    public Seating(CinemaClass cinemaClass) {
        if (cinemaClass == null)
            throw new IllegalArgumentException("cinemaClass cannot be null");
        int rows = cinemaClass.rows();
        int columns = cinemaClass.columns();
        aisles = new HashSet<>(Arrays.stream(cinemaClass.aisles()).boxed().collect(Collectors.toList()));
        seats = new int[rows][columns];
    }

    public boolean bookSeat(char row, int col) {
        if (seats[row-'A'][col-1] == 1) // occupied seat
            return false;
        seats[row-'A'][col-1] = 1;
        return true;
    }

    public void unBookSeat(char row, int col) {
        seats[row-'A'][col-1] = 0;
    }

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

    public int getNumCols() {
        return seats[0].length;
    }
}

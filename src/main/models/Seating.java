package main.models;

public class Seating implements java.io.Serializable {
    private static final long serialVersionUID = 5L;
    private int[][] seats;
    private int numAvailableSeats;

    public Seating(CinemaClass cinemaClass) {
        generateSeating(cinemaClass);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SEATS SELECTION:\n\n");
        sb.append(" ");
        for (int i = 0; i < seats[0].length-3; i++) {
            if (i == (seats[0].length-5)/2)
                sb.append("-----SCREEN-");
            else
                sb.append("---");
        }
        sb.append("--\n\n");
        for (int i = 0; i < seats.length; i++) {  
            sb.append((char)(i+'A') + " ");  // index
            for (int j = 0; j < seats[i].length; j++) { //seats
                if (seats[i][j] == 0)
                    sb.append("[ ]");
                else if (seats[i][j] == 1)
                    sb.append("[X]");
                else
                    sb.append("   ");
            }
            sb.append(" " + (char)(i+'A') + "\n"); // index
        }
        sb.append("  ");
        for (int i = 0, j = 1; i < seats[0].length; i++) { // index
            if (seats[0][i] == -1)
                sb.append("   ");
            else { 
                if (j >= 10)
                    sb.append(j + " ");
                else
                    sb.append(" " + j + " ");
                j++;
            }
        }
        sb.append("\nLegend: [ ]Available  [X]Occupied\n"); // legend
        return sb.toString();
    }

    private void generateSeating(CinemaClass cinemaClass) {
        int rows = cinemaClass.rows();
        int columns = cinemaClass.columns();
        int[] aisles = cinemaClass.aisles();
        seats = new int[rows][columns];
        for (int i : aisles) {
            for (int j = 0; j < seats.length; j++) {
                seats[j][i] = -1; //aisle
            }
        }
        numAvailableSeats = rows * (columns - aisles.length); 
    }

    public int getNumAvailableSeats() {
        return numAvailableSeats;
    }
}

package main.models;

import main.models.Session.CinemaClass;

public class Seating implements java.io.Serializable {
    private static final long serialVersionUID = 5L;
    private int[][] seats;

    public Seating(CinemaClass cinemaClass) {
        generateSeating(cinemaClass);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
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
        int rows = 0, columns = 0;
        int[] aisles;
        switch (cinemaClass) {
            case STANDARD: 
                rows = 17;
                columns = 36;
                aisles = new int[]{7, 28};
                break;
            case GVMAX: 
                rows = 10;
                columns = 20;
                aisles = new int[]{4, 15};
                break;
            case GOLDCLASS:
            case GOLDCLASSEXPRESS:
                rows = 4;
                columns = 11;
                aisles = new int[]{2, 5, 8};
                break;
            case DELUXEPLUS:
                rows = 5;
                columns = 11;
                aisles = new int[]{2, 5, 8};
                break;
            case GEMINI: //each index represents 2 seats
                rows = 7;
                columns = 11;
                aisles = new int[]{2, 5, 8};
                break;
            default: aisles = new int[1]; 
        }
        seats = new int[rows][columns];
        for (int i : aisles) {
            for (int j = 0; j < seats.length; j++) {
                seats[j][i] = -1; //aisle
            }
        }
    }
}

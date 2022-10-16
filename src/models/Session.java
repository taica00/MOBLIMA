package models;

import java.time.LocalDateTime;
import java.util.Random;

public class Session implements java.io.Serializable {
    private Cinema cinema;
    private Movie movie;
    private LocalDateTime dateTime;
    private int[][] seating;
    private CinemaClass cinemaClass;
    private String movieType;

    enum CinemaClass {STANDARD, GVMAX, GOLDCLASS, GOLDCLASSEXPRESS, DELUXEPLUS, GEMINI}

    public Session(Cinema cinema, Movie movie, LocalDateTime dateTime, String cinemaClass) {
        this.cinema = cinema;
        this.movie = movie;
        this.dateTime = dateTime;
        this.cinemaClass = CinemaClass.valueOf(cinemaClass);
        movieType = "2D";
        generateSeating();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(cinema.getCineplex() + " " + cinema.getLocation() + " - " + cinemaClass + "\n");
        sb.append(movie.getTitle() + " (" + movie.getRating() + ")\n");
        sb.append(dateTime.toString() + "\n");
        return sb.toString();
    }

    private void generateSeating() {
        int rows = 0, columns = 0;
        int[] aisles;
        switch (cinemaClass) {
            case STANDARD: 
                rows = 17;
                columns = 36;
                aisles = new int[]{8, 29};
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
                columns = 8;
                aisles = new int[]{1, 3, 5};
                break;
            default: aisles = new int[1]; 
        }
        seating = new int[rows][columns];
        for (int i : aisles) {
            for (int j = 0; j < seating.length; j++) {
                seating[j][i] = -1; //aisle
            }
        }
        Random rd = new Random();
        int takenSeats = rd.nextInt(seating.length*seating[0].length/2);
        for (int i = 0; i < takenSeats; i++) {
            int x, y;
            do {
                x = rd.nextInt(seating.length);
                y = rd.nextInt(seating[0].length);
            } while (seating[x][y] != 0);
            seating[x][y] = 1;
        }
    } 
}

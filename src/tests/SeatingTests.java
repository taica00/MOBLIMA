package tests;

import main.models.CinemaClass;
import main.models.Seating;

public class SeatingTests {
    public static void main(String[] args) {
        for (CinemaClass cinemaClass : CinemaClass.values()) {
            Seating seating = new Seating(cinemaClass);
            System.out.println(cinemaClass);
            System.out.println(seating);
        }
    }
}

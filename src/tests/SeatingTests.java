package tests;

import main.models.CinemaClass;
import main.models.Seating;
import main.models.StandardSeating;

public class SeatingTests {
    public static void main(String[] args) {
        for (CinemaClass cinemaClass : CinemaClass.values()) {
            Seating seating = new StandardSeating(cinemaClass);
            System.out.println(cinemaClass);
            seating.displaySeating();
        }
    }
}

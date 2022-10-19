package tests;

import models.Seating;
import models.Session.CinemaClass;

public class SeatingTests {
    public static void main(String[] args) {
        for (CinemaClass cinemaClass : CinemaClass.values()) {
            Seating seating = new Seating(cinemaClass);
            System.out.println(seating);
        }
    }
   
    
}

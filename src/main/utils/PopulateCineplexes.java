package main.utils;

import java.util.ArrayList;
import java.util.List;

import main.models.Cinema;
import main.models.CinemaClass;
import main.models.Cineplex;

public class PopulateCineplexes extends Populator{
    public static void main(String[] args) {
        List<Cineplex> cineplexes = new ArrayList<>();
        Cineplex nex = new Cineplex("nex"); 
        Cineplex jCube = new Cineplex("JCube");
        Cineplex jewel = new Cineplex("Jewel");

        nex.addCinema(new Cinema(nex, 1, CinemaClass.PREMIERE));
        nex.addCinema(new Cinema(nex, 2, CinemaClass.PREMIERE));
        for (int i = 1; i <= 8; i++)
            nex.addCinema(new Cinema(nex, i, CinemaClass.STANDARD));

        jCube.addCinema(new Cinema(jCube, 1, CinemaClass.IMAX));
        for (int i = 1; i <= 6; i++) 
            jCube.addCinema(new Cinema(jCube, i, CinemaClass.STANDARD));
        
        jewel.addCinema(new Cinema(jewel, 1, CinemaClass.IMAX));
        jewel.addCinema(new Cinema(jewel, 1, CinemaClass.LUMIERE));
        jewel.addCinema(new Cinema(jewel, 2, CinemaClass.LUMIERE));
        jewel.addCinema(new Cinema(jewel, 1, CinemaClass.DREAMERS));
        for (int i = 1; i <= 7; i++) 
            jewel.addCinema(new Cinema(jewel, i, CinemaClass.STANDARD));
    
        cineplexes.add(nex);
        cineplexes.add(jCube);
        cineplexes.add(jewel);
        serialize(cineplexes, "cineplexes.ser");
    }
}

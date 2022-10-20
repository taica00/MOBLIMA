package main.controllers;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

import main.models.Cinema;
import main.models.Cineplex;
import main.models.Movie;
import main.models.Session;

public class CineplexController {
    private static Cineplex[] cineplexes;
    private static final String FILENAME = "src/main/data/cineplexes.ser";

    private CineplexController() {
        throw new IllegalStateException("Utility class");
    }

    public static void viewShowTimes(Movie movie) {
        for (Cineplex cineplex : cineplexes) {
            for (Cinema cinema : cineplex.getCinemas()) {
                List<Session> sessions = cinema.getShowTimes();
                if (sessions.isEmpty())
                    continue;
                System.out.println(cinema);
            }
        }
    }

    public static void loadCineplexes() {
        try {
            FileInputStream fileIn = new FileInputStream(FILENAME);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            cineplexes = (Cineplex[])in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            System.out.println("Class not found");
            c.printStackTrace();
            return;
        }
    }
}

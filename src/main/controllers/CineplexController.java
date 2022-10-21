package main.controllers;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.time.LocalDateTime;
import java.util.List;

import main.models.Cinema;
import main.models.Cineplex;
import main.models.Movie;
import main.models.Session;

public class CineplexController extends Controller {
    private static Cineplex[] cineplexes;
    private static final String FILENAME = "src/main/data/cineplexes.ser";

    public static void viewShowTimes(Movie movie) {
        for (Cineplex cineplex : cineplexes) {
            for (Cinema cinema : cineplex.getCinemas()) {
                List<Session> sessions = cinema.getShowTimes(movie);
                if (sessions.isEmpty())
                    continue;
                System.out.println(cinema);
                for (Session session : sessions) {
                    LocalDateTime dateTime = session.getDateTime();
                    System.out.println(dateTime.getDayOfMonth() + " " + dateTime.getMonth() + " " + dateTime.getYear() + " " + dateTime.toLocalTime() + " - " + session.getCinemaClass());
                }
                System.out.println();
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

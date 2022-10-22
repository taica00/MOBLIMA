package main.controllers;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import main.models.Cinema;
import main.models.Cineplex;
import main.models.Movie;
import main.models.Session;
import main.ui.ShowTimesUI;

public class CineplexController extends Controller {
    private static List<Cineplex> cineplexes;
    private static final String FILENAME = "src/main/data/cineplexes.ser";

    public static void viewShowTimes(Movie movie) {
        List<List<Session>> movieSessions = new ArrayList<>();
        for (Cineplex cineplex : cineplexes) {
            for (Cinema cinema : cineplex.getCinemas()) {
                List<Session> cinemaSessions = cinema.getShowTimes(movie);
                if (cinemaSessions.isEmpty())
                    continue;
                movieSessions.add(cinemaSessions);
            }
        }
        ShowTimesUI.view(movieSessions);
    }

    public static void loadCineplexes() {
        try {
            FileInputStream fileIn = new FileInputStream(FILENAME);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            cineplexes = (List<Cineplex>)in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("Class not found");
            c.printStackTrace();
        }
    }
}

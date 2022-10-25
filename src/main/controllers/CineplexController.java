package main.controllers;

import java.util.ArrayList;
import java.util.List;

import main.models.Cinema;
import main.models.Cineplex;
import main.models.Movie;
import main.models.Session;
import main.ui.ShowTimesUI;

public class CineplexController extends Controller {
    private static List<Cineplex> cineplexes;
    private static final String FILEPATH = "src/main/data/cineplexes.ser";

    public static void viewShowTimes(Movie movie) {
        List<List<Session>> movieSessions = new ArrayList<>();
        for (Cineplex cineplex : cineplexes) {
            for (Cinema cinema : cineplex.getCinemas()) {
                List<Session> cinemaSessions = cinema.getSessions(movie);
                if (cinemaSessions.isEmpty())
                    continue;
                movieSessions.add(cinemaSessions);
            }
        }
        ShowTimesUI.view(movieSessions);
    }

    public static void loadCineplexes() {
        cineplexes = loadData(FILEPATH);
    }

    public static void saveCineplexes() {
        saveData(cineplexes, FILEPATH);
    }
}

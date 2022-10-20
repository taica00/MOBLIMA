package main;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

import main.models.Cineplex;
import main.models.Movie;
import main.models.Session;

public class MainApp {
    public static void main(String[] args) {
        List<Movie> movies = null;
        Cineplex[] cineplexes = null;
        try {
            FileInputStream fileIn = new FileInputStream("src/data/movies.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            movies = (List<Movie>) in.readObject();
            in.close();
            fileIn.close();

            fileIn = new FileInputStream("src/data/cineplexesshowtimes.ser");
            in = new ObjectInputStream(fileIn);
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
        List<Session> sessions = cineplexes[1].getCinema("Funan").getShowTimes();
        for (Session session : sessions)
            System.out.println(session);

    }
}

package tests;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import main.models.Cinema;
import main.models.CinemaClass;
import main.models.Cineplex;
import main.models.Movie;
import main.models.MovieStatus;
import main.ui.MovieDetails;
import main.ui.ShowTimes;

public class MovieBookingTests {
    public static void main(String[] args) {
        Movie testMovie = new Movie("test", "PG" , MovieStatus.END_OF_SHOWING, "test", "test", "test");
        Cineplex testCineplex = new Cineplex("test");
        Cinema testCinema = new Cinema(testCineplex, 1, CinemaClass.STANDARD);
        testCineplex.addCinema(testCinema);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyHHmm");
        LocalDateTime dateTime = LocalDateTime.parse("0211221200", formatter);
        testCinema.addSession(testMovie, dateTime, false);

        // configure end of showing and movie should not be listed for booking
        MovieDetails.view(testMovie);
        System.out.println();
        ShowTimes.view(testCineplex);

        // booking only allowed for “Preview” and “Now Showing” status.
        System.out.println();
        testMovie.setShowingStatus("PREVIEW");
        MovieDetails.view(testMovie);
        ShowTimes.view(testCineplex);

        System.out.println();
        testMovie.setShowingStatus("NOW_SHOWING");
        MovieDetails.view(testMovie);
        ShowTimes.view(testCineplex);
    }
}

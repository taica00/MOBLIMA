package tests;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import main.models.Cinema;
import main.models.Movie;
import main.models.MovieStatus;
import main.ui.MovieDetailsUI;
import main.ui.ShowTimesUI;

public class BookingTests {
    public static void main(String[] args) {
        // configure end of showing and movie should not be listed for booking

        // from movie showtimes
        Movie testMovie = new Movie("test", "PG" , MovieStatus.NOW_SHOWING, "test", "test", "test");
        MovieDetailsUI.view(testMovie);
        System.out.println();
        testMovie.setShowingStatus("END_OF_SHOWING");
        MovieDetailsUI.view(testMovie);

        // from cinema showtimes
        System.out.println();
        Cinema testCinema = new Cinema("test", "test");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyHHmm");
        LocalDateTime dateTime = LocalDateTime.parse("0211221200", formatter);
        testCinema.addSession(testMovie, dateTime, "STANDARD", false);
        ShowTimesUI.view(testCinema, false);

        // TODO booking only allowed for “Preview” and “Now Showing” status.
    }
}

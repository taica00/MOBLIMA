package main.models;

/**
 * Showing status for movies.
 * Determines if movie showtimes are visible to movie-goers.
 * @author Tai Chen An
 * @version 1.0 
 * @since 2022-11-06 
 */

public enum MovieStatus {
    COMING_SOON("Coming Soon"), 
    PREVIEW("Preview"), 
    NOW_SHOWING("Now Showing"), 
    END_OF_SHOWING("End of Showing");

    private String string;

    MovieStatus(String name){string = name;}

    @Override
    public String toString() {
        return string;
    }
}
